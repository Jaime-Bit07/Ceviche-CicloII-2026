package pe.edu.upeu.sysventas.cinebasic;

import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class CandyCBasic extends Application {

    @Override
    public void start(Stage stage) {
        TableView<CandyBasic> tabla = new TableView<>();

        TableColumn<CandyBasic, String> colNombre =
                new TableColumn<>("Producto");
        colNombre.setCellValueFactory(
                dato -> new SimpleStringProperty(
                        dato.getValue().getNombre()
                )
        );

        TableColumn<CandyBasic, String> colTipo =
                new TableColumn<>("Tipo");
        colTipo.setCellValueFactory(
                dato -> new SimpleStringProperty(
                        dato.getValue().getTipo()
                )
        );

        TableColumn<CandyBasic, Double> colPrecio =
                new TableColumn<>("Precio");
        colPrecio.setCellValueFactory(
                dato -> new SimpleObjectProperty<>(
                        dato.getValue().getPrecio()
                )
        );

        TableColumn<CandyBasic, Integer> colStock =
                new TableColumn<>("Stock");
        colStock.setCellValueFactory(
                dato -> new SimpleIntegerProperty(
                        dato.getValue().getStock()
                ).asObject()
        );

        tabla.getColumns().addAll(
                colNombre,
                colTipo,
                colPrecio,
                colStock
        );

        tabla.getItems().addAll(
                new CandyBasic(
                        1L, "Snicker", "Chocolate", 5.00, 20
                ),
                new CandyBasic(
                        2L, "Popcorn", "Popcorn", 12.50, 30
                ),
                new CandyBasic(
                        3L, "Nachos", "Comida", 10.00, 15
                )
        );

        Scene escena = new Scene(tabla, 650, 300);

        stage.setTitle("Candy Básico");
        stage.setScene(escena);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
