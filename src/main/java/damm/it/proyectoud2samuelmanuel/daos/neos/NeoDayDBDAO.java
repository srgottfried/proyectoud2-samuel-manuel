package damm.it.proyectoud2samuelmanuel.daos.neos;

import damm.it.proyectoud2samuelmanuel.daos.CrudDAO;
import damm.it.proyectoud2samuelmanuel.db.ConnectionManager;
import damm.it.proyectoud2samuelmanuel.models.Neo;
import damm.it.proyectoud2samuelmanuel.models.NeoDay;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class NeoDayDBDAO implements CrudDAO<NeoDay> {
    private final static Logger logger = LogManager.getLogger();
    private final Connection connection;

    public NeoDayDBDAO(Connection connection) {
        this.connection = connection;
    }

    public NeoDayDBDAO() {
        this.connection = ConnectionManager.getConnection("nasa");
    }


    @Override
    public void create(NeoDay neoDay) {

    }

    @Override
    public List<NeoDay> read() {
        List<NeoDay> neoDays = new ArrayList<>();
        List<Neo> neos = new ArrayList<>();
        ResultSet resultSet = null;
        Date now = Date.valueOf(LocalDate.now());
        String query = """
                select * from neos
                where date = ?
                """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDate(1, now);
            resultSet = preparedStatement.executeQuery();
            if (resultSet != null) {
                while (resultSet.next()) {
                    neos.add(new Neo(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getDouble("diameter"),
                            resultSet.getDouble("min_distance"),
                            resultSet.getDouble("speed"),
                            resultSet.getBoolean("hazarous"),
                            resultSet.getDate("date").toLocalDate()
                    ));
                }
                neoDays.add(new NeoDay(now.toString(), neos));
            }
        } catch (SQLException e) {
            logger.error("Error al leer en la base de datos: {}", e.getMessage());
        } finally {
            try {
                resultSet.close();
            } catch (SQLException e) {
                logger.error("Error al cerrar el resultSet: {}", e.getMessage());
            }
        }
        return neoDays;
    }

    public NeoDay read(String stringDate) {
        List<Neo> neos = new ArrayList<>();
        ResultSet resultSet = null;
        String query = """
                select * from neos
                where date = ?
                """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDate(1, Date.valueOf(stringDate));
            resultSet = preparedStatement.executeQuery();
            if (resultSet != null) {
                while (resultSet.next()) {
                    neos.add(new Neo(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getDouble("diameter"),
                            resultSet.getDouble("min_distance"),
                            resultSet.getDouble("speed"),
                            resultSet.getBoolean("hazarous"),
                            resultSet.getDate("date").toLocalDate()
                    ));
                }
            }
        } catch (SQLException e) {
            logger.error("Error al leer en la base de datos: {}", e.getMessage());
        } finally {
            try {
                resultSet.close();
            } catch (SQLException e) {
                logger.error("Error al cerrar el resultSet: {}", e.getMessage());
            }
        }
        return new NeoDay(stringDate, neos);
    }

    @Override
    public void update(NeoDay neoDay) {

    }

    @Override
    public void delete(NeoDay neoDay) {

    }

    @Override
    public boolean exists(NeoDay neoDay) {
        return false;
    }
}
