package pe.edu.upeu.sysventas.cine.repository;

import pe.edu.upeu.sysventas.cine.model.Candy;

import java.util.List;
import java.util.Optional;

public interface ICandyRepository {

    Candy save(Candy candy);

    List<Candy> findAll();

    Optional<Candy> findById(Long id);

    Candy update(Candy candy);

    boolean deleteById(Long id);
}
