package pe.edu.upeu.sysventas.repository;

import pe.edu.upeu.sysventas.model.UnidMedida;

public class UnidadMedidaRepository extends AbstractJpRepository<UnidMedida, Long> {
    private long sequence=1;


    @Override
    protected Long getId(UnidMedida entity) {
        return 0L;
    }

    @Override
    protected void setId(UnidMedida entity, Long aLong) {

    }

    @Override
    protected Long generateId() {
        return 0L;
    }
}

