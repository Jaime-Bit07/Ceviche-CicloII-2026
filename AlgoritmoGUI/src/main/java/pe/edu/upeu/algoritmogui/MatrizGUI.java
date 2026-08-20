package pe.edu.upeu.algoritmogui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.swing.*;

public class MatrizGUI extends Application {
    Label informacion;
    GridPane grid;

    public static void main(String[] args) {
        launch(args);

      }

    @Override
    public void start(Stage primaryStage) {
    Label titulo=new Label("MATRIZ - FORMAS");
    Label lbnTm=new Label("Tamaño de la Matriz");
    Spinner<Integer> spTm=new Spinner<>(2, 30,5);

        Label lbnNumI=new Label("Numero de Inicio: ");
        Spinner<Integer> spNumI=new Spinner<>(0, 30,0);
        Button btnGenerar= new Button("Generar Matriz");

        HBox hb=new HBox(10, lbnTm, spTm,lbnNumI,spNumI,btnGenerar);
        grid = new GridPane();
        grid.setVgap(3);
        grid.setVgap(3);

        VBox vb=new VBox(15,titulo,hb,grid,informacion);

        ScrollPane cp=new ScrollPane(vb);
        primaryStage.setScene(new Scene(cp));
        primaryStage.setTitle("Formas Matriciales");
        primaryStage.show();

    }
}
