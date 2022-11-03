package damm.it.proyectoud2samuelmanuel.controllers;

import damm.it.proyectoud2samuelmanuel.Main;
import damm.it.proyectoud2samuelmanuel.View;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URL;

/**
 * Clase abstracta que representa a los controladores. Se usa para abstraer la lógica de tareas
 * comunes, como lanzar una nueva ventana o cambiar de escena.
 */
abstract public class Controller {
    private static final Logger logger = LogManager.getLogger();

    protected Stage stage;

    /**
     * Método para lanzar una nueva ventana. Se lanzará en un nuevo hilo JavaFX.
     *
     * @param view La vista que tendrá la nueva ventana
     */
    public static void launchView(View view) {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                return null;
            }
        };

        task.setOnSucceeded(event -> {
            Stage stage = new Stage();
            launchView(view, stage);
        });

        Thread t = new Thread(task);
        t.setDaemon(true);
        t.start();
    }

    /**
     * Método para lanzar una nueva ventana a partir de una Stage ya existente.
     * Se necesita para lanzar la primera ventana desde la main.
     *
     * @param view La vista que tendrá la nueva ventana
     * @param stage La Stage que contendrá la nueva ventana
     */
    public static void launchView (View view, Stage stage) {
        Scene scene;
        FXMLLoader fxmlLoader;

        try {
            fxmlLoader = new FXMLLoader(Main.class.getResource(view.getPath()));
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            logger.error("Error al cargar la vista {}: {}", view.getPath(), e.getMessage());
            return;
        }

        Controller controller = fxmlLoader.getController();
        controller.setStage(stage);

        URL icon;
        if ((icon = Main.class.getResource("img/icon-nasa.png")) != null)
            stage.getIcons().add(new Image(icon.toString()));

        stage.setTitle(view.getTitle());
        stage.setScene(scene);
        controller.postInit();

        stage.show();
    }

    /**
     * Método para establecer la stage que contiene la vista.
     *
     * @param stage La stage que contiene la vista
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Método para cambiar de vista.
     *
     * @param view Nueva vista
     */
    protected void changeView(View view) {
        Scene scene;
        FXMLLoader fxmlLoader;

        try {
            fxmlLoader = new FXMLLoader(Main.class.getResource(view.getPath()));
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            logger.error("Error al cargar la vista: {}", view.getPath());
            return;
        }

        Controller controller = fxmlLoader.getController();
        controller.setStage(stage);

        stage.setTitle(view.getTitle());

        stage.setScene(scene);
        stage.centerOnScreen();

        controller.postInit();
    }

    /**
     * Método para aplicar modificadores sobre la stage, antes de lanzarla.
     */
    protected void postInit() { }
}
