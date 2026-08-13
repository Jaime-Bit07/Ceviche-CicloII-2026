module pe.edu.upeu.repasog3 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens pe.edu.upeu.repasog3 to javafx.fxml;
    exports pe.edu.upeu.repasog3;
}