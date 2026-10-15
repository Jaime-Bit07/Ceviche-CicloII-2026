package pe.edu.upeu.sysventas.repository.helper;

import pe.edu.upeu.sysventas.config.DatabaseConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *  SqlHelper Capa de Acceso a Datos (JDBC puro)
 */
public abstract class SqlHelper<T> {

    protected Connection openConnection() throws SQLException {
        return DatabaseConfig.getConnection();
    }


    //  BLOQUE 2: MÉTODO ABSTRACTO DE MAPEO
    protected abstract T mapRow(ResultSet rs) throws SQLException;


    //  BLOQUE 3: HELPERS PARA CONSULTAS (SELECT)
    protected List<T> executeQuery(String sql, Object... params) {
        try (Connection conn = openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            setParams(ps, params);

            try (ResultSet rs = ps.executeQuery()) {
                List<T> list = new ArrayList<>();
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
                return list;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error en query: " + e.getMessage(), e);
        }
    }

    /**
     * Ejecuta un SELECT y retorna solo EL PRIMER resultado (o vacío).
     *
     * Usar Optional evita retornar null y previene NullPointerException:
     *   - Optional.of(objeto) → se encontró el registro
     *   - Optional.empty()    → no existe ningún registro
     *
     * ATENCIÓN: Este método llama a mapRow(). Solo usarlo con SELECT
     * que traigan todas las columnas de la entidad.
     * Para "SELECT 1" usar executeExists().
     *
     * @param sql    Sentencia SELECT con "?" como marcadores.
     * @param params Valores que reemplazan cada "?".
     * @return Optional con la entidad si se encontró, o Optional.empty().
     */
    protected Optional<T> executeQueryOne(String sql, Object... params) {
        try (Connection conn = openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            setParams(ps, params);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs)); // ← requiere columnas completas
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error en queryOne: " + e.getMessage(), e);
        }
    }

    /**
     * MÉTODO NUEVO — Verifica si existe un registro con el ID dado.
     *
     * ¿POR QUÉ EXISTE ESTE MÉTODO SEPARADO?
     * ----------------------------------------
     * El SQL "SELECT 1 FROM tabla WHERE pk = ?" devuelve un ResultSet
     * con UNA sola columna sin nombre de entidad. Si se pasara ese
     * ResultSet a mapRow(), este intentaría leer "id_producto",
     * "nombre", etc., y fallaría con:
     *   Column "id_producto" not found
     *
     * Este método maneja el ResultSet directamente (sin mapRow),
     * por eso es seguro para "SELECT 1".
     *
     * Solo retorna true/false — no construye ningún objeto.
     *
     * @param sql    Sentencia "SELECT 1 FROM tabla WHERE pk = ?"
     * @param id     Valor de la clave primaria a buscar
     * @return true si encontró al menos una fila, false si no existe
     */
    protected boolean executeExists(String sql, Object id) {
        try (Connection conn = openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setObject(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // true = existe, false = no existe
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error en existsById: " + e.getMessage(), e);
        }
    }

    // ═══════════════════════════════════════════════════════════════
    //  BLOQUE 4: HELPERS PARA MODIFICACIONES (INSERT / UPDATE / DELETE)
    // ═══════════════════════════════════════════════════════════════

    /**
     * Ejecuta un INSERT y retorna el ID generado automáticamente por la BD.
     *
     * NOTA: No maneja su propia transacción.
     * El commit/rollback lo hace AbstractJpaRepository en save().
     *
     * @param conn   Conexión activa con transacción ya abierta.
     * @param sql    Sentencia INSERT con "?" como marcadores.
     * @param params Valores que reemplazan cada "?" en orden.
     * @return El ID (clave primaria) generado por la base de datos.
     */
    protected long executeInsertGetKey(Connection conn, String sql, Object... params)
            throws SQLException {

        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            setParams(ps, params);
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) return keys.getLong(1);
                throw new SQLException("No se generó clave para: " + sql);
            }
        }
    }

    /**
     * Ejecuta un UPDATE o DELETE dentro de una transacción ya abierta.
     *
     * No abre conexión propia — usa la que le pasan.
     * El commit/rollback lo maneja AbstractJpaRepository.
     *
     * @param conn   Conexión con transacción abierta.
     * @param sql    Sentencia UPDATE o DELETE con "?" como marcadores.
     * @param params Valores que reemplazan cada "?".
     */
    protected void executeUpdate(Connection conn, String sql, Object... params)
            throws SQLException {

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            setParams(ps, params);
            ps.executeUpdate();
        }
    }

    /**
     * Ejecuta un UPDATE o DELETE abriendo su PROPIA conexión y transacción.
     *
     * Útil para operaciones aisladas fuera del flujo normal de save()/update().
     *
     * @param sql    Sentencia UPDATE o DELETE con "?" como marcadores.
     * @param params Valores que reemplazan cada "?".
     * @return Número de filas modificadas o eliminadas.
     */
    protected int executeUpdateStandalone(String sql, Object... params) {
        try (Connection conn = openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            conn.setAutoCommit(false);
            setParams(ps, params);
            int rows = ps.executeUpdate();
            conn.commit();
            return rows;

        } catch (SQLException e) {
            throw new RuntimeException("Error en executeUpdate: " + e.getMessage(), e);
        }
    }

    // ═══════════════════════════════════════════════════════════════
    //  BLOQUE 5: MÉTODO PRIVADO DE APOYO INTERNO
    // ═══════════════════════════════════════════════════════════════

    /**
     * Asigna los valores a los marcadores "?" del PreparedStatement.
     *
     * Los índices en JDBC comienzan en 1, no en 0.
     *   params[0] → posición 1
     *   params[1] → posición 2  ... etc.
     */
    private void setParams(PreparedStatement ps, Object... params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            ps.setObject(i + 1, params[i]);
        }
    }
}