module pe.edu.upeu.algoritmosgui {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens pe.edu.upeu.algoritmosgui to javafx.fxml;
    exports pe.edu.upeu.algoritmosgui;
}