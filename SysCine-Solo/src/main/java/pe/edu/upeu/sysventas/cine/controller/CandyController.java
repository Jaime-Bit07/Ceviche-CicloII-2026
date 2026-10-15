package pe.edu.upeu.sysventas.cine.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import pe.edu.upeu.sysventas.cine.model.Candy;
import pe.edu.upeu.sysventas.cine.service.ICandyService;
import pe.edu.upeu.sysventas.exception.CandyValidationException;
import pe.edu.upeu.sysventas.exception.ModelNotFoundException;

public class CandyController {

    private final ICandyService candyService;

    @FXML private TableView<Candy> tablaCandys;
    @FXML private TableColumn<Candy, String> colNombre;
    @FXML private TableColumn<Candy, String> colTipo;
    @FXML private TableColumn<Candy, Double> colPrecio;
    @FXML private TableColumn<Candy, Integer> colStock;
    @FXML private TextField txtNombre;
    @FXML private TextField txtTipo;
    @FXML private TextField txtPrecio;
    @FXML private TextField txtStock;
    @FXML private Label lblMensaje;

    private ObservableList<Candy> productos;

    public CandyController(ICandyService candyService) {
        this.candyService = candyService;
    }

    @FXML
    public void initialize() {
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colStock.setCellValueFactory(new PropertyValueFactory<>("stock"));

        productos = FXCollections.observableArrayList(candyService.listar());
        tablaCandys.setItems(productos);

        tablaCandys.getSelectionModel().selectedItemProperty()
                .addListener((observable, anterior, seleccionado) -> {
                    if (seleccionado != null) {
                        txtNombre.setText(seleccionado.getNombre());
                        txtTipo.setText(seleccionado.getTipo());
                        txtPrecio.setText(String.valueOf(seleccionado.getPrecio()));
                        txtStock.setText(String.valueOf(seleccionado.getStock()));
                    }
                });
    }

    @FXML
    public void crearCandy() {
        try {
            Candy candy = candyService.crear(
                    txtNombre.getText(),
                    txtTipo.getText(),
                    Double.valueOf(txtPrecio.getText().trim()),
                    Integer.valueOf(txtStock.getText().trim())
            );
            productos.add(candy);
            mostrarMensaje("Golosina creada correctamente.");
            limpiar();
        } catch (NumberFormatException e) {
            mostrarMensaje("Precio y stock deben ser números válidos.");
        } catch (CandyValidationException e) {
            mostrarMensaje(e.getMessage());
        }
    }

    @FXML
    public void actualizarCandy() {
        Candy seleccionado = tablaCandys.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarMensaje("Selecciona una golosina.");
            return;
        }

        try {
            candyService.actualizar(
                    seleccionado.getIdCandy(),
                    txtNombre.getText(),
                    txtTipo.getText(),
                    Double.valueOf(txtPrecio.getText().trim()),
                    Integer.valueOf(txtStock.getText().trim())
            );
            tablaCandys.refresh();
            mostrarMensaje("Golosina actualizada correctamente.");
        } catch (NumberFormatException e) {
            mostrarMensaje("Precio y stock deben ser números válidos.");
        } catch (CandyValidationException | ModelNotFoundException e) {
            mostrarMensaje(e.getMessage());
        }
    }

    @FXML
    public void eliminarCandy() {
        Candy seleccionado = tablaCandys.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarMensaje("Selecciona una golosina.");
            return;
        }

        try {
            candyService.eliminar(seleccionado.getIdCandy());
            productos.remove(seleccionado);
            mostrarMensaje("Golosina eliminada correctamente.");
            limpiar();
        } catch (ModelNotFoundException e) {
            mostrarMensaje(e.getMessage());
        }
    }

    @FXML
    private void limpiar() {
        txtNombre.clear();
        txtTipo.clear();
        txtPrecio.clear();
        txtStock.clear();
        tablaCandys.getSelectionModel().clearSelection();
    }

    private void mostrarMensaje(String mensaje) {
        lblMensaje.setText(mensaje);
    }
}
