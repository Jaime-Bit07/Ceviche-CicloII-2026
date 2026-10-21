package pe.edu.upeu.sysventas.service;

//import pe.edu.upeu.sysventas.dto.comprobante.Comprobante;
import pe.edu.upeu.sysventas.model.Venta;

import java.io.File;

public interface IVentaService extends ICrudGenericoService<Venta, Long>{
    File getFile(String filex);
    /*JasperPrint runReport(Long idv) throws JRException, SQLException;
    JasperPrint runReportVentas(String fInicio, String ffinal) throws JRException, SQLException;*/
    //Comprobante generarComprobante(Venta venta);

}
