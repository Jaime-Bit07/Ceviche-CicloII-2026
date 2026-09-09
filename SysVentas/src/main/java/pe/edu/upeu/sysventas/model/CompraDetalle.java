package pe.edu.upeu.sysventas.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompraDetalle {
    private Long idCompraDetalle;
    private Double pu;
    private Double cantidad;
    private Double subtotal;
    private Compra idCompra;
    private Producto idProducto;
}