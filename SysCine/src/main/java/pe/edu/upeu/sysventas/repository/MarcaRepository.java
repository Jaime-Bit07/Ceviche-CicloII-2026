package pe.edu.upeu.sysventas.repository;

import pe.edu.upeu.sysventas.model.Marca;

public class MarcaRepository extends AbstractJpaRepository<Marca, Long>{
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

    public void seedData() {
        if (findAll().isEmpty()) {
            save(new Marca(generateId(), "Samsung"));
            save(new Marca(generateId(),"LG"));
            save(new Marca(generateId(),"Sony"));
            save(new Marca(generateId(),"HP"));
            save(new Marca(generateId(),"Lenovo"));
        }
    }
}
