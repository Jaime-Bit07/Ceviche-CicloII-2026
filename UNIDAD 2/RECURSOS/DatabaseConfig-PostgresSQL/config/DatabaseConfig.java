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
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * Configuración de base de datos PostgreSQL.
 */
public class DatabaseConfig {

    private static final Logger log = LoggerFactory.getLogger(DatabaseConfig.class);
    private static DataSource dataSource;

    private DatabaseConfig() {
    }

    /**
     * Inicializa el DataSource una sola vez.
     */
    public static synchronized void init() {
        if (dataSource != null) { return; }
        Properties props = loadProperties("application.properties");
        // Las variables de entorno DB_URL, DB_USERNAME y DB_PASSWORD tienen
        // prioridad: así las credenciales no necesitan estar en el código.
        String url = env("DB_URL", props.getProperty("db.url"));
        String username = env("DB_USERNAME", props.getProperty("db.username"));
        String password = env("DB_PASSWORD", props.getProperty("db.password", ""));

        if (url == null || url.isBlank()) {
            throw new RuntimeException(
                    "No se ha configurado db.url en application.properties (ni la variable DB_URL)"
            );
        }

        try {
            /*
             * ========================================================
             * DataSource PostgreSQL (pool HikariCP)
             * ========================================================
             * Neon es una BD en la nube: cada conexión nueva paga TCP +
             * TLS + auth (y un posible "cold start" si el cómputo estaba
             * en autosuspend). El pool evita pagar ese costo en cada
             * operación, reutilizando conexiones ya abiertas.
             */
            HikariConfig hc = new HikariConfig();
            hc.setJdbcUrl(url);
            hc.setUsername(username);
            hc.setPassword(password);
            hc.setDriverClassName("org.postgresql.Driver");
            hc.setPoolName("SysVentasPool");

            // App de escritorio, un solo usuario: pool pequeño es suficiente.
            hc.setMaximumPoolSize(10);
            hc.setMinimumIdle(2);

            // Neon puede tardar varios segundos en "despertar" el cómputo
            // si estaba en autosuspend; damos margen antes de fallar.
            hc.setConnectionTimeout(30_000);

            // Revalida conexiones inactivas cada 4 min para detectar y
            // reemplazar conexiones que Neon haya cerrado por inactividad,
            // en vez de que el usuario reciba el error en plena operación.
            hc.setKeepaliveTime(TimeUnit.MINUTES.toMillis(4));
            hc.setMaxLifetime(TimeUnit.MINUTES.toMillis(25));
            hc.setIdleTimeout(TimeUnit.MINUTES.toMillis(10));

            HikariDataSource ds = new HikariDataSource(hc);
            try (Connection conn = ds.getConnection()) {
                log.info("==========================================");
                log.info("Conexión PostgreSQL establecida correctamente");
                log.info("Base de datos: {}", conn.getCatalog());
                log.info("Usuario: {}", username);
                log.info("URL: {}", url);
                log.info("==========================================");
            }
            dataSource = ds;

        } catch (Exception e) {
            log.error("No fue posible conectar con PostgreSQL", e);
            throw new RuntimeException(
                    "No fue posible conectar con PostgreSQL.", e
            );
        }
        log.info("DataSource PostgreSQL inicializado.");
        /*
         * ========================================================
         * Ejecución automática del DDL
         * ========================================================
         */
        boolean ddlAuto = Boolean.parseBoolean(
                props.getProperty("db.ddl.auto", "true")
        );
        if (ddlAuto) {
            String script = props.getProperty("db.ddl.script", "schema.sql");
            runDdlScript(script);
        }
    }

    /**
     * Devuelve el DataSource.
     */
    public static DataSource getDataSource() {
        if (dataSource == null) {
            init();
        }
        return dataSource;
    }

    /**
     * Obtiene una conexión.
     */
    public static Connection getConnection()
            throws SQLException {
        return getDataSource().getConnection();
    }

    /**
     * Libera recursos.
     */
    public static synchronized void shutdown() {
        if (dataSource instanceof HikariDataSource hikariDs) {
            log.info("Liberando DataSource PostgreSQL...");
            hikariDs.close();
        }
        dataSource = null;
    }

    // ===========================================================
    // Métodos privados
    // ===========================================================
    /**
     * Devuelve la variable de entorno si existe y no está vacía;
     * en otro caso, el valor por defecto.
     */
    private static String env(String name, String defaultValue) {
        String value = System.getenv(name);
        return (value == null || value.isBlank()) ? defaultValue : value;
    }

    /**
     * Carga application.properties.
     */
    private static Properties loadProperties(String filename) {
        Properties props = new Properties();
        try (InputStream is =
                     DatabaseConfig.class
                             .getClassLoader()
                             .getResourceAsStream(filename)) {
            if (is == null) {
                throw new RuntimeException( "No se encontró " + filename);
            }

            props.load(is);
            return props;
        } catch (IOException e) {
            throw new RuntimeException(  "Error leyendo " + filename,e );
        }
    }

    /**
     * Ejecuta el script DDL.
     */
    private static void runDdlScript(String scriptName) {
        log.info("Ejecutando DDL: {}", scriptName);
        try (InputStream is =
                     DatabaseConfig.class.getClassLoader()
                             .getResourceAsStream(scriptName)) {
            if (is == null) {
                log.warn("No se encontró el script {}",  scriptName );
                return;
            }
            String sql = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            /*
             * Eliminamos comentarios de línea.
             */
            String noComments = Arrays.stream(
                            sql.split("\\R")
                    )
                    .filter(line ->
                            !line.strip().startsWith("--"))
                    .reduce(
                            "",
                            (a, b) -> a + "\n" + b
                    );
            /*
             * Dividimos las sentencias por ;
             */
            String[] statements = noComments.split(";");
            try (Connection conn = getConnection();
                 Statement stmt = conn.createStatement()) {
                int executed = 0;
                int skipped = 0;
                for (String raw : statements) {
                    String statement =
                            raw.strip();
                    if (statement.isEmpty()) {
                        continue;
                    }
                    try {
                        stmt.execute(statement);
                        executed++;
                        log.debug(
                                "DDL ejecutado correctamente."
                        );
                    } catch (SQLException e) {
                        String sqlState =
                                e.getSQLState();
                        /*
                         * PostgreSQL:
                         *
                         * 42P07 = relation/table already exists
                         * 42710 = duplicate object
                         */
                        if ("42P07".equals(sqlState)
                                || "42710".equals(sqlState)) {

                            skipped++;
                            log.debug("Objeto ya existente. Se omite DDL."
                            );
                        } else {
                            log.error(
                                    "Error ejecutando DDL.\nSQL: {}\nSQLState: {}",
                                    statement, sqlState, e
                            );
                            throw e;
                        }
                    }
                }
                log.info(
                        "DDL completado. Ejecutadas: {} | Omitidas: {}",
                        executed,  skipped
                );
            }
        } catch (Exception e) {
            throw new RuntimeException(
                    "Error ejecutando " + scriptName,e
            );
        }
    }
}
