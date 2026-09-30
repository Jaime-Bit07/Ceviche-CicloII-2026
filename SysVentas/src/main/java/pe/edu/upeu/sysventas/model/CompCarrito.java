package pe.edu.upeu.sysventas.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompCarrito {
    private Long idCompcarrito;
    private Proveedor idProveedor;
    private Producto idProducto;
    private String nombreProducto;
    private Double cantidad;
    private Double punitario;
    private Double ptotal;
    private int estado;
    private Usuario idUsuario;
}