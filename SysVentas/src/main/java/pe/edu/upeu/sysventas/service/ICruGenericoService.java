package pe.edu.upeu.sysventas.service;

import java.util.List;

public interface ICruGenericoService <T,ID>{
    T save(T t );
    T update(ID id,T t);
    List<T> findAll();
    T findbyid (ID id);
    void delete(ID id);


}
