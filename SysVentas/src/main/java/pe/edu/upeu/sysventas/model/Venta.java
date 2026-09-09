package pe.edu.upeu.sysventas.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Venta extends DocumentoComercial{
    //private Long idVenta;
    //private Double preciobase;
    //private Double igv;
    //private Double preciototal;
    private Cliente dniruc;
    private Usuario idUsuario;
    //private String numDoc;
    private LocalDateTime fechaGener;
    //private String serie;
    //private String tipoDoc;
    private List<VentaDetalle> detalleVenta;

    @Override
    public double calcularTotal() {
        return getPrecioTotal()+getIgv();
    }
}