package pe.edu.upeu.algoritmogui;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class Calculadora extends Application {
    Label resultado;
    HBox hb;
    @Override
    public void start(Stage stage) throws IOException {
        Label mensaje = new Label("Bienvenido a JavaFX");
        mensaje.setStyle("-fx-font-size: 18px;");
        TextField txtNombre=new TextField();
        Button boton = new Button("Saludar");




        boton.setOnAction(evento ->
                mensaje.setText("¡Hola !"+txtNombre.getText()));
        calc();

        VBox raiz = new VBox(15, mensaje, txtNombre, boton,hb,resultado);
        raiz.setAlignment(Pos.CENTER);
        raiz.setStyle("-fx-padding: 30;");

        Scene escena = new Scene(raiz, 600, 250);
        stage.setTitle("Hola JavaFX");
        stage.setScene(escena);
        stage.show();
    }


    public void calc(){
        TextField num1 = new TextField();
        TextField num2 = new TextField();
        resultado = new Label();

        ChoiceBox<String> oper = new ChoiceBox<>();
        oper.getItems().addAll("S", "M", "L", "XL");
        oper.setValue("+");

        Button operar = new Button("Operar");
        operar.setOnAction(evento->{
            switch(oper.getValue()){
                case"+": resultado.setText("Resultado: "+
                        (Integer.parseInt(num1.getText())+Integer.parseInt(num2.getText()))); break;
                case"-": resultado.setText("Resultado: "+
                        (Integer.parseInt(num1.getText())-Integer.parseInt(num2.getText())));break;
                case"*": resultado.setText("Resultado: "+
                        (Integer.parseInt(num1.getText())*Integer.parseInt(num2.getText())));break;
                case"/": resultado.setText("Resultado: "+
                        (Integer.parseInt(num1.getText())/Integer.parseInt(num2.getText())));break;
            }

        });

        hb=new HBox(10,num1,oper,num2,operar);

    }

}
