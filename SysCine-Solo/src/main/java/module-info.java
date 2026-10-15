module pe.edu.upeu.sysventas {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;

    opens pe.edu.upeu.sysventas.cine.controller to javafx.fxml;
    opens pe.edu.upeu.sysventas.cine.model to javafx.base;
    exports pe.edu.upeu.sysventas.cinerun;
}
