package pe.edu.upeu.ventaautos.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pe.edu.upeu.ventaautos.model.Vehiculo;

public class VehiculoService {
    private final ObservableList<Vehiculo> inventario = FXCollections.observableArrayList(
            new Vehiculo("A1B-245", "Toyota", "Corolla", 2024, 24900, 3),
            new Vehiculo("B7C-810", "Hyundai", "Tucson", 2023, 32900, 2),
            new Vehiculo("C3D-521", "Kia", "Rio", 2022, 17900, 4));
    public ObservableList<Vehiculo> listar() { return inventario; }
    public void agregar(Vehiculo vehiculo) { inventario.add(vehiculo); }
    public void eliminar(Vehiculo vehiculo) { inventario.remove(vehiculo); }
}