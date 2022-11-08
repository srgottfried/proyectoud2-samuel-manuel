package damm.it.proyectoud2samuelmanuel.daos.users;

import damm.it.proyectoud2samuelmanuel.daos.CrudDAO;
import damm.it.proyectoud2samuelmanuel.db.ConnectionManager;
import damm.it.proyectoud2samuelmanuel.models.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDBDAO implements CrudDAO<User> {
    private final static Logger logger = LogManager.getLogger();
    private final Connection connection;

    public UserDBDAO(Connection connection) {
        this.connection = connection;
    }

    public UserDBDAO() {
        this.connection = ConnectionManager.getConnection("login_nasa");
    }

    @Override
    public void create(User user) {
        String query = """
                insert into users 
                (username, password, api_key) 
                values (?,?,?)
                """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getApiKey());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error al realizar la inserción de datos: {}", e.getMessage());
        }
    }

    @Override
    public List<User> read() {
        String query = "select * from users";
        List<User> users;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            users = new ArrayList<>();
            if (resultSet != null) {
                while (resultSet.next()) {
                    users.add(new User(
                            resultSet.getInt("id"),
                            resultSet.getString("username"),
                            resultSet.getString("password"),
                            resultSet.getString("api_key")
                    ));
                }
            }
            return users;
        } catch (SQLException e) {
            logger.error("Error al realizar la lectura sobre la base de datos: {}", e.getMessage());
        }
        return null;
    }


    public User read(String dateString) {
        String query = """
                select * 
                from users
                where username = ?
                """;
        ResultSet resultSet = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, dateString.split(":")[0]);
            resultSet = preparedStatement.executeQuery();
            if (resultSet != null) {
                while (resultSet.next()) {
                    return (new User(
                            resultSet.getInt("id"),
                            resultSet.getString("username"),
                            resultSet.getString("password"),
                            resultSet.getString("api_key")
                    ));
                }
            }
        } catch (SQLException e) {
            logger.error("Error al realizar la lectura sobre la base de datos: {}", e.getMessage());
        } finally {
            try {
                resultSet.close();
            } catch (SQLException e) {
                logger.error("Error al cerar el statement: {}", e.getMessage());
            }
        }
        return null;
    }

    @Override
    public void update(User user) {
        String query = """
                update users
                set             username = ?, 
                                password = ?, 
                                api_key = ?
                where id = ?
                """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getApiKey());
            preparedStatement.setInt(4, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error al actualizar la base de datos: {}", e.getMessage());
        }
    }

    @Override
    public void delete(User user) {
        String query = """
                delete from users
                where id = ?
                """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error al eliminar registro de la base de datos: {}", e.getMessage());
        }
    }

    @Override
    public boolean exists(User user) {
        String query = "select count(*) from users where id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, user.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt("id") != 0;
        } catch (SQLException e) {
            logger.error("Error al comprobar la existencia de registro: {}", e.getMessage());
        }
        return false;
    }


}
