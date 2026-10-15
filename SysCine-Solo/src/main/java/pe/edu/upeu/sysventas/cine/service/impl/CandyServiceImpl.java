package pe.edu.upeu.sysventas.cine.service.impl;

import pe.edu.upeu.sysventas.cine.model.Candy;
import pe.edu.upeu.sysventas.cine.repository.ICandyRepository;
import pe.edu.upeu.sysventas.cine.service.ICandyService;
import pe.edu.upeu.sysventas.exception.CandyValidationException;
import pe.edu.upeu.sysventas.exception.ModelNotFoundException;

import java.util.List;

public class CandyServiceImpl implements ICandyService {

    private final ICandyRepository candyRepository;

    public CandyServiceImpl(ICandyRepository candyRepository) {
        this.candyRepository = candyRepository;
    }

    @Override
    public Candy crear(String nombre, String tipo, Double precio, Integer stock) {
        validarDatos(nombre, tipo, precio, stock);
        Candy candy = new Candy(null, nombre.trim(), tipo.trim(), precio, stock);
        return candyRepository.save(candy);
    }

    @Override
    public List<Candy> listar() {
        return candyRepository.findAll();
    }

    @Override
    public Candy actualizar(Long id, String nombre, String tipo, Double precio, Integer stock) {
        validarDatos(nombre, tipo, precio, stock);
        Candy candy = candyRepository.findById(id)
                .orElseThrow(() -> new ModelNotFoundException(
                        "No se encontró la golosina con ID " + id
                ));

        candy.setNombre(nombre.trim());
        candy.setTipo(tipo.trim());
        candy.setPrecio(precio);
        candy.setStock(stock);
        return candyRepository.update(candy);
    }

    @Override
    public void eliminar(Long id) {
        if (!candyRepository.deleteById(id)) {
            throw new ModelNotFoundException(
                    "No se encontró la golosina con ID " + id
            );
        }
    }

    private void validarDatos(String nombre, String tipo, Double precio, Integer stock) {
        if (nombre == null || nombre.isBlank()) {
            throw new CandyValidationException("El nombre es obligatorio.");
        }
        if (tipo == null || tipo.isBlank()) {
            throw new CandyValidationException("El tipo es obligatorio.");
        }
        if (precio == null || !Double.isFinite(precio) || precio < 0) {
            throw new CandyValidationException("El precio debe ser un número válido y no negativo.");
        }
        if (stock == null || stock < 0) {
            throw new CandyValidationException("El stock debe ser un entero no negativo.");
        }
    }
}
