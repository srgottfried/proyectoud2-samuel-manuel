package damm.it.proyectoud1samuelmanuel;

import damm.it.proyectoud1samuelmanuel.controllers.Controller;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Clase principal de la aplicación. Únicamente gestiona la inicialización.
 */
public class Main extends Application {
    /**
     * Método de entrada del programa.
     *
     * @param args Argumentos de consola
     */
    public static void main(String[] args) {
        launch();
    }

    /**
     * Método llamado al lanzarse la aplicación.
     *
     * @param stage Stage principal de la aplicación
     */
    @Override
    public void start(Stage stage) {
        Controller.launchView(View.VIEW_LOGIN, stage);
    }
}