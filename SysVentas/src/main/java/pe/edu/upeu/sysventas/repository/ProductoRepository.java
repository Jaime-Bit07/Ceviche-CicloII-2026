package pe.edu.upeu.sysventas.repository;

import pe.edu.upeu.sysventas.enums.TipoProducto;
import pe.edu.upeu.sysventas.model.Categoria;
import pe.edu.upeu.sysventas.model.Marca;
import pe.edu.upeu.sysventas.model.Producto;
import pe.edu.upeu.sysventas.model.UnidMedida;

public class ProductoRepository extends AbstractJpaRepository<Producto, Long>{
    private long sequence=1;
    @Override
    protected Long getId(Producto entity) {
        return entity.getIdProducto();
    }

    @Override
    protected void setId(Producto entity, Long id) {
        entity.setIdProducto(id);
    }

    @Override
    protected Long generateId() {
        return sequence++;
    }

    public void seedData() {
        if (findAll().isEmpty()) {
            Marca marca=new Marca();
            marca.setIdMarca(1L);

            UnidMedida unidMedida=new UnidMedida();
            unidMedida.setIdUnidad(1L);

            Categoria categoria=new Categoria();
            categoria.setIdCategoria(1L);

            save(new Producto(generateId(), "Televisor", TipoProducto.PRODUCTO,
                    0.0,0.0,0.0,0.0, 0.0,categoria, marca, unidMedida ));
        }
    }

}
