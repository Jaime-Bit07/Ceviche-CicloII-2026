package pe.edu.upeu.sysventas.dto.comprobante;

import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;
@Data
public class ItemComprobante {

    private String      descripcion;
    private Double         cantidad;
    private BigDecimal  precioUnitario;   // precio con IGV incluido
    private BigDecimal  subtotal;         // cantidad * precioUnitario


    public ItemComprobante(String descripcion, Double cantidad, BigDecimal precioUnitario) {
        this.descripcion     = descripcion;
        this.cantidad        = cantidad;
        this.precioUnitario  = precioUnitario.setScale(2, RoundingMode.HALF_UP);
        this.subtotal        = precioUnitario
                                   .multiply(new BigDecimal(cantidad))
                                   .setScale(2, RoundingMode.HALF_UP);
    }


}
