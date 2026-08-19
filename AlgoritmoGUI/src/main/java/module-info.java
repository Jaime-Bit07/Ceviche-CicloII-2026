module pe.edu.upeu.algoritmogui {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens pe.edu.upeu.algoritmogui to javafx.fxml;
    exports pe.edu.upeu.algoritmogui;
}