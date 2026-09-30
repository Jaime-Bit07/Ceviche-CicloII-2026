module pe.edu.upeu.ventaautos {
    requires javafx.controls;
    requires javafx.fxml;
    opens pe.edu.upeu.ventaautos.controller to javafx.fxml;
    opens pe.edu.upeu.ventaautos.model to javafx.base;
    exports pe.edu.upeu.ventaautos;
}