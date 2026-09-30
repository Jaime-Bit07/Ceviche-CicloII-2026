package pe.edu.upeu.ventaautos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class VentaAutosApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(VentaAutosApp.class.getResource("/View/main_venta_autos.fxml"));
        Scene scene = new Scene(loader.load(), 1180, 760);
        scene.getStylesheets().add(VentaAutosApp.class.getResource("/css/style.css").toExternalForm());
        stage.setTitle("AutoNova | Gestión de ventas");
        stage.setMinWidth(980);
        stage.setMinHeight(680);
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) { launch(args); }
}