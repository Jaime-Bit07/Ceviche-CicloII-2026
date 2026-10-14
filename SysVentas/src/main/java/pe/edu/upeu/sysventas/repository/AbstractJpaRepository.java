package pe.edu.upeu.sysventas.repository;

import pe.edu.upeu.sysventas.repository.helper.SqlHelper;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractJpaRepository<T,ID> extends SqlHelper<T> implements ICrudGenericoRepository<T,ID>{

    protected abstract String getTableName();
    protected abstract String getPkColumn();
    protected abstract T insert(Connection connection, T entity) throws SQLException;
    protected abstract T updateRow(Connection connection, T entity) throws SQLException;
    @Override
    public T save(T entity) {
        try(Connection conn = openConnection()) {
            conn.setAutoCommit(false);
            try {
                T result = insert(conn, entity);
                conn.commit();
                return result;
            } catch (Exception e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error en guardar: " + e.getMessage(), e);
        }
    }

    @Override
    public T update(T entity) {
        try(Connection conn = openConnection()) {
            conn.setAutoCommit(false);
            try {
                T result = updateRow(conn, entity);
                conn.commit();
                return result;
            } catch (Exception e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar: " + e.getMessage(), e);
        }
    }
    @Override
    public Optional<T> findById(ID id) {
        String sql = "SELECT * FROM " + getTableName() + " WHERE " + getPkColumn() + " = ?";
        return executeQueryOne(sql, id);
    }
    @Override
    public List<T> findAll() {
        String sql = "SELECT * FROM " + getTableName();
        return executeQuery(sql);
    }
    @Override
    public void deleteById(ID id) {
        String sql = "DELETE FROM " + getTableName() + " WHERE " + getPkColumn() + " = ?";
        executeUpdateStandalone(sql, id);
    }
    @Override
    public boolean existsById(ID id) {
        String sql = "SELECT 1 FROM " + getTableName() + " WHERE " + getPkColumn() + " = ?";
        return executeExists(sql, id);
    }

}
