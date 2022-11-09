package damm.it.proyectoud2samuelmanuel;

import damm.it.proyectoud2samuelmanuel.controllers.Controller;
import damm.it.proyectoud2samuelmanuel.services.SqlService;
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
        SqlService.close();
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