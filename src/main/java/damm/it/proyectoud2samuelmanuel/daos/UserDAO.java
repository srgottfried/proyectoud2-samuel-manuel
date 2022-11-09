package damm.it.proyectoud2samuelmanuel.daos;

import damm.it.proyectoud2samuelmanuel.services.SqlService;
import damm.it.proyectoud2samuelmanuel.models.User;
import damm.it.proyectoud2samuelmanuel.services.CypherService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.NoSuchElementException;
import java.util.Objects;

public class UserDAO implements DAO<User, String> {
    private final static Logger logger = LogManager.getLogger();

    public UserDAO() {
    }

    @Override
    public User get(String username) throws NoSuchElementException {
        String query = """
                SELECT
                    `id`, `username`, `db_key`
                FROM `users` WHERE `username` = ?
                """;

        try (PreparedStatement ps = Objects.requireNonNull(SqlService.getConnectionLogin()).prepareStatement(query)) {
            ps.setString(1, CypherService.hash(username));

            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return (new User(
                        resultSet.getInt("id"),
                        username,
                        resultSet.getString("db_key")
                ));
            }
        } catch (SQLException | NullPointerException e) {
            logger.error("No se ha podido leer el usuario de la base de datos: {}", e.getMessage());
        }

        throw new NoSuchElementException("No se ha encontrado al usuario.");
    }

    @Override
    public void add(User user) {
        String query = """
                INSERT INTO `users` (`username`) VALUES
                (?)
                """;

        try (PreparedStatement ps = Objects.requireNonNull(SqlService.getConnectionLogin()).prepareStatement(query)) {
            ps.setString(1, CypherService.hash(user.getName()));

            ps.executeUpdate();
            ResultSet generatedKeys = ps.getGeneratedKeys();

            if (generatedKeys.next())
                user.setId((int)generatedKeys.getLong(1));

        } catch (SQLException | NullPointerException e) {
            logger.error("No se ha podido guardar el usuario en la base de datos: {}", e.getMessage());
        }
    }

    @Override
    public void update(User user) throws NoSuchElementException {
        String query = """
                UPDATE `users` SET
                    `username` = ?
                WHERE `id` = ?
                """;

        try (PreparedStatement ps = Objects.requireNonNull(SqlService.getConnectionLogin()).prepareStatement(query)) {
            ps.setString(1, CypherService.hash(user.getName()));
            ps.setInt(2, user.getId());

            ps.executeUpdate();
        } catch (SQLException | NullPointerException e) {
            logger.error("No se ha podido actualizar el usuario en la base de datos: {}", e.getMessage());
            throw new NoSuchElementException("No se ha encontrado al usuario.");
        }
    }

    @Override
    public void remove(User user) throws NoSuchElementException {
        String query = """
                DELETE FROM `users`
                WHERE `id` = ?
                """;

        try (PreparedStatement ps = Objects.requireNonNull(SqlService.getConnectionLogin()).prepareStatement(query)) {
            ps.setInt(1, user.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            logger.error("No se ha podido eliminar el usuario en la base de datos: {}", e.getMessage());
            throw new NoSuchElementException("No se ha encontrado al usuario.");
        }
    }

    @Override
    public boolean exists(String username) throws NoSuchElementException {
        String query = """
            SELECT
                count(*)
            FROM `users` WHERE `username` = ?
            """;

        try (PreparedStatement ps = Objects.requireNonNull(SqlService.getConnectionLogin()).prepareStatement(query)) {
            ps.setString(1, CypherService.hash(username));

            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next())
                return resultSet.getInt("id") != 0;

        } catch (SQLException e) {
            logger.error("No se ha podido comprobar la existencia del usuario en la base de datos: {}", e.getMessage());
        }

        return false;
    }
}
