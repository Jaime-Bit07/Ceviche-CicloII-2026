package pe.edu.upeu.sysventas.service.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import pe.edu.upeu.sysventas.dto.comprobante.Comprobante;
//import pe.edu.upeu.sysventas.dto.comprobante.ItemComprobante;
import pe.edu.upeu.sysventas.model.Venta;
import pe.edu.upeu.sysventas.model.VentaDetalle;
import pe.edu.upeu.sysventas.repository.ICrudGenericoRepository;
import pe.edu.upeu.sysventas.repository.VentaRepository;
import pe.edu.upeu.sysventas.service.IVentaService;

import javax.sql.DataSource;
import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class VentaServiceImp extends CrudGenericoServiceImp<Venta, Long>
        implements IVentaService {

    private static final Logger logger = LoggerFactory.getLogger(VentaServiceImp.class);

    private final VentaRepository ventaRepository;
    private final DataSource dataSource;

    public VentaServiceImp(VentaRepository ventaRepository, DataSource dataSource) {
        this.ventaRepository = ventaRepository;
        this.dataSource = dataSource;
    }

    @Override
    protected ICrudGenericoRepository<Venta, Long> getRepo() {
        return ventaRepository;
    }

    @Override
    public File getFile(String filex) {
        File newFolder = new File("jasper");
        Path camino = Paths.get(newFolder.getAbsolutePath() + "/" + filex);
        logger.debug("Ruta Jasper: {}", camino.toAbsolutePath());
        return camino.toFile();
    }

   /* @Override
    public Comprobante generarComprobante(Venta venta){
        Comprobante b = new Comprobante();
        b.setRucEmisor("10436319172");
        b.setRazonSocial("SysCenterLife S.A.C.");
        b.setDireccion("Av. La Torre  - Juliaca");
        b.setUbigeo("Puno, Juliaca, Cercado");
        b.setTelefono("951782520");

        b.setTipoDocumento("BOLETA DE VENTA ELECTRÓNICA");
        b.setSerie("B001");
        b.setNumero("00000042");
        b.setFechaEmision(venta.getFechaGener().toString());
        b.setMoneda("PEN");

        b.setTipoDocCliente(venta.getDniruc().getTipoDocumento().name());
        b.setNroDocCliente(venta.getDniruc().getDniruc());
        b.setNombreCliente(venta.getDniruc().getNombres());
        b.setDireccionCliente("Av. Sangalli 1234");

        List<ItemComprobante> items = new ArrayList<>();

        for (VentaDetalle vd : venta.getDetalleVenta()) {
            items.add(new ItemComprobante(vd.getIdProducto().getNombre(), vd.getCantidad(), new BigDecimal(vd.getSubtotal())));
        }
        b.setItems(items);
        // Calcular totales
        BigDecimal total = items.stream()
                .map(ItemComprobante::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        b.setOpGravadas(total.divide(new BigDecimal("1.18"), 2, RoundingMode.HALF_UP));
        b.setIgv(total.subtract(b.getOpGravadas()).setScale(2, RoundingMode.HALF_UP));
        b.setTotal(total.setScale(2, RoundingMode.HALF_UP));
        return b;
    }*/


}
