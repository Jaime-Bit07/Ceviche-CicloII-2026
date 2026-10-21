package pe.edu.upeu.sysventas.repository;

import pe.edu.upeu.sysventas.model.Producto;
import pe.edu.upeu.sysventas.model.Usuario;
import pe.edu.upeu.sysventas.model.VentCarrito;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class VentCarritoRepository extends AbstractJpaRepository<VentCarrito, Long> {
    @Override protected String getTableName() { return "vent_carrito"; }
    @Override protected String getPkColumn()  { return "id_carrito"; }

    private static final String SELECT_JOIN =
            "SELECT vc.*, p.nombre AS prod_nombre, p.pu AS prod_pu, p.stock AS prod_stock " +
            "FROM vent_carrito vc " +
            "JOIN producto p ON vc.id_producto = p.id_producto ";

    @Override public List<VentCarrito> findAll() { return executeQuery(SELECT_JOIN); }
    @Override public Optional<VentCarrito> findById(Long id) {
        return executeQueryOne(SELECT_JOIN + "WHERE vc.id_carrito = ?", id);
    }

    @Override
    protected VentCarrito mapRow(ResultSet rs) throws SQLException {
        VentCarrito vc = new VentCarrito();
        vc.setIdCarrito(rs.getLong("id_carrito"));
        vc.setDniruc(rs.getString("dniruc"));
        vc.setNombreProducto(rs.getString("nombre_producto"));
        vc.setCantidad(rs.getDouble("cantidad"));
        vc.setPunitario(rs.getDouble("punitario"));
        vc.setPtotal(rs.getDouble("ptotal"));
        vc.setEstado(rs.getInt("estado"));
        // Producto mínimo (solo id y nombre para la tabla)
        Producto p = Producto.builder().idProducto(rs.getLong("id_producto"))
                .nombre(rs.getString("prod_nombre")).build();
        vc.setIdProducto(p);
        // Usuario mínimo (solo id)
        Usuario u = new Usuario();
        u.setIdUsuario(rs.getLong("id_usuario"));
        vc.setIdUsuario(u);
        return vc;
    }
    @Override
    protected VentCarrito insert(Connection conn, VentCarrito e) throws SQLException {
        long id = executeInsertGetKey(conn,
                "INSERT INTO vent_carrito(dniruc,id_producto,nombre_producto,cantidad,punitario,ptotal,estado,id_usuario,tipo_producto) VALUES(?,?,?,?,?,?,?,?,?)",
                e.getDniruc(),e.getIdProducto().getIdProducto(),e.getNombreProducto(),
                e.getCantidad(),e.getPunitario(),e.getPtotal(),e.getEstado(),
                e.getIdUsuario().getIdUsuario(), e.getTipoProducto().name() );
        e.setIdCarrito(id); return e;
    }
    @Override
    protected VentCarrito updateRow(Connection conn, VentCarrito e) throws SQLException {
        executeUpdate(conn,
                "UPDATE vent_carrito SET dniruc=?,id_producto=?,nombre_producto=?,cantidad=?,punitario=?,ptotal=?,estado=? WHERE id_carrito=?",
                e.getDniruc(),e.getIdProducto().getIdProducto(),e.getNombreProducto(),
                e.getCantidad(),e.getPunitario(),e.getPtotal(),e.getEstado(),e.getIdCarrito());
        return e;
    }
    public List<VentCarrito> listaCarritoCliente(String dniruc) {
        return executeQuery(SELECT_JOIN + "WHERE vc.dniruc = ?", dniruc);
    }
    public void deleteByDniruc(String dniruc) {
        executeUpdateStandalone("DELETE FROM vent_carrito WHERE dniruc = ?", dniruc);
    }
}
