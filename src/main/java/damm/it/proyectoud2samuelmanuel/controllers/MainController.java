package damm.it.proyectoud2samuelmanuel.controllers;

import damm.it.proyectoud2samuelmanuel.Main;
import damm.it.proyectoud2samuelmanuel.View;
import damm.it.proyectoud2samuelmanuel.daos.NeoDayDAO;
import damm.it.proyectoud2samuelmanuel.daos.RequestDAO;
import damm.it.proyectoud2samuelmanuel.models.Neo;
import damm.it.proyectoud2samuelmanuel.models.NeoDay;
import damm.it.proyectoud2samuelmanuel.models.Request;
import damm.it.proyectoud2samuelmanuel.services.UserService;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Controlador de la ventana principal.
 */
public class MainController extends Controller implements Initializable {
    private static final Logger logger = LogManager.getLogger();

    @FXML
    private TabPane tabPane;
    @FXML
    private DatePicker dateStart;
    @FXML
    private DatePicker dateEnd;
    @FXML
    private CheckBox checkHazardous;
    @FXML
    private Button btnLogout;
    @FXML
    private ChoiceBox<String> selectName;
    @FXML
    private ChoiceBox<String> selectDiameter;
    @FXML
    private ChoiceBox<String> selectAprox;
    @FXML
    private ChoiceBox<String> selectSpeed;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtDiameter;
    @FXML
    private TextField txtAprox;
    @FXML
    private TextField txtSpeed;

    private NeoDayDAO neoDayDAO;
    private RequestDAO requestDAO;
    private Request lastRequest;
    private Task<List<NeoDay>> searchTask;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        neoDayDAO = new NeoDayDAO();
        requestDAO = new RequestDAO();
    }

    @Override
    protected void postInit() {
        stage.setResizable(true);
        stage.setMinWidth(1200);
        stage.setMinHeight(760);
        dateStart.setValue(LocalDate.now());
        dateEnd.setValue(LocalDate.now());
        btnLogout.setText(UserService.getActiveUser().getName());

        if (requestDAO.exists(UserService.getActiveUser())) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

            URL icon;
            if ((icon = Main.class.getResource("img/icon-nasa.png")) != null) {
                Stage stage = (Stage)alert.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image(icon.toString()));
            }

            alert.setTitle("??ltima consulta");
            alert.setHeaderText(null);
            alert.setContentText("??Quieres recuperar tu ??ltima consulta?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK)
                loadRequest();
        }

        stage.setOnCloseRequest(event -> saveRequest());
    }

    /**
     * M??todo para lanzar la ventana de mensaje del dia.
     */
    public void launchQotd() {
        launchView(View.VIEW_QOTD);
    }

    /**
     * M??todo para cerrar la sesi??n de usuario.
     */
    public void logout() {
        saveRequest();
        UserService.logout();
        changeView(View.VIEW_LOGIN);
    }

    /**
     * M??todo llamado al pulsar una tecla.
     */
    public void onKeyPressed(KeyEvent event) {
        switch (event.getCode()) {
            case ENTER -> searchButton();
            case ESCAPE -> logout();
        }
    }

    /**
     * M??todo llamado al pulsar el bot??n de buscar.
     */
    public void searchButton() {
        if (searchTask != null && searchTask.isRunning())
            return;

        generateRequest();
        search();
    }

    /**
     * M??todo para exportar en json.
     */
    public void exportJson() {
        File outFile = getExportFile("JSON", ".json");
        if (outFile == null)
            return;

        NeoDay neoDay = getSelectedItems();
        if (neoDay == null)
            return;

        this.neoDayDAO.saveJson(neoDay, outFile);
    }

    /**
     * M??todo para exportar en csv.
     */
    public void exportCsv() {
        File outFile = getExportFile("CSV", ".csv");
        if (outFile == null)
            return;

        NeoDay neoDay = getSelectedItems();
        if (neoDay == null)
            return;

        this.neoDayDAO.saveCsv(neoDay, outFile);
    }

    /**
     * M??todo para realizar una consulta y filtrarla.
     */
    private void search() {
        searchTask = new Task<>() {
            @Override
            protected List<NeoDay> call() throws NoSuchElementException {
                return neoDayDAO.getByLapse(dateStart.getValue(), dateEnd.getValue(), neo -> {
                    if (checkHazardous.isSelected() && !neo.isHazardous())
                        return true;

                    if (!txtName.getText().isBlank() && applyFilter(neo.getName(), selectName.getValue(), txtName.getText()))
                        return true;

                    if (!txtDiameter.getText().isBlank() && applyFilter(neo.getDiameter(), selectDiameter.getValue(), Double.parseDouble(txtDiameter.getText())))
                        return true;

                    if (!txtAprox.getText().isBlank() && applyFilter(neo.getMinDistance(), selectAprox.getValue(), Double.parseDouble(txtAprox.getText())))
                        return true;

                    if (!txtSpeed.getText().isBlank() && applyFilter(neo.getSpeed(), selectSpeed.getValue(), Double.parseDouble(txtSpeed.getText())))
                        return true;

                    return false;
                });
            }
        };

        searchTask.setOnSucceeded(event -> {
            List<NeoDay> neoDays = searchTask.getValue();
            tabPane.getTabs().clear();

            for (NeoDay neoDay : neoDays)
                newTab(neoDay.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), neoDay.getNeos());
        });

        searchTask.setOnFailed(event -> logger.error("Ha fallado la obtenci??n de los NEOs: {}", searchTask.getException().getMessage()));

        Thread t = new Thread(searchTask);
        t.setDaemon(true);
        t.start();
    }

    /**
     * M??todo para almacenar en un objeto Request la ??ltima consulta realizada.
     */
    private void generateRequest() {
        String opName = selectName.getValue();
        String name = txtName.getText();
        String opDiameter = selectDiameter.getValue();
        String diameter = txtDiameter.getText();
        String opMaxAprox = selectAprox.getValue();
        String maxAprox = txtAprox.getText();
        String opSpeed = selectSpeed.getValue();
        String speed = txtSpeed.getText();
        boolean onlyDP = checkHazardous.isSelected();
        LocalDate startDate = dateStart.getValue();
        LocalDate endDate = dateEnd.getValue();

        lastRequest = new Request(UserService.getActiveUser().getName(), opName, name, opDiameter, diameter, opMaxAprox, maxAprox, opSpeed, speed, onlyDP, startDate, endDate);
    }

    /**
     * M??todo para cargar la ??ltima consulta del usuario activo.
     */
    private void loadRequest() {
        Request request = requestDAO.get(UserService.getActiveUser());

        selectName.setValue(request.getOpName());
        txtName.setText(request.getName());
        selectDiameter.setValue(request.getOpDiameter());
        txtDiameter.setText(request.getDiameter());
        selectAprox.setValue(request.getOpMaxAprox());
        txtAprox.setText(request.getMaxAprox());
        selectSpeed.setValue(request.getOpSpeed());
        txtSpeed.setText(request.getSpeed());
        checkHazardous.setSelected(request.isOnlyPD());
        dateStart.setValue(request.getStartDate());
        dateEnd.setValue(request.getEndDate());

        search();
    }

    /**
     * M??todo para guardar la ??ltima consulta del usuario activo.
     */
    private void saveRequest() {
        if (lastRequest != null)
            requestDAO.add(lastRequest);
    }


    /**
     * M??todo para aplicar una operaci??n l??gica con strings. Necesaria para traducir los seleccionables.
     * @param str1 Primera string a comparar
     * @param op Operaci??n a aplicar
     * @param str2 Segunda string a comparar
     * @return True si no se cumple la condici??n, false en caso contrario
     */
    private boolean applyFilter(String str1, String op, String str2) {
        return !switch (op) {
            case "==" -> str1.matches(str2);
            case "!=" -> !str1.matches(str2);
            default -> false;
        };
    }

    /**
     * M??todo para aplicar una operaci??n l??gica con doubles. Necesaria para traducir los seleccionables.
     * @param num1 Primer double a comparar
     * @param op Operaci??n a aplicar
     * @param num2 Segundo double a comparar
     * @return True si no se cumple la condici??n, false en caso contrario
     */
    private boolean applyFilter(double num1, String op, double num2) {
        return !switch (op) {
            case "<" -> num1 < num2;
            case "<=" -> num1 <= num2;
            case "==" -> num1 == num2;
            case "!=" -> num1 != num2;
            case ">=" -> num1 >= num2;
            case ">" -> num1 > num2;
            default -> false;
        };
    }

    /**
     * M??todo para crear una nueva pesta??a.
     *
     * @param date Fecha a la que se corresponde la pesta??a
     * @param neos Lista de neos que mostrar?? la tabla
     */
    private void newTab(String date, List<Neo> neos) {
        Tab tab = new Tab();
        tab.setText(date);

        TableView<Neo> table;
        FXMLLoader fxmlLoader;

        try {
            fxmlLoader = new FXMLLoader(Main.class.getResource("views/neo-tab-view.fxml"));
            table = fxmlLoader.load();
        } catch (IOException e) {
            System.err.print("Error al cargar la vista\n");
            return;
        }

        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        table.getItems().addAll(neos);
        tab.setContent(table);

        tabPane.getTabs().add(tab);
        tabPane.getTabs().sort(Comparator.comparing(Tab::getText));
    }

    /**
     * M??todo para obtener los datos de NEO seleccionados en la tabla activa.
     *
     * @return Los datos de NEO seleccionados
     */
    @SuppressWarnings("unchecked")
    private NeoDay getSelectedItems() {
        Node node = tabPane.getSelectionModel().getSelectedItem().getContent();
        if (!(node instanceof TableView<?>) || ((TableView<?>)node).getItems().isEmpty() || !(((TableView<?>)node).getItems().get(0) instanceof Neo))
            return null;

        TableView<Neo> table = (TableView<Neo>)node;
        List<Neo> selectedNeos = table.getSelectionModel().getSelectedItems().stream().toList();

        return new NeoDay(LocalDate.MIN, selectedNeos);
    }

    /**
     * M??todo mostrar el di??logo para seleccionar un archivo.
     *
     * @param fileType Tipo de fichero a seleccionar
     * @param extension Extensi??n del fichero a seleccionar
     * @return La ruta al fichero a guardar
     */
    private File getExportFile(String fileType, String extension) {
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(String.format("%s files (*%s)", fileType, extension), "*" + extension);
        fileChooser.getExtensionFilters().add(extFilter);

        return fileChooser.showSaveDialog(stage);
    }
}