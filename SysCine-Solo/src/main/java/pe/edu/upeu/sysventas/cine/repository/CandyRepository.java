package pe.edu.upeu.sysventas.cine.repository;

import pe.edu.upeu.sysventas.cine.model.Candy;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CandyRepository implements ICandyRepository {

    private final List<Candy> candies = new ArrayList<>();
    private long nextId = 1;

    @Override
    public Candy save(Candy candy) {
        candy.setIdCandy(nextId++);
        candies.add(candy);
        return candy;
    }

    @Override
    public List<Candy> findAll() {
        return new ArrayList<>(candies);
    }

    @Override
    public Optional<Candy> findById(Long id) {
        return candies.stream()
                .filter(candy -> candy.getIdCandy().equals(id))
                .findFirst();
    }

    @Override
    public Candy update(Candy candy) {
        return findById(candy.getIdCandy())
                .map(existing -> {
                    existing.setNombre(candy.getNombre());
                    existing.setTipo(candy.getTipo());
                    existing.setPrecio(candy.getPrecio());
                    existing.setStock(candy.getStock());
                    return existing;
                })
                .orElseThrow(() -> new IllegalArgumentException(
                        "No se encontró la golosina con ID " + candy.getIdCandy()
                ));
    }

    @Override
    public boolean deleteById(Long id) {
        return candies.removeIf(candy -> candy.getIdCandy().equals(id));
    }
}
