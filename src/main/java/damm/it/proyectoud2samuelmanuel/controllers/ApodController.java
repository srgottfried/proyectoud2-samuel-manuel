package damm.it.proyectoud2samuelmanuel.controllers;

import damm.it.proyectoud2samuelmanuel.daos.ApodDAO;
import damm.it.proyectoud2samuelmanuel.models.Apod;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;

/**
 * Controlador de la vista de mensaje del dia.
 */
public class ApodController extends Controller implements Initializable {
    private static final Logger logger = LogManager.getLogger();

    @FXML
    private Label lblQotd;
    @FXML
    private Label lblTitle;
    @FXML
    private Label lblCopyright;
    @FXML
    private ImageView imgApod;
    @FXML
    private DatePicker date;
    @FXML
    private AnchorPane paneApod;
    private LocalDate lastAvailableDate;
    private LocalDate lastDate;
    private ApodDAO apodDAO;

    /**
     * Método llamado al cargarse la vista. Todavía no tiene ni Stage ni Scene asignadas.
     *
     * @param location Localización del archivo .fxml
     * @param resources Paquete de recursos para la localización de las vistas
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        apodDAO = new ApodDAO();

        try {
            lastAvailableDate = apodDAO.getLastDate();
            date.setValue(lastAvailableDate);
        } catch (NoSuchElementException e) {
            logger.error("No hay ninguna imagen del dia en la base de datos.");
        }

        updateQotd();
    }

    /**
     * Método llamado al terminar de inicializarse la vista. Ya tiene Stage y Scene asignadas.
     */
    @Override
    protected void postInit() {
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);

        stage.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            switch (event.getCode()) {
                case LEFT, A -> prevDay();
                case RIGHT, D -> nextDay();
                case ESCAPE, SPACE -> closePopup();
            }

            event.consume();
        });

        date.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            lastDate = oldValue;
            updateQotd();
        });
    }

    /**
     * Método para cerrar la ventana.
     */
    public void closePopup() {
        stage.close();
    }

    /**
     * Método para cambiar al dia anterior.
     */
    public void prevDay() {
        if (date != null)
            date.setValue(date.getValue().minusDays(1));
    }

    /**
     * Método para cambiar al dia siguiente.
     */
    public void nextDay() {
        if (date == null || date.getValue().compareTo(lastAvailableDate) >= 0)
            return;

        date.setValue(date.getValue().plusDays(1));
    }

    /**
     * Método para actualizar la frase e imagen del dia.
     */
    public void updateQotd() {
        Task<Apod> task = new Task<>() {
            @Override
            protected Apod call() {
                return apodDAO.get(date.getValue());
            }
        };

        task.setOnSucceeded(event -> {
            Apod apod = task.getValue();
            if (date.getValue() == null) { // La primera consulta se hace sin fecha, para descubrir el último dia del que hay foto
                lastAvailableDate = apod.getDate();
                date.setValue(lastAvailableDate);

                date.setDayCellFactory(param -> new DateCell() {
                    @Override
                    public void updateItem(LocalDate date, boolean empty) {
                        super.updateItem(date, empty);
                        setDisable(empty || date.compareTo(lastAvailableDate) > 0 );
                    }
                });
            }

            Image img = new Image(apod.getImg());

            imgApod.setImage(img);
            imgApod.setPreserveRatio(true);
            imgApod.setFitHeight(600);
            imgApod.setFitWidth(0);

            paneApod.setPrefWidth(imgApod.getFitWidth());

            lblQotd.setText(apod.getExplanation());
            lblTitle.setText(apod.getTitle());
            if (apod.getCopyright() != null)
                lblCopyright.setText("© " + apod.getCopyright());

            stage.setWidth(((img.getWidth() / img.getHeight()) * 600) + 640);
            stage.setHeight(600);
            stage.centerOnScreen();
        });

        task.setOnFailed(event -> {
            if (date.getValue() != null &&  !date.getValue().equals(lastDate))
                date.setValue(lastDate);
            logger.error("Ha fallado la obtención del APOD: {}", task.getException().getMessage());
        });

        Thread t = new Thread(task);
        t.setDaemon(true);
        t.start();
    }
}
