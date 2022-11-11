package damm.it.proyectoud2samuelmanuel.daos;

import damm.it.proyectoud2samuelmanuel.services.SqlService;
import damm.it.proyectoud2samuelmanuel.models.Neo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * Clase que implementa el acceso a datos de Neo.
 */
public class NeoDAO implements DAO<Neo, Integer> {
    private final static Logger logger = LogManager.getLogger();

    public NeoDAO() {
    }

    /**
     * Obtiene un neo a partir de su id.
     *
     * @param id
     * @return Neo
     * @throws NoSuchElementException
     */
    @Override
    public Neo get(Integer id) throws NoSuchElementException {
        String query = """
                SELECT
                    `id`, `name`, `diameter`, `min_distance`, `speed`, `hazarous`, `date`
                FROM `neos` WHERE `id` = ?
                """;

        try (PreparedStatement ps = Objects.requireNonNull(SqlService.getConnectionNasa()).prepareStatement(query)) {
            ps.setInt(1, id);

            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return new Neo(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getDouble("diameter"),
                        resultSet.getDouble("min_distance"),
                        resultSet.getDouble("speed"),
                        resultSet.getBoolean("hazarous"),
                        resultSet.getDate("date").toLocalDate()
                );
            }
        } catch (SQLException | NullPointerException e) {
            logger.error("No se ha ha podido leer el NEO de la base de datos: {}", e.getMessage());
        }

        throw new NoSuchElementException("No se ha encontrado el NEO");
    }

    /**
     * Obtiene una lista de neos a partir de un intervalo de fechas.
     *
     * @param date
     * @param predicate
     * @return Lista de neos
     * @throws NoSuchElementException
     */
    public List<Neo> getByDate(LocalDate date, Predicate<Neo> predicate) throws NoSuchElementException {
        String query = """
                SELECT
                    `id`, `name`, `diameter`, `min_distance`, `speed`, `hazarous`, `date`
                FROM `neos` WHERE `date` = ?
                """;

        List<Neo> neos = new ArrayList<>();
        try (PreparedStatement ps = Objects.requireNonNull(SqlService.getConnectionNasa()).prepareStatement(query)) {
            ps.setDate(1, Date.valueOf(date));

            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Neo neo = new Neo(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getDouble("diameter"),
                        resultSet.getDouble("min_distance"),
                        resultSet.getDouble("speed"),
                        resultSet.getBoolean("hazarous"),
                        resultSet.getDate("date").toLocalDate()
                );

                if (predicate != null && !predicate.test(neo))
                    neos.add(neo);
            }

            return neos;
        } catch (SQLException | NullPointerException e) {
            logger.error("No se ha ha podido leer el NEO de la base de datos: {}", e.getMessage());
        }

        throw new NoSuchElementException("No se ha encontrado el NEO");
    }

    /**
     * Aáde un neo a la base de datos.
     *
     * @param neo
     */
    @Override
    public void add(Neo neo) {
        String query = """
                INSERT INTO neos (`name`, `diameter`, `min_distance`, `speed`, `hazarous`, `date`) VALUES
                (?,?,?,?,?,?)
                """;
        try (PreparedStatement ps = Objects.requireNonNull(SqlService.getConnectionNasa()).prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, neo.getName());
            ps.setDouble(2, neo.getDiameter());
            ps.setDouble(3, neo.getMinDistance());
            ps.setDouble(4, neo.getSpeed());
            ps.setBoolean(5, neo.isHazardous());
            ps.setDate(6, Date.valueOf(neo.getDate()));

            ps.executeUpdate();
            ResultSet generatedKeys = ps.getGeneratedKeys();

            if (generatedKeys.next())
                neo.setId((int) generatedKeys.getLong(1));

        } catch (SQLException | NullPointerException e) {
            logger.error("No se ha podido insertar el NEO en la base de datos: {}", e.getMessage());
        }
    }

    /**
     * Actualiza un neo de la base de datos.
     *
     * @param neo
     * @throws NoSuchElementException
     */
    @Override
    public void update(Neo neo) throws NoSuchElementException {
        String query = """
                UPDATE `neos` SET
                    `name` = ?,
                    `diameter` = ?,
                    `min_distance` = ?,
                    `speed` = ?,
                    `hazarous` = ?,
                    `date` = ?
                WHERE `id` = ?
                """;

        try (PreparedStatement ps = Objects.requireNonNull(SqlService.getConnectionNasa()).prepareStatement(query)) {
            ps.setString(1, neo.getName());
            ps.setDouble(2, neo.getDiameter());
            ps.setDouble(3, neo.getMinDistance());
            ps.setDouble(4, neo.getSpeed());
            ps.setBoolean(5, neo.isHazardous());
            ps.setDate(6, Date.valueOf(neo.getDate()));
            ps.setInt(7, neo.getId());

            ps.executeUpdate();
        } catch (SQLException | NullPointerException e) {
            logger.error("No se ha podido actualizar el NEO en la base de datos: {}", e.getMessage());
            throw new NoSuchElementException("No se ha encontrado el NEO");
        }
    }


    /**
     * Borra un neo de la base de datos.
     *
     * @param neo
     * @throws NoSuchElementException
     */
    @Override
    public void remove(Neo neo) throws NoSuchElementException {
        String query = """
                DELETE FROM `neos`
                WHERE `id` = ?
                """;
        try (PreparedStatement ps = Objects.requireNonNull(SqlService.getConnectionNasa()).prepareStatement(query)) {
            ps.setInt(1, neo.getId());

            ps.executeUpdate();
        } catch (SQLException | NullPointerException e) {
            logger.error("No se ha podido eliminar el NEO en la base de datos: {}", e.getMessage());
            throw new NoSuchElementException("No se ha encontrado el NEO");
        }
    }


    /**
     * Comprueba si existe un neo con id dada en la base de datos.
     *
     * @param id
     * @return si existe
     */
    @Override
    public boolean exists(Integer id) {
        String query = """
                    SELECT
                        count(*)
                    FROM `neos` WHERE `id` = ?
                """;

        try (PreparedStatement ps = Objects.requireNonNull(SqlService.getConnectionNasa()).prepareStatement(query)) {
            ps.setInt(1, id);

            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next())
                return resultSet.getInt("id") != 0;

        } catch (SQLException | NullPointerException e) {
            logger.error("No se ha podido comprobar la existencia del NEO en la base de datos: {}", e.getMessage());
        }
        return false;
    }
}
