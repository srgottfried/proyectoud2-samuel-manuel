package damm.it.proyectoud2samuelmanuel.models;

/**
 * Esta clase modela a los usuarios del programa y almacena una referencia al usuario activo
 */
public class User {
    private int id;
    private String name;
    private String dbKey;

    public User(String name, String dbKey) {
        this.name = name;
        this.dbKey = dbKey;
    }

    /**
     * Constructor del usuario.
     *
     * @param name Nombre del usuario
     * @param dbKey Clave de api del usuario
     */
    public User(int id, String name, String dbKey) {
        this.id = id;
        this.name = name;
        this.dbKey = dbKey;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * Método para obtener la clave de api del usuario.
     *
     * @return La clave de api del usuario
     */
    public String getDbKey() {
        return dbKey;
    }

    public void setDbKey(String dbKey) {
        this.dbKey = dbKey;
    }

    /**
     * Método para obtener el nombre del usuario.
     *
     * @return El nombre del usuario
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
