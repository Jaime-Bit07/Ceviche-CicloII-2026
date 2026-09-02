package pe.edu.upeu.algoritmosgui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MatrizTriangular extends Application {
    GridPane grid;
    Label lblInfo;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Label titulo=new Label("MATRIZ DE LA FORMA 5");

        Label lblTam=new Label("Tamaño (n):");
        Spinner<Integer> spInico=new Spinner<>(2, 30,5);

        Label lblNumI=new Label("Numero de Inicio:");
        Spinner<Integer> spNumI=new Spinner<>(0, 30,0);

        Button gMtriz=new Button("Generar");

        HBox controles=new HBox(10, lblTam, spInico, lblNumI, spNumI, gMtriz);
        grid=new GridPane();
        grid.setHgap(3);
        grid.setVgap(3);
        gMtriz.setOnAction(event -> {
            matrizF5(spInico.getValue(),spNumI.getValue() );
        });
        matrizF5(spInico.getValue(),spNumI.getValue() );//Agregado
        lblInfo=new Label("Mostrar Información"); //Agregado
        VBox root=new VBox(15, titulo, controles, grid, lblInfo); //modificado
        root.setPadding(new Insets(15));
        ScrollPane scroll=new ScrollPane(root);

        primaryStage.setScene(new Scene(scroll));
        primaryStage.setTitle("Ejemplos de Matriz GUI");
        primaryStage.show();
    }

    public void matrizF5(int tam, int numI){
        grid.getChildren().clear();
        for(int f=0;f<tam; f++){
            for(int c=tam-1;c>=tam-1-f; c--){
                Button cuadrito=new Button(String.valueOf(numI));
                cuadrito.setMinSize(48,42);
                cuadrito.setPrefSize(48,42);
                grid.add(cuadrito, c, f);
                int ff=f;
                int cc=c;
                cuadrito.setOnAction(event -> {
                    lblInfo.setText("Su valor es: "+cuadrito.getText()
                            +" ubicado en la fila: "+(ff)+" y columna: "+(cc));
                });
                numI++;
            }
        }
    }

}
