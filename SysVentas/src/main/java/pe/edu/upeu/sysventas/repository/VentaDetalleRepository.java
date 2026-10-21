package pe.edu.upeu.sysventas.repository;

import pe.edu.upeu.sysventas.model.Producto;
import pe.edu.upeu.sysventas.model.Venta;
import pe.edu.upeu.sysventas.model.VentaDetalle;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
public class VentaDetalleRepository extends AbstractJpaRepository<VentaDetalle, Long> {
    @Override protected String getTableName() { return "venta_detalle"; }
    @Override protected String getPkColumn()  { return "id_venta_detalle"; }
    @Override
    protected VentaDetalle mapRow(ResultSet rs) throws SQLException {
        Venta v = new Venta(); v.setId(rs.getLong("id_venta"));
        Producto p = new Producto(); p.setIdProducto(rs.getLong("id_producto"));
        return VentaDetalle.builder().idVentaDetalle(rs.getLong("id_venta_detalle"))
                .pu(rs.getDouble("pu")).cantidad(rs.getDouble("cantidad"))
                .descuento(rs.getDouble("descuento")).subtotal(rs.getDouble("subtotal"))
                .idVenta(v).idProducto(p).build();
    }
    @Override
    protected VentaDetalle insert(Connection conn, VentaDetalle e) throws SQLException {
        long id = executeInsertGetKey(conn,
                "INSERT INTO venta_detalle(pu,cantidad,descuento,subtotal,id_venta,id_producto) VALUES(?,?,?,?,?,?)",
                e.getPu(),e.getCantidad(),e.getDescuento(),e.getSubtotal(),
                e.getIdVenta().getId(),e.getIdProducto().getIdProducto());
        e.setIdVentaDetalle(id); return e;
    }
    @Override
    protected VentaDetalle updateRow(Connection conn, VentaDetalle e) throws SQLException {
        executeUpdate(conn,"UPDATE venta_detalle SET pu=?,cantidad=?,descuento=?,subtotal=?,id_venta=?,id_producto=? WHERE id_venta_detalle=?",
                e.getPu(),e.getCantidad(),e.getDescuento(),e.getSubtotal(),
                e.getIdVenta().getId(),e.getIdProducto().getIdProducto(),e.getIdVentaDetalle()); return e;
    }
}
