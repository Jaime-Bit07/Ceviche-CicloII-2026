package pe.edu.upeu.sysventas.cinerun;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

//Herencia/relacion de generalizacion, Syscine es hija, Application
public class SysCine extends Application {

    @Override
    //Polimorfismo de application
    public void start(Stage stage) throws Exception {
        Parent vista = FXMLLoader.load(
                getClass().getResource("/cineview/main_cine.fxml")
        );

        Scene escena = new Scene(vista, 600, 400);

        stage.setTitle("SysCine - Candy");
        stage.setScene(escena);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
