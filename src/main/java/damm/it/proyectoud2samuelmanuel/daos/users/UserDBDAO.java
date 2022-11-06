package damm.it.proyectoud2samuelmanuel.daos.users;

import damm.it.proyectoud2samuelmanuel.daos.DBDAO;
import damm.it.proyectoud2samuelmanuel.models.Neo;
import damm.it.proyectoud2samuelmanuel.models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDBDAO implements DBDAO<User> {
    private final Connection connection;

    public UserDBDAO(Connection connection) {
        this.connection = connection;
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
            System.out.println("Error al realizar la inserción de datos");
            e.printStackTrace();
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
            System.out.println("Error al realizar la lectura sobre la base de datos.");
            e.printStackTrace();
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
            System.out.println("Error al actualizar la base de datos");
            e.printStackTrace();
        }
    }

    public void update(int id, User user) {
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
            preparedStatement.setInt(4, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al actualizar la base de datos");
            e.printStackTrace();
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
            System.out.println("Error al eliminar registro de la base de datos");
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String query = """
                delete from users
                where id = ?
                """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al eliminar registro de la base de datos");
            e.printStackTrace();
        }
    }
}
