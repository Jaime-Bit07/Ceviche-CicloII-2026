package pe.edu.upeu.sysventas.repository;

import pe.edu.upeu.sysventas.model.Marca;

public class MarcaRepository extends AbstractJpRepository<Marca, Long>{
    private long sequence=1;


    @Override
    protected Long getId(Marca entity) {
        return entity.getIdMarca();
    }

    @Override
    protected void setId(Marca entity, Long id) {
        entity.setIdMarca(id);

    }

    @Override
    protected Long generateId() {
        return sequence++;
    }

    @Override
    public Marca update(Long aLong, Marca marca) {
        return null;
    }

    @Override
    public void delete(Long aLong) {

    }
}
