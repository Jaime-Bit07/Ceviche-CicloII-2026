package pe.edu.upeu.sysventas.service.impl;

import pe.edu.upeu.sysventas.model.VentCarrito;
import pe.edu.upeu.sysventas.repository.ICrudGenericoRepository;
import pe.edu.upeu.sysventas.repository.VentCarritoRepository;
import pe.edu.upeu.sysventas.service.IVentaCarritoService;

import java.util.List;

public class VentaCarritoServiceImp extends CrudGenericoServiceImp<VentCarrito, Long>
        implements IVentaCarritoService {

    private final VentCarritoRepository ventCarritoRepository;

    public VentaCarritoServiceImp(VentCarritoRepository ventCarritoRepository) {
        this.ventCarritoRepository = ventCarritoRepository;
    }

    @Override
    protected ICrudGenericoRepository<VentCarrito, Long> getRepo() {
        return ventCarritoRepository;
    }

    @Override
    public List<VentCarrito> listaCarritoCliente(String dni) {
        return ventCarritoRepository.listaCarritoCliente(dni);
    }

    @Override
    public void deleteCarAll(String dniruc) {
        ventCarritoRepository.deleteByDniruc(dniruc);
    }
}
