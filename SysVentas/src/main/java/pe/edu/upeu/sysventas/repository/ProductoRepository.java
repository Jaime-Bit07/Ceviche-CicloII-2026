package pe.edu.upeu.sysventas.repository;

import pe.edu.upeu.sysventas.enums.TipoProducto;
import pe.edu.upeu.sysventas.model.Categoria;
import pe.edu.upeu.sysventas.model.Marca;
import pe.edu.upeu.sysventas.model.Producto;
import pe.edu.upeu.sysventas.model.UnidMedida;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ProductoRepository extends AbstractJpaRepository<Producto, Long>{


    @Override
    protected String getTableName() {
        return "producto";
    }

    @Override
    protected String getPkColumn() {
        return "id_producto";
    }

    private static final String SELECT_JOIN =
            "SELECT p.*, c.nombre AS cat_nombre, m.nombre AS mar_nombre, " +
                    "u.nombre_medida FROM producto p " +
                    "JOIN categoria c ON p.id_categoria = c.id_categoria " +
                    "JOIN marca m ON p.id_marca = m.id_marca " +
                    "JOIN unid_medida u ON p.id_unidad = u.id_unidad ";
    @Override public List<Producto> findAll() { return executeQuery(SELECT_JOIN); }
    @Override public Optional<Producto> findById(Long id) {
        return executeQueryOne(SELECT_JOIN + "WHERE p.id_producto = ?", id);
    }


    @Override
    protected Producto insert(Connection conn, Producto e) throws SQLException {
        long id = executeInsertGetKey(conn,
                "INSERT INTO producto(nombre,pu,puold,utilidad,stock,stockold,id_categoria,id_marca,id_unidad, tipo_producto) VALUES(?,?,?,?,?,?,?,?,?, ?)",
                e.getNombre(),
                e.getPu(),
                e.getPuold(),
                e.getUtilidad(),
                e.getStock(),
                e.getStockold(),
                e.getIdCategoria().getIdCategoria(),
                e.getIdMarca().getIdMarca(),
                e.getIdUnidad().getIdUnidad(),
                e.getTipoProducto().name()
        );
        e.setIdProducto(id); return e;
    }

    @Override
    protected Producto updateRow(Connection conn, Producto e) throws SQLException {
        executeUpdate(conn,
                "UPDATE producto SET nombre=?,pu=?,puold=?,utilidad=?,stock=?,stockold=?,id_categoria=?,id_marca=?,id_unidad=?,tipo_producto=? WHERE id_producto=?",
                e.getNombre(),
                e.getPu(),
                e.getPuold(),
                e.getUtilidad(),
                e.getStock(),
                e.getStockold(),
                e.getIdCategoria().getIdCategoria(),
                e.getIdMarca().getIdMarca(),
                e.getIdUnidad().getIdUnidad(),
                e.getTipoProducto().name(),
                e.getIdProducto());
        return e;
    }

    @Override
    protected Producto mapRow(ResultSet rs) throws SQLException {
        return Producto.builder()
                .idProducto(rs.getLong("id_producto"))
                .nombre(rs.getString("nombre"))
                .pu(rs.getDouble("pu"))
                .puold(rs.getDouble("puold"))
                .utilidad(rs.getDouble("utilidad"))
                .stock(rs.getDouble("stock"))
                .stockold(rs.getDouble("stockold"))
                .tipoProducto((TipoProducto.valueOf(rs.getString("tipo_producto"))))

                .idCategoria(Categoria.builder()
                        .idCategoria(rs.getLong("id_categoria"))
                        .nombre(rs.getString("cat_nombre")).build())
                .idMarca(Marca.builder()
                        .idMarca(rs.getLong("id_marca"))
                        .nombre(rs.getString("mar_nombre")).build())
                .idUnidad(UnidMedida.builder()
                        .idUnidad(rs.getLong("id_unidad"))
                        .nombreMedida(rs.getString("nombre_medida")
                        )
                        .build())
                .build();
    }


}
