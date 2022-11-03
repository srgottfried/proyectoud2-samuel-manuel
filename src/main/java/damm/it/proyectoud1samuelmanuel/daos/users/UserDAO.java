package damm.it.proyectoud1samuelmanuel.daos.users;

import damm.it.proyectoud1samuelmanuel.daos.DAO;
import damm.it.proyectoud1samuelmanuel.models.User;

public interface UserDAO extends DAO<User> {
    User read(String loginData);
}
