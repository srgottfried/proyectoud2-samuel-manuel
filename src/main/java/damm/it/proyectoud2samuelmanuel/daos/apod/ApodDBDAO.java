package damm.it.proyectoud2samuelmanuel.daos.apod;

import damm.it.proyectoud2samuelmanuel.daos.CrudDAO;
import damm.it.proyectoud2samuelmanuel.db.ConnectionManager;
import damm.it.proyectoud2samuelmanuel.models.Apod;
import damm.it.proyectoud2samuelmanuel.models.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ApodDBDAO implements CrudDAO<Apod> {
    private final static Logger logger = LogManager.getLogger();
    private final Connection connection;

    public ApodDBDAO(Connection connection) {
        this.connection = connection;
    }

    public ApodDBDAO() {
        this.connection = ConnectionManager.getConnection("nasa");
    }

    @Override
    public void create(Apod apod) {
        String query = """
                insert into apods 
                (date, url, title, explanation, copyright) 
                values (?,?,?,?,?)
                """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDate(1, Date.valueOf(apod.getDate()));
            preparedStatement.setString(2, apod.getUrl());
            preparedStatement.setString(3, apod.getTitle());
            preparedStatement.setString(4, apod.getExplanation());
            preparedStatement.setString(5, apod.getCopyright());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al realizar la inserción de datos");
            e.printStackTrace();
        }
    }

    @Override
    public List<Apod> read() {
        String query = "select * from apods";
        List<Apod> apods;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            apods = new ArrayList<>();
            if (resultSet != null) {
                while (resultSet.next()) {
                    apods.add(new Apod(
                            resultSet.getInt("id"),
                            resultSet.getDate("date").toString(),
                            resultSet.getString("url"),
                            resultSet.getString("title"),
                            resultSet.getString("explanation"),
                            resultSet.getString("copyright")
                    ));
                }
            }
            return apods;
        } catch (SQLException e) {
            System.out.println("Error al realizar la lectura sobre la base de datos.");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(Apod apod) {
        String query = """
                update apods
                set             date = ?,
                                url = ?,
                                title = ?,
                                explanation = ?,
                                copyright = ?                       
                where id = ?
                """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDate(1, Date.valueOf(apod.getDate()));
            preparedStatement.setString(2, apod.getUrl());
            preparedStatement.setString(3, apod.getTitle());
            preparedStatement.setString(4, apod.getExplanation());
            preparedStatement.setString(5, apod.getCopyright());
            preparedStatement.setInt(6, apod.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al actualizar la base de datos");
            e.printStackTrace();
        }
    }


    public void update(int id, Apod apod) {
        String query = """
                update apods
                set             date = ?,
                                url = ?,
                                title = ?,
                                explanation = ?,
                                copyright = ?                       
                where id = ?
                """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDate(1, Date.valueOf(apod.getDate()));
            preparedStatement.setString(2, apod.getUrl());
            preparedStatement.setString(3, apod.getTitle());
            preparedStatement.setString(4, apod.getExplanation());
            preparedStatement.setString(5, apod.getCopyright());
            preparedStatement.setInt(6, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al actualizar la base de datos");
            e.printStackTrace();
        }
    }


    @Override
    public void delete(Apod apod) {
        String query = """
                delete from apods
                where id = ?
                """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, apod.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al eliminar registro de la base de datos");
            e.printStackTrace();
        }
    }

    @Override
    public boolean exists(Apod apod) {
        String query = "select count(*) from apods where id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, apod.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt("id") != 0;
        } catch (SQLException e) {
            logger.error("Error al comprobar la existencia de registro: {}", e.getMessage());
        }
        return false;
    }
}
