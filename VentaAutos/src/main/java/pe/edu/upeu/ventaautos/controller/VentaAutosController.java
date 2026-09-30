package pe.edu.upeu.ventaautos.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import pe.edu.upeu.ventaautos.model.Vehiculo;
import pe.edu.upeu.ventaautos.model.Venta;
import pe.edu.upeu.ventaautos.service.VehiculoService;
import pe.edu.upeu.ventaautos.service.VentaService;

import java.time.LocalDate;
import java.util.Locale;

public class VentaAutosController {
    @FXML private TextField txtPlaca, txtMarca, txtModelo, txtAnio, txtPrecio, txtStock, txtBuscar, txtCliente;
    @FXML private TableView<Vehiculo> tablaVehiculos;
    @FXML private TableColumn<Vehiculo, String> colPlaca, colMarca, colModelo;
    @FXML private TableColumn<Vehiculo, Integer> colAnio, colStock;
    @FXML private TableColumn<Vehiculo, Double> colPrecio;
    @FXML private TableView<Venta> tablaVentas;
    @FXML private TableColumn<Venta, String> colCodigoVenta, colClienteVenta, colVehiculoVenta;
    @FXML private TableColumn<Venta, LocalDate> colFechaVenta;
    @FXML private TableColumn<Venta, Integer> colCantidadVenta;
    @FXML private TableColumn<Venta, Double> colTotalVenta;
    @FXML private Label lblResumenAutos, lblResumenVentas;

    private final VehiculoService vehiculoService = new VehiculoService();
    private final VentaService ventaService = new VentaService();
    private Vehiculo seleccionado;

    @FXML private void initialize() {
        colPlaca.setCellValueFactory(new PropertyValueFactory<>("placa"));
        colMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        colModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        colAnio.setCellValueFactory(new PropertyValueFactory<>("anio"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colCodigoVenta.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colFechaVenta.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        colClienteVenta.setCellValueFactory(new PropertyValueFactory<>("cliente"));
        colVehiculoVenta.setCellValueFactory(new PropertyValueFactory<>("vehiculo"));
        colCantidadVenta.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        colTotalVenta.setCellValueFactory(new PropertyValueFactory<>("total"));
        tablaVehiculos.setItems(vehiculoService.listar());
        tablaVentas.setItems(ventaService.listar());
        tablaVehiculos.getSelectionModel().selectedItemProperty().addListener((obs, old, value) -> cargar(value));
        txtBuscar.textProperty().addListener((obs, old, value) -> filtrar(value));
        actualizarResumen();
    }

    @FXML private void guardarVehiculo() {
        try {
            String placa = txtPlaca.getText().trim().toUpperCase(Locale.ROOT);
            String marca = txtMarca.getText().trim();
            String modelo = txtModelo.getText().trim();
            if (placa.isEmpty() || marca.isEmpty() || modelo.isEmpty()) {
                aviso(Alert.AlertType.WARNING, "Completa placa, marca y modelo."); return;
            }
            if (vehiculoService.listar().stream().anyMatch(v -> v != seleccionado && v.getPlaca().equalsIgnoreCase(placa))) {
                aviso(Alert.AlertType.WARNING, "Ya existe un vehículo con esa placa."); return;
            }
            int anio = Integer.parseInt(txtAnio.getText().trim());
            double precio = Double.parseDouble(txtPrecio.getText().trim());
            int stock = Integer.parseInt(txtStock.getText().trim());
            if (anio < 1900 || anio > LocalDate.now().getYear() + 1 || precio <= 0 || stock < 0) {
                aviso(Alert.AlertType.WARNING, "Revisa el año, el precio y el stock ingresados."); return;
            }
            if (seleccionado == null) vehiculoService.agregar(new Vehiculo(placa, marca, modelo, anio, precio, stock));
            else {
                seleccionado.setPlaca(placa); seleccionado.setMarca(marca); seleccionado.setModelo(modelo);
                seleccionado.setAnio(anio); seleccionado.setPrecio(precio); seleccionado.setStock(stock);
                tablaVehiculos.refresh(); tablaVentas.refresh();
            }
            limpiar(); filtrar(txtBuscar.getText()); actualizarResumen();
        } catch (NumberFormatException ex) {
            aviso(Alert.AlertType.WARNING, "Año, precio y stock deben ser valores numéricos.");
        }
    }

    @FXML private void eliminarVehiculo() {
        if (seleccionado == null) { aviso(Alert.AlertType.INFORMATION, "Selecciona un vehículo del inventario."); return; }
        vehiculoService.eliminar(seleccionado); limpiar(); filtrar(txtBuscar.getText()); actualizarResumen();
    }

    @FXML private void nuevoVehiculo() { limpiar(); }

    @FXML private void registrarVenta() {
        Vehiculo vehiculo = tablaVehiculos.getSelectionModel().getSelectedItem();
        String cliente = txtCliente.getText().trim();
        if (vehiculo == null) { aviso(Alert.AlertType.INFORMATION, "Selecciona el vehículo que deseas vender en la pestaña Inventario."); return; }
        if (cliente.isEmpty()) { aviso(Alert.AlertType.WARNING, "Ingresa el nombre del cliente."); return; }
        if (vehiculo.getStock() < 1) { aviso(Alert.AlertType.WARNING, "No hay unidades disponibles de este vehículo."); return; }
        vehiculo.setStock(vehiculo.getStock() - 1);
        int numero = ventaService.listar().size() + 1;
        ventaService.registrar(new Venta(String.format("V-%04d", numero), LocalDate.now(), cliente,
                vehiculo.getMarca() + " " + vehiculo.getModelo(), 1, vehiculo.getPrecio()));
        tablaVehiculos.refresh(); txtCliente.clear(); actualizarResumen();
        aviso(Alert.AlertType.INFORMATION, "Venta registrada correctamente.");
    }

    private void cargar(Vehiculo vehiculo) {
        seleccionado = vehiculo;
        if (vehiculo == null) return;
        txtPlaca.setText(vehiculo.getPlaca()); txtMarca.setText(vehiculo.getMarca());
        txtModelo.setText(vehiculo.getModelo()); txtAnio.setText(String.valueOf(vehiculo.getAnio()));
        txtPrecio.setText(String.valueOf(vehiculo.getPrecio())); txtStock.setText(String.valueOf(vehiculo.getStock()));
    }
    private void limpiar() {
        seleccionado = null; tablaVehiculos.getSelectionModel().clearSelection();
        txtPlaca.clear(); txtMarca.clear(); txtModelo.clear(); txtAnio.clear(); txtPrecio.clear(); txtStock.clear();
    }
    private void filtrar(String texto) {
        String q = texto == null ? "" : texto.trim().toLowerCase(Locale.ROOT);
        tablaVehiculos.setItems(FXCollections.observableArrayList(vehiculoService.listar().stream().filter(v -> q.isEmpty()
                || v.getPlaca().toLowerCase(Locale.ROOT).contains(q) || v.getMarca().toLowerCase(Locale.ROOT).contains(q)
                || v.getModelo().toLowerCase(Locale.ROOT).contains(q)).toList()));
    }
    private void actualizarResumen() {
        lblResumenAutos.setText(String.valueOf(vehiculoService.listar().size()));
        lblResumenVentas.setText(String.valueOf(ventaService.listar().size()));
    }
    private void aviso(Alert.AlertType tipo, String mensaje) {
        Alert alerta = new Alert(tipo); alerta.setTitle("AutoNova"); alerta.setHeaderText(null);
        alerta.setContentText(mensaje); alerta.showAndWait();
    }
}