package pe.edu.upeu.genericos;

import java.util.ArrayList;
import java.util.List;

public class Repositorio<T> {
    private final List<T> elementos = new ArrayList<>();
    public void guardar(T elemento) {
        elementos.add(elemento);
    }
    public List<T> listarTodos() {
        return List.copyOf(elementos); // copia defensiva — Capítulo 16
    }
    public boolean eliminar(T elemento) {
        return elementos.remove(elemento); // usa equals() — Capítulo 15
    }
    public int total() {
        return elementos.size();
    }

}
