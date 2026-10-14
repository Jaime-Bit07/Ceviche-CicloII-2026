module pe.edu.upeu.sysventas {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires static lombok;
    requires jakarta.validation;

    requires org.postgresql.jdbc;
    requires java.sql;
    requires java.naming;
    requires org.slf4j;
    requires com.zaxxer.hikari;


    opens pe.edu.upeu.sysventas to javafx.fxml;
    opens pe.edu.upeu.sysventas.controller to javafx.fxml;
    exports pe.edu.upeu.sysventas;
    opens pe.edu.upeu.sysventas.model;
}