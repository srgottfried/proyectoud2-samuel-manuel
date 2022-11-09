package damm.it.proyectoud2samuelmanuel.daos;

import damm.it.proyectoud2samuelmanuel.services.SqlService;
import damm.it.proyectoud2samuelmanuel.models.Apod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;
import java.util.Objects;

public class ApodDAO implements DAO<Apod, LocalDate> {
    private final static Logger logger = LogManager.getLogger();

    public ApodDAO() {
    }

    @Override
    public Apod get(LocalDate localDate) throws NoSuchElementException {
        String query = """
                SELECT
                    `id`, `date`, `img`, `title`, `explanation`, `copyright`
                FROM `apods` WHERE `date` = ?
                """;

        try (PreparedStatement ps = Objects.requireNonNull(SqlService.getConnectionNasa()).prepareStatement(query)) {
            ps.setDate(1, Date.valueOf(localDate));

            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return new Apod(
                        resultSet.getInt("id"),
                        resultSet.getDate("date").toLocalDate(),
                        resultSet.getBinaryStream("img"),
                        resultSet.getString("title"),
                        resultSet.getString("explanation"),
                        resultSet.getString("copyright")
                );
            }
        } catch (SQLException | NullPointerException e) {
            logger.error("No se ha podido leer el APOD de la base de datos: {}", e.getMessage());
        }

        throw new NoSuchElementException("No se ha encontrado el APOD");
    }

    public LocalDate getLastDate() throws NoSuchElementException {
        String query = """
                SELECT
                    `date`
                FROM `apods` ORDER BY `date` DESC
                """;

        try (PreparedStatement ps = Objects.requireNonNull(SqlService.getConnectionNasa()).prepareStatement(query)) {
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next())
                return resultSet.getDate("date").toLocalDate();

        } catch (SQLException | NullPointerException e) {
            logger.error("No se ha podido leer el APOD de la base de datos: {}", e.getMessage());
        }

        throw new NoSuchElementException("No se ha encontrado el APOD");
    }

    @Override
    public void add(Apod apod) {
        String query = """
                INSERT INTO apods (`date`, `img`, `title`, `explanation`, `copyright`) VALUES
                (?,?,?,?,?)
                """;

        try (PreparedStatement ps = Objects.requireNonNull(SqlService.getConnectionNasa()).prepareStatement(query)) {
            ps.setDate(1, Date.valueOf(apod.getDate()));
            ps.setBinaryStream(2, apod.getImg());
            ps.setString(3, apod.getTitle());
            ps.setString(4, apod.getExplanation());
            ps.setString(5, apod.getCopyright());

            ps.executeUpdate();
            ResultSet generatedKeys = ps.getGeneratedKeys();

            if (generatedKeys.next())
                apod.setId((int)generatedKeys.getLong(1));

        } catch (SQLException | NullPointerException e) {
            logger.error("No se ha podido guardar el APOD en la base de datos: {}", e.getMessage());
        }
    }

    @Override
    public void update(Apod apod) throws NoSuchElementException {
        String query = """
                UPDATE `apods` SET
                    `date` = ?,
                    `img` = ?,
                    `title` = ?,
                    `explanation` = ?,
                    `copyright` = ?
                WHERE `id` = ?
                """;

        try (PreparedStatement ps = Objects.requireNonNull(SqlService.getConnectionNasa()).prepareStatement(query)) {
            ps.setDate(1, Date.valueOf(apod.getDate()));
            ps.setBinaryStream(2, apod.getImg());
            ps.setString(3, apod.getTitle());
            ps.setString(4, apod.getExplanation());
            ps.setString(5, apod.getCopyright());
            ps.setInt(6, apod.getId());

            ps.executeUpdate();
        } catch (SQLException | NullPointerException e) {
            logger.error("No se ha podido actualizar el APOD de la base de datos: {}", e.getMessage());
            throw new NoSuchElementException("No se ha encontrado el APOD");
        }
    }

    @Override
    public void remove(Apod apod) throws NoSuchElementException {
        String query = """
                DELETE FROM `apods`
                WHERE `id` = ?
                """;

        try (PreparedStatement ps = Objects.requireNonNull(SqlService.getConnectionNasa()).prepareStatement(query)) {
            ps.setInt(1, apod.getId());

            ps.executeUpdate();
        } catch (SQLException | NullPointerException e) {
            logger.error("No se ha podido eliminar el APOD de la base de datos: {}", e.getMessage());
            throw new NoSuchElementException("No se ha encontrado el APOD");
        }
    }

    @Override
    public boolean exists(LocalDate localDate) {
        String query = """
            SELECT count(*)
                FROM `apods`
            WHERE `date` = ?
            """;

        try (PreparedStatement ps = Objects.requireNonNull(SqlService.getConnectionNasa()).prepareStatement(query)) {
            ps.setString(1, localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next())
                return resultSet.getInt("id") != 0;

        } catch (SQLException | NullPointerException e) {
            logger.error("No se ha podido comprobar la existencia del APOD de la base de datos: {}", e.getMessage());
        }

        return false;
    }
}
