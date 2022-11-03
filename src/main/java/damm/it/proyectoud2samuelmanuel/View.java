package damm.it.proyectoud2samuelmanuel;

/**
 * Enumerador con las distintas vistas de la aplicación. Aquí se define su título y ruta del .fxml
 */
public enum View {
    VIEW_LOGIN("Login", "views/login-view.fxml"),
    VIEW_QOTD("Mensaje del dia", "views/apod-view.fxml"),
    VIEW_MAIN("Búsqueda de asteroides", "views/main-view.fxml");

    private final String title;
    private final String path;

    /**
     * Constructor de la vista.
     *
     * @param title Título de la vista
     * @param path Ruta del .fxml
     */
    View(String title, String path) {
        this.title = title;
        this.path = path;
    }

    /**
     * Método para obtener el título.
     *
     * @return Título de la vista
     */
    public String getTitle() {
        return title;
    }

    /**
     * Método para obtener la ruta del .fxml.
     *
     * @return Ruta del .fxml
     */
    public String getPath() {
        return path;
    }
}