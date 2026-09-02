package pe.edu.upeu.algoritmogui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MatrizGUI extends Application {
    Label informacion;
    GridPane grid;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Label titulo=new Label("MATRIZ - FORMAS");

        Label lbnTm=new Label("Tamaño de la Matriz:");
        Spinner<Integer> spTm=new Spinner<>(2, 30, 5);

        Label lbnNumI=new Label("Numero de Inicio:");
        Spinner<Integer> spNumI=new Spinner<>(0, 30, 0);
        Button btnGenerar=new Button("Generar Matriz");
        informacion=new Label("Mostrar Posiciones....!");
        grid=new GridPane();
        grid.setVgap(3);
        grid.setHgap(3);

        btnGenerar.setOnAction(event -> {
            matrizF5(spTm.getValue(), spNumI.getValue());
            matrizF11(spTm.getValue(), spNumI.getValue());
        });

        ChoiceBox<String> formas = new ChoiceBox<>();
        formas.getItems().addAll("Forma 5", "Forma 11", "Forma 13", "Forma X");
        formas.setValue("Forma 5");
        Label lbnFormas=new Label("Elegir Froma:");
        HBox hbx=new HBox(15, lbnFormas, formas);
        HBox hb=new HBox(10, lbnTm, spTm,lbnNumI, spNumI, btnGenerar );
        matrizF5(spTm.getValue(), spNumI.getValue());
        VBox vb=new VBox(15, titulo, hb, hbx, grid, informacion);
        ScrollPane cp=new ScrollPane(vb);
        primaryStage.setScene(new Scene(cp));
        primaryStage.setTitle("Formas Matriciales");
        primaryStage.show();


        Label lbdFormas=new Label("Elegir Froma:");
        HBox hdx=new HBox(15, lbdFormas, formas);
        HBox hd=new HBox(10, lbnTm, spTm,lbnNumI, spNumI, btnGenerar );
        matrizF11(spTm.getValue(), spNumI.getValue());
        VBox vd=new VBox(15, titulo, hd, hdx, grid, informacion);
        ScrollPane cd=new ScrollPane(vd);
        primaryStage.setScene(new Scene(cd));
        primaryStage.setTitle("Formas Matriciales");
        primaryStage.show();



    }
    public void matrizF5(int tamanho, int numI){
        grid.getChildren().clear();
        for (int f = 0; f < tamanho; f++) {
            for (int c = tamanho-1; c >=tamanho-1-f; c--) {
                int ff=f, cc=c;
                Button cuadro=new Button(String.valueOf(numI));
                cuadro.setMinSize(48, 42);
                cuadro.setPrefSize(48,42);
                cuadro.setOnAction(event -> {
                    informacion.setText("Valor: "+cuadro.getText()+"  Fila:"+(ff)+"  Columna:"+cc);
                });
                grid.add(cuadro, c, f);
                numI++;
            }
        }
    }
    public void matrizF11(int tamanho, int numI){
        grid.getChildren().clear();
        int[][] matriz=new int[tamanho][tamanho];
        for (int f = 0; f < matriz.length; f++) {
            for (int c = matriz[0].length-1-f; c>=0; c--) {


                matriz[f][c]=numI;
                numI++;
            }
        }
    }

}
