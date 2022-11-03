package damm.it.proyectoud1samuelmanuel.models;

/**
 * Esta clase modela a los usuarios del programa y almacena una referencia al usuario activo
 */
public class User {
    private final String name;
    private final String apiKey;

    /**
     * Constructor del usuario.
     *
     * @param name Nombre del usuario
     * @param apiKey Clave de api del usuario
     */
    public User(String name, String apiKey) {
        this.name = name;
        this.apiKey = apiKey;
    }

    /**
     * Método para obtener la clave de api del usuario.
     *
     * @return La clave de api del usuario
     */
    public String getApiKey() {
        return apiKey;
    }

    /**
     * Método para obtener el nombre del usuario.
     *
     * @return El nombre del usuario
     */
    public String getName() {
        return name;
    }
}
