package damm.it.proyectoud2samuelmanuel.services;

import damm.it.proyectoud2samuelmanuel.daos.UserDAO;
import damm.it.proyectoud2samuelmanuel.models.User;

import java.util.NoSuchElementException;

/**
 * Clase para gestionar a los usuarios. Almacena al usuario activo y realiza el login y logout.
 */
public abstract class UserService {
    private static User activeUser = null;
    private static final UserDAO userDAO = new UserDAO();

    /**
     * Método para iniciar la sesión de un usuario.
     *
     * @param name Nombre del usuario
     * @param pwd Contraseña del usuario
     * @return True si se ha realizado el login correctamente, false si algún dato era incorrecto
     */
    public static boolean login(String name, String pwd) {
        User user;

        try {
            user = userDAO.get(name);
            String decryptedPwd = CypherService.decryptUser(user.getDbKey(), String.format("%s:%s", name, pwd));
            if (decryptedPwd == null)
                return false;

            user.setDbKey(decryptedPwd);
        } catch (NoSuchElementException e) {
            return false;
        }

        activeUser = user;
        return true;
    }

    /**
     * Método para cerrar la sesión activa.
     */
    public static void logout() {
        SqlService.closeNasa();
        activeUser = null;
    }

    /**
     * Método para obtener al usuario activo.
     *
     * @return Retorna el usuario activo o null, en caso no haber hecho login
     */
    public static User getActiveUser() {
        return activeUser;
    }
}
