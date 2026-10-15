package pe.edu.upeu.sysventas.cinerun;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pe.edu.upeu.sysventas.config.AppCandyContext;

//Herencia/relacion de generalizacion, Syscine es hija, Application
public class SysCine extends Application {

    @Override
    //Polimorfismo de application
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/cineview/main_cine.fxml")
        );
        loader.setControllerFactory(AppCandyContext.getInstance()::getBean);
        Parent vista = loader.load();

        Scene escena = new Scene(vista, 600, 400);

        stage.setTitle("SysCine - Candy");
        stage.setScene(escena);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
