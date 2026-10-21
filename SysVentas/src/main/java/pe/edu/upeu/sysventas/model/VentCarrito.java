package pe.edu.upeu.sysventas.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.edu.upeu.sysventas.enums.TipoProducto;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VentCarrito {

    private Long idCarrito;
    private String dniruc;
    private Producto idProducto;
    private String nombreProducto;
    private Double cantidad;
    private Double punitario;
    private Double ptotal;
    private int estado;
    private Usuario idUsuario;
    private TipoProducto tipoProducto;
}