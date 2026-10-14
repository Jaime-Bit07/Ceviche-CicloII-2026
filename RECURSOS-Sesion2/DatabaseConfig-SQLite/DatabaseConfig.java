package pe.edu.upeu.sysventas.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * Configuración de la base de datos SQLite (archivo local) con pool HikariCP.
 *
 * <p>Propiedades opcionales en {@code application.properties}:
 * <ul>
 *   <li>{@code db.url} (por defecto {@value #DEFAULT_URL})</li>
 *   <li>{@code db.ddl.auto} (por defecto {@code true})</li>
 *   <li>{@code db.ddl.script} (por defecto {@value #DEFAULT_SCRIPT})</li>
 * </ul>
 */
public final class DatabaseConfig {

    private static final Logger LOG = LoggerFactory.getLogger(DatabaseConfig.class);

    private static final String PROPERTIES_FILE = "application.properties";
    private static final String DEFAULT_URL = "jdbc:sqlite:data/sysventas.db";
    private static final String DEFAULT_SCRIPT = "schema_sv.sql";

    private static volatile HikariDataSource dataSource;

    private DatabaseConfig() {
    }

    /** Inicializa el pool y ejecuta el DDL una sola vez. */
    public static synchronized void init() {
        if (dataSource != null) {
            return;
        }
        Properties props = loadProperties();
        String url = props.getProperty("db.url", DEFAULT_URL);

        HikariDataSource ds = createDataSource(url);
        try {
            if (Boolean.parseBoolean(props.getProperty("db.ddl.auto", "true"))) {
                runScript(ds, props.getProperty("db.ddl.script", DEFAULT_SCRIPT));
            }
        } catch (RuntimeException e) {
            ds.close();
            throw e;
        }
        dataSource = ds;
        LOG.info("SQLite listo: {}", url);
    }

    public static DataSource getDataSource() {
        if (dataSource == null) {
            init();
        }
        return dataSource;
    }

    public static Connection getConnection() throws SQLException {
        return getDataSource().getConnection();
    }

    /** Cierra el pool y libera el archivo de base de datos. */
    public static synchronized void shutdown() {
        if (dataSource != null) {
            dataSource.close();
            dataSource = null;
        }
    }

    private static HikariDataSource createDataSource(String url) {
        HikariConfig config = new HikariConfig();
        config.setPoolName("SysVentasPool");
        config.setDriverClassName("org.sqlite.JDBC");
        config.setJdbcUrl(url);
        // SQLite admite un único escritor: un pool pequeño es suficiente.
        config.setMaximumPoolSize(5);
        // PRAGMAs que SQLite aplica por conexión.
        config.addDataSourceProperty("foreign_keys", "true");  // desactivado por defecto en SQLite
        config.addDataSourceProperty("journal_mode", "WAL");   // lectores concurrentes con un escritor
        config.addDataSourceProperty("busy_timeout", "5000");  // espera 5 s si el archivo está bloqueado
        try {
            return new HikariDataSource(config);
        } catch (RuntimeException e) {
            throw new IllegalStateException("No fue posible abrir la base de datos SQLite: " + url, e);
        }
    }

    private static Properties loadProperties() {
        try (InputStream in = DatabaseConfig.class.getResourceAsStream("/" + PROPERTIES_FILE)) {
            if (in == null) {
                throw new IllegalStateException("No se encontró " + PROPERTIES_FILE);
            }
            Properties props = new Properties();
            props.load(in);
            return props;
        } catch (IOException e) {
            throw new IllegalStateException("Error leyendo " + PROPERTIES_FILE, e);
        }
    }

    /** Ejecuta el script DDL (idempotente) sentencia por sentencia. */
    private static void runScript(DataSource ds, String script) {
        try (InputStream in = DatabaseConfig.class.getResourceAsStream("/" + script)) {
            if (in == null) {
                LOG.warn("No se encontró el script DDL {}", script);
                return;
            }
            String sql = new String(in.readAllBytes(), StandardCharsets.UTF_8)
                    .lines()
                    .filter(line -> !line.strip().startsWith("--"))
                    .collect(Collectors.joining("\n"));

            try (Connection conn = ds.getConnection(); Statement stmt = conn.createStatement()) {
                for (String statement : sql.split(";")) {
                    if (!statement.isBlank()) {
                        stmt.execute(statement);
                    }
                }
            }
            LOG.info("DDL ejecutado: {}", script);
        } catch (IOException | SQLException e) {
            throw new IllegalStateException("Error ejecutando el script " + script, e);
        }
    }
}
