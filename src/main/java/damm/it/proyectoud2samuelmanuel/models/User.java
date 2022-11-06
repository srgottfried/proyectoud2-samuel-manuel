package damm.it.proyectoud2samuelmanuel.models;

/**
 * Esta clase modela a los usuarios del programa y almacena una referencia al usuario activo
 */
public class User {
    private int id;
    private final String name;
    private String password;
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


    public User(String name, String password, String apiKey) {
        this.name = name;
        this.password = password;
        this.apiKey = apiKey;
    }

    public User(int id, String name, String password, String apiKey) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.apiKey = apiKey;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getPassword() {
        return password;
    }
}
