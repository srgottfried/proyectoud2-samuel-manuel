package damm.it.proyectoud2samuelmanuel.controllers;

import damm.it.proyectoud2samuelmanuel.View;
import damm.it.proyectoud2samuelmanuel.services.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Controlador de la vista de Login.
 */
public class LoginController extends Controller {
    private final static Logger logger = LogManager.getLogger();

    @FXML
    private TextField txtName;
    @FXML
    private PasswordField txtPwd;

    @Override
    protected void postInit() {
        stage.setResizable(false);
    }

    /**
     * Método asociado al botón de login. Validará los datos introducidos y cambiará de ventana
     * si son correctos.
     */
    public void login() {
        if (!UserService.login(txtName.getText().toLowerCase(), txtPwd.getText())) {
            txtName.getStyleClass().add("failed-txt");
            txtPwd.getStyleClass().add("failed-txt");
            return;
        }

        launchView(View.VIEW_QOTD); // Nueva ventana para la imagen del dia
        changeView(View.VIEW_MAIN); // La vista principal se muestra en la misma ventana de login
    }

    /**
     * Método llamado cuando se pulsa una tecla.
     *
     * @param event Información de la tecla pulsada.
     */
    public void onKeyPressed(KeyEvent event) {
        switch (event.getCode()) {
            case ENTER -> login();
            case ESCAPE -> stage.close();
        }
    }
}
