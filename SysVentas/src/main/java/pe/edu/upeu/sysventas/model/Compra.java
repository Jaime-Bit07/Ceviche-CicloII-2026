package pe.edu.upeu.sysventas.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Compra extends DocumentoComercial {

    //private Long idCompra;
    //private Double precioBase;
    //private Double igv;
    //private Double preciototal;
    private Proveedor idProveedor;
    private Usuario idUsuario;
    //private String serie;
    //private String numDoc;
    private LocalDate fechaComp;
    //private String tipoDoc;
    private LocalDate fechaReg;
    private List<CompraDetalle> detalleCompra;

    @Override
    public double calcularTotal() {
        return getPrecioTotal()+getIgv();
    }
}