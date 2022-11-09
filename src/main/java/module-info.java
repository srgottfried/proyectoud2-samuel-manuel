module damm.it.proyectoud1samuelmanuel {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.swing;
    requires java.desktop;

    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.dataformat.csv;
    requires com.fasterxml.jackson.datatype.jsr310;

    requires org.apache.logging.log4j;

    requires java.sql;

    exports damm.it.proyectoud2samuelmanuel;
    opens damm.it.proyectoud2samuelmanuel to javafx.fxml;

    exports damm.it.proyectoud2samuelmanuel.controllers;
    opens damm.it.proyectoud2samuelmanuel.controllers to javafx.fxml;

    exports damm.it.proyectoud2samuelmanuel.models;
    opens damm.it.proyectoud2samuelmanuel.models to javafx.fxml;

    exports damm.it.proyectoud2samuelmanuel.daos;
    opens damm.it.proyectoud2samuelmanuel.daos to javafx.fxml;
}