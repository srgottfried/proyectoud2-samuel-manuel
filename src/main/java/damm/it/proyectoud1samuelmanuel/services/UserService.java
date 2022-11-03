package damm.it.proyectoud1samuelmanuel.services;

import damm.it.proyectoud1samuelmanuel.models.User;
import damm.it.proyectoud1samuelmanuel.repositories.users.UserRepository;
import damm.it.proyectoud1samuelmanuel.repositories.users.UserRepositoryImpl;

import java.util.NoSuchElementException;

/**
 * Clase para gestionar a los usuarios. Almacena al usuario activo y realiza el login y logout.
 */
public class UserService {
    private static User activeUser = null;
    private static final UserRepository userRepository = new UserRepositoryImpl();

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
            user = userRepository.get(String.format("%s:%s", name, pwd));
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
