package pe.edu.upeu.ventaautos.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pe.edu.upeu.ventaautos.model.Venta;

public class VentaService {
    private final ObservableList<Venta> ventas = FXCollections.observableArrayList();
    public ObservableList<Venta> listar() { return ventas; }
    public void registrar(Venta venta) { ventas.add(venta); }
}