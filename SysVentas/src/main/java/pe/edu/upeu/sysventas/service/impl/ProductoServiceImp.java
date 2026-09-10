package pe.edu.upeu.sysventas.service.impl;

import pe.edu.upeu.sysventas.model.Producto;
import pe.edu.upeu.sysventas.repository.ProductoRepository;
import pe.edu.upeu.sysventas.repository.ICrudGenericoRepository;
import pe.edu.upeu.sysventas.service.IProductoService;


public class ProductoServiceImp extends CrudGenericoServiceImp<Producto, Long>
        implements IProductoService {

    private final ProductoRepository productoRepository;

    public ProductoServiceImp(ProductoRepository categoriaRepository) {
        this.productoRepository = categoriaRepository;
    }

    @Override
    protected ICrudGenericoRepository<Producto, Long> getRepo() {
        return productoRepository;
    }


}