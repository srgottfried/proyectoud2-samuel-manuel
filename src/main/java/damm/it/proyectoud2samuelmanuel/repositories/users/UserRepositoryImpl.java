package damm.it.proyectoud2samuelmanuel.repositories.users;

import damm.it.proyectoud2samuelmanuel.daos.users.UserDAO;
import damm.it.proyectoud2samuelmanuel.daos.users.UserPropDAO;
import damm.it.proyectoud2samuelmanuel.models.User;

import java.util.NoSuchElementException;

/**
 * Repositorio para los usuarios.
 */
public class UserRepositoryImpl implements UserRepository {
    private final UserDAO userDAO;

    /**
     * Constructor del repositorio.
     */
    public UserRepositoryImpl() {
        userDAO = new UserPropDAO();
    }

    /**
     * Método para obtener a un usuario dado su nombre.
     *
     * @param loginData Los datos del usuario que se quiere obtener en formato nombre:contraseña
     * @return El usuario solicitado
     * @throws NoSuchElementException Si no se encuentra al usuario
     */
    @Override
    public User get(String loginData) throws NoSuchElementException {
        User user;
        if ((user = userDAO.read(loginData)) != null)
            return user;

        throw new NoSuchElementException("No existe ese usuario.");
    }
}
