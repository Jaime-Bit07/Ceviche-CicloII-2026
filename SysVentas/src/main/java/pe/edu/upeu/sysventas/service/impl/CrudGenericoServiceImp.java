package pe.edu.upeu.sysventas.service.impl;

import pe.edu.upeu.sysventas.exception.ModelNotFoundException;
import pe.edu.upeu.sysventas.repository.ICrudGenericoRepository;
import pe.edu.upeu.sysventas.service.ICrudGenericoService;

import java.util.List;
import java.util.Optional;

public abstract class CrudGenericoServiceImp<T, ID> implements ICrudGenericoService<T, ID> {

    protected abstract ICrudGenericoRepository<T, ID> getRepo();

    @Override
    public T save(T t) {
        return getRepo().save(t);
    }

    @Override
    public T update(ID id, T t) {
        if(getRepo().existsById(id)){
            throw new ModelNotFoundException("ID no existe:"+id);
        }
        return getRepo().update(t);
    }

    @Override
    public T findById(ID id) {
        return getRepo().findById(id).orElseThrow(()->new ModelNotFoundException("el id no existe "+id));
    }

    @Override
    public List<T> findAll() {
        return getRepo().findAll();
    }

    @Override
    public void delete(ID id) {
        if(!getRepo().existsById(id)){
            throw new ModelNotFoundException("ID no existe:"+id);
        }
        getRepo().deleteById(id);

    }
}
