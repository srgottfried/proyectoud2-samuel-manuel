package damm.it.proyectoud2samuelmanuel.daos.neos;

import damm.it.proyectoud2samuelmanuel.daos.CrudDAO;
import damm.it.proyectoud2samuelmanuel.db.ConnectionManager;
import damm.it.proyectoud2samuelmanuel.models.Apod;
import damm.it.proyectoud2samuelmanuel.models.Neo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NeoDBDAO implements CrudDAO<Neo> {
    private final static Logger logger = LogManager.getLogger();
    private final Connection connection;

    public NeoDBDAO(Connection connection) {
        this.connection = connection;
    }

    public NeoDBDAO() {
        this.connection = ConnectionManager.getConnection("nasa");
    }

    @Override
    public void create(Neo neo) {
        String query = """
                insert into neos 
                (name, diameter, min_distance, speed, hazarous, date) 
                values (?,?,?,?,?,?,?)
                """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, neo.getName());
            preparedStatement.setDouble(2, neo.getDiameter());
            preparedStatement.setDouble(3, neo.getDiameter());
            preparedStatement.setDouble(4, neo.getMinDistance());
            preparedStatement.setDouble(5, neo.getSpeed());
            preparedStatement.setBoolean(6, neo.isHazardous());
            preparedStatement.setDate(7, Date.valueOf(neo.getDate()));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al realizar la inserción de datos");
            e.printStackTrace();
        }
    }

    @Override
    public List<Neo> read() {
        String query = "select * from neos";
        List<Neo> neos;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            neos = new ArrayList<>();
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
            return neos;
        } catch (SQLException e) {
            System.out.println("Error al realizar la lectura sobre la base de datos.");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(Neo neo) {
        String query = """
                update neos
                set             name = ?, 
                                diameter = ?, 
                                min_distance = ?,
                                speed = ?,
                                hazarous = ?,
                                date = ?        
                where id = ?
                """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, neo.getName());
            preparedStatement.setDouble(2, neo.getDiameter());
            preparedStatement.setDouble(3, neo.getMinDistance());
            preparedStatement.setDouble(4, neo.getSpeed());
            preparedStatement.setBoolean(5, neo.isHazardous());
            preparedStatement.setDate(6, Date.valueOf(neo.getDate()));
            preparedStatement.setInt(7, neo.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al actualizar la base de datos");
            e.printStackTrace();
        }
    }

    public void update(int id, Neo neo) {
        String query = """
                update neos
                set             name = ?, 
                                diameter = ?, 
                                min_distance = ?,
                                speed = ?,
                                hazarous = ?,
                                date = ?        
                where id = ?
                """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, neo.getName());
            preparedStatement.setDouble(2, neo.getDiameter());
            preparedStatement.setDouble(3, neo.getMinDistance());
            preparedStatement.setDouble(4, neo.getSpeed());
            preparedStatement.setBoolean(5, neo.isHazardous());
            preparedStatement.setDate(6, Date.valueOf(neo.getDate()));
            preparedStatement.setInt(7, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al actualizar la base de datos");
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Neo neo) {
        String query = """
                delete from neos
                where id = ?
                """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, neo.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al eliminar registro de la base de datos");
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String query = """
                delete from neos
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

    @Override
    public boolean exists(Neo neo) {
        String query = "select count(*) from apods where id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, neo.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt("id") != 0;
        } catch (SQLException e) {
            logger.error("Error al comprobar la existencia de registro: {}", e.getMessage());
        }
        return false;
    }

}
