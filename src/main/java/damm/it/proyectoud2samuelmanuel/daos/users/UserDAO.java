package damm.it.proyectoud2samuelmanuel.daos.users;

import damm.it.proyectoud2samuelmanuel.daos.DAO;
import damm.it.proyectoud2samuelmanuel.models.User;

public interface UserDAO extends DAO<User> {
    User read(String loginData);
}
