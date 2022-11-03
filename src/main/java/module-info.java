module damm.it.proyectoud1samuelmanuel {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.swing;
    requires java.desktop;

    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.dataformat.csv;

    requires org.apache.logging.log4j;

    exports damm.it.proyectoud1samuelmanuel;
    opens damm.it.proyectoud1samuelmanuel to javafx.fxml;

    exports damm.it.proyectoud1samuelmanuel.controllers;
    opens damm.it.proyectoud1samuelmanuel.controllers to javafx.fxml;

    exports damm.it.proyectoud1samuelmanuel.models;
    opens damm.it.proyectoud1samuelmanuel.models to javafx.fxml;

    exports damm.it.proyectoud1samuelmanuel.entities.apod;
    opens damm.it.proyectoud1samuelmanuel.entities.apod to com.fasterxml.jackson.databind;

    exports damm.it.proyectoud1samuelmanuel.entities.noes;
    opens damm.it.proyectoud1samuelmanuel.entities.noes to com.fasterxml.jackson.databind;
    exports damm.it.proyectoud1samuelmanuel.daos;
    opens damm.it.proyectoud1samuelmanuel.daos to javafx.fxml;
}