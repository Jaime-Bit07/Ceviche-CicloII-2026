package pe.edu.upeu.sysventas.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Screen;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import pe.edu.upeu.sysventas.SysVentas;
import pe.edu.upeu.sysventas.config.AppContext;
import pe.edu.upeu.sysventas.model.Usuario;
import pe.edu.upeu.sysventas.service.IUsuarioService;

@RequiredArgsConstructor
public class LoginController {

    final private IUsuarioService us;

    @FXML
    TextField txtUsuario;

    @FXML
    PasswordField txtClave;

    @FXML
    public void login(ActionEvent e){
        us.loginUsuario(txtUsuario.getText(), txtClave.getText()).ifPresentOrElse(
                usuario -> { abrirPantalla(e, usuario); },
                ()->{
                    System.out.println("Error en las credenciales...");
                }
        );

    }

    @FXML
    public void logout(ActionEvent e){
        Platform.exit();
        System.exit(0);
    }


    public void abrirPantalla(ActionEvent e, Usuario u){
        try {

            Screen screen=Screen.getPrimary();

            AppContext appContext=AppContext.getInstance();
            Rectangle2D dimen=screen.getBounds();

            FXMLLoader fxmlLoader = new FXMLLoader(SysVentas.class.getResource("/view/maingui.fxml"));
            fxmlLoader.setControllerFactory(appContext::getBean);
            Scene scene = new Scene(fxmlLoader.load(), dimen.getWidth(), dimen.getHeight()-30);
            scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());

            Stage stage= (Stage) ((Node)e.getSource()).getScene().getWindow();

            stage.setTitle("SysVentas Principal!");
            stage.setX(dimen.getMinX());
            stage.setY(dimen.getMinY());

            stage.setScene(scene);
            stage.setResizable(true);
            stage.show();


        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

    }


}
