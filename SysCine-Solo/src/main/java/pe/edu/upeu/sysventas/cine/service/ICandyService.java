package pe.edu.upeu.sysventas.cine.service;

import pe.edu.upeu.sysventas.cine.model.Candy;

import java.util.List;

public interface ICandyService {

    Candy crear(String nombre, String tipo, Double precio, Integer stock);

    List<Candy> listar();

    Candy actualizar(Long id, String nombre, String tipo, Double precio, Integer stock);

    void eliminar(Long id);
}
