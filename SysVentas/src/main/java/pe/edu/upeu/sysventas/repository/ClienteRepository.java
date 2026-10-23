package pe.edu.upeu.sysventas.repository;

import pe.edu.upeu.sysventas.enums.TipoDocumento;
import pe.edu.upeu.sysventas.model.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClienteRepository extends AbstractJpaRepository<Cliente, String> {
    @Override protected String getTableName() { return "cliente"; }
    @Override protected String getPkColumn()  { return "dniruc"; }

    @Override
    protected Cliente mapRow(ResultSet rs) throws SQLException {
        return Cliente.builder()
                .dniruc(rs.getString("dniruc"))
                .nombres(rs.getString("nombres"))
                .repLegal(rs.getString("rep_legal"))
                .direccion(rs.getString("direccion"))
                .tipoDocumento(TipoDocumento.valueOf(rs.getString("tipo_documento")))
                .build();
    }
    @Override
    protected Cliente insert(Connection conn, Cliente e) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO cliente(dniruc,nombres,rep_legal,tipo_documento, direccion) VALUES(?,?,?,?, ?)")) {
            ps.setString(1, e.getDniruc());
            ps.setString(2, e.getNombres());
            ps.setString(3, e.getRepLegal());
            ps.setString(4, e.getTipoDocumento().name());
            ps.setString(5, e.getDireccion());
            ps.executeUpdate();
        }
        return e;
    }
    @Override
    protected Cliente updateRow(Connection conn, Cliente e) throws SQLException {
        executeUpdate(conn,
                "UPDATE cliente SET nombres=?,rep_legal=?,tipo_documento=? WHERE dniruc=?",
                e.getNombres(),
                e.getRepLegal(),
                e.getTipoDocumento().name(),
                e.getDniruc());
        return e;
    }
}
