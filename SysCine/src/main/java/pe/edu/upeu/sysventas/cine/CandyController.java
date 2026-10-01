package pe.edu.upeu.sysventas.cine;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
//Dependencias
import pe.edu.upeu.sysventas.cine.candycbody.CandyCreate;
import pe.edu.upeu.sysventas.cine.candycbody.CandyDelete;
import pe.edu.upeu.sysventas.cine.candycbody.CandyRead;
import pe.edu.upeu.sysventas.cine.candycbody.CandyUpdate;

public class CandyController {
    @FXML
    private TableView<Candy> tablaCandys;
    @FXML
    private TableColumn<Candy, String> colNombre;
    @FXML
    private TableColumn<Candy, String> colTipo;
    @FXML
    private TableColumn<Candy, Double> colPrecio;
    @FXML
    private TableColumn<Candy, Integer> colStock;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtTipo;
    @FXML
    private TextField txtPrecio;
    @FXML
    private TextField txtStock;
    @FXML
    private Label lblMensaje;

    //Dependencias
    private final CandyCreate candyCreate = new CandyCreate();
    private final CandyRead candyRead = new CandyRead();
    private final CandyUpdate candyUpdate = new CandyUpdate();
    private final CandyDelete candyDelete = new CandyDelete();

    //Agregacion
    private ObservableList<Candy> productos;

    @FXML
    public void initialize() {
        colNombre.setCellValueFactory(
                new PropertyValueFactory<>("nombre")
        );

        colTipo.setCellValueFactory(
                new PropertyValueFactory<>("tipo")
        );

        colPrecio.setCellValueFactory(
                new PropertyValueFactory<>("precio")
        );

        colStock.setCellValueFactory(
                new PropertyValueFactory<>("stock")
        );

        productos = FXCollections.observableArrayList(
                candyRead.obtenerTodos()
        );

        tablaCandys.setItems(productos);

        tablaCandys.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, anterior, seleccionado) -> {
                    if (seleccionado != null) {
                        txtNombre.setText(seleccionado.getNombre());
                        txtTipo.setText(seleccionado.getTipo());
                        txtPrecio.setText(
                                String.valueOf(seleccionado.getPrecio())
                        );
                        txtStock.setText(
                                String.valueOf(seleccionado.getStock())
                        );
                    }
                });
    }

    @FXML
    public void crearCandy() {
        if (!validarCampos()) {
            return;
        }

        try {
            Candy nuevo = candyCreate.crearCandy(
                    (long) (productos.size() + 1),
                    txtNombre.getText(),
                    txtTipo.getText(),
                    Double.parseDouble(txtPrecio.getText()),
                    Integer.parseInt(txtStock.getText())
            );

            productos.add(nuevo);
            lblMensaje.setText("Golosina creada correctamente.");
            limpiar();

        } catch (NumberFormatException e) {
            lblMensaje.setText(
                    "Precio y stock deben ser números válidos."
            );
        }
    }

    @FXML
    public void actualizarCandy() {
        Candy seleccionado =
                tablaCandys.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            lblMensaje.setText(
                    "Selecciona una golosina."
            );
            return;
        }

        if (!validarCampos()) {
            return;
        }

        try {
            candyUpdate.actualizarCandy(
                    seleccionado,
                    txtNombre.getText(),
                    txtTipo.getText(),
                    Double.parseDouble(txtPrecio.getText()),
                    Integer.parseInt(txtStock.getText())
            );

            tablaCandys.refresh();
            lblMensaje.setText(
                    "Golosina actualizada correctamente."
            );

        } catch (NumberFormatException e) {
            lblMensaje.setText(
                    "Precio y stock deben ser números válidos."
            );
        }
    }

    @FXML
    public void eliminarCandy() {
        Candy seleccionado =
                tablaCandys.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            lblMensaje.setText(
                    "Selecciona una golosina."
            );
            return;
        }

        candyDelete.eliminarCandy(productos, seleccionado);
        lblMensaje.setText(
                "Golosina eliminada correctamente."
        );
        limpiar();
    }

    private boolean validarCampos() {
        if (txtNombre.getText().isBlank()
                || txtTipo.getText().isBlank()
                || txtPrecio.getText().isBlank()
                || txtStock.getText().isBlank()) {

            lblMensaje.setText(
                    "Completa todos los campos."
            );
            return false;
        }

        try {
            double precio =
                    Double.parseDouble(txtPrecio.getText());

            int stock =
                    Integer.parseInt(txtStock.getText());

            if (precio < 0 || stock < 0) {
                lblMensaje.setText(
                        "Precio y stock no pueden ser negativos."
                );
                return false;
            }

        } catch (NumberFormatException e) {
            lblMensaje.setText(
                    "Precio y stock deben ser números."
            );
            return false;
        }

        return true;
    }

    @FXML
    private void limpiar() {
        txtNombre.clear();
        txtTipo.clear();
        txtPrecio.clear();
        txtStock.clear();

        tablaCandys.getSelectionModel()
                .clearSelection();
    }
}
