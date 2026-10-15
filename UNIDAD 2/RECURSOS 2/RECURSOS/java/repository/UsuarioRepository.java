package pe.edu.upeu.sysventas.repository;

import pe.edu.upeu.sysventas.model.Perfil;
import pe.edu.upeu.sysventas.model.Usuario;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UsuarioRepository extends AbstractJpaRepository<Usuario, Long> {
    private static final String SELECT_JOIN =
            "SELECT u.*, p.nombre AS perfil_nombre, p.codigo AS perfil_codigo " +
                    "FROM usuario u JOIN perfil p ON u.id_perfil = p.id_perfil ";
    @Override
    protected String getTableName() {return "usuario";}
    @Override
    protected String getPkColumn() {return "id_usuario";}

    @Override
    public List<Usuario> findAll() {
        return executeQuery(SELECT_JOIN);
    }

    @Override
    public Optional<Usuario> findById(Long id) {
        return executeQueryOne(SELECT_JOIN + "WHERE u.id_usuario = ?", id);
    }

    @Override
    protected Usuario mapRow(ResultSet rs) throws SQLException {
        Perfil perfil = Perfil.builder()
                .idPerfil(rs.getLong("id_perfil"))
                .nombre(rs.getString("perfil_nombre"))
                .codigo(rs.getString("perfil_codigo"))
                .build();
        return Usuario.builder()
                .idUsuario(rs.getLong("id_usuario"))
                .usuario(rs.getString("usuario"))
                .clave(rs.getString("clave"))
                .estado(rs.getString("estado"))
                .idPerfil(perfil)
                .build();
    }
    public Optional<Usuario> buscarUsuario(String usuario) {
        return executeQueryOne(SELECT_JOIN + "WHERE u.usuario = ?",
                usuario);
    }

    public Optional<Usuario> findByUsuarioAndClave(String usuario, String clave) {
        return executeQueryOne(SELECT_JOIN + "WHERE u.usuario = ? AND u.clave = ?",
                usuario,
                clave
        );
    }

    @Override
    protected Usuario insert(Connection conn, Usuario entity) throws SQLException {
        long id = executeInsertGetKey(conn,
                "INSERT INTO usuario(usuario,clave,estado,id_perfil) VALUES(?,?,?,?)",
                entity.getUsuario(), entity.getClave(), entity.getEstado(),
                entity.getIdPerfil() != null ? entity.getIdPerfil().getIdPerfil() : null);
        entity.setIdUsuario(id); return entity;
    }

    @Override
    protected Usuario updateRow(Connection conn, Usuario entity) throws SQLException {
        executeUpdate(conn,
                "UPDATE usuario SET usuario=?,clave=?,estado=?,id_perfil=? WHERE id_usuario=?",
                entity.getUsuario(), entity.getClave(), entity.getEstado(),
                entity.getIdPerfil() != null ? entity.getIdPerfil().getIdPerfil() : null,
                entity.getIdUsuario());
        return entity;
    }
}
