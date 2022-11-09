package damm.it.proyectoud2samuelmanuel.services;


import damm.it.proyectoud2samuelmanuel.Main;
import damm.it.proyectoud2samuelmanuel.models.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public abstract class SqlService {
    private static final Logger logger = LogManager.getLogger();
    private static final Properties properties = new Properties();
    public static Connection connectionNasa;
    public static Connection connectionLogin;

    public static Connection getConnectionLogin() {
        try {
            if (connectionLogin == null || connectionLogin.isClosed()) {
                properties.load(Main.class.getResourceAsStream("db/credentials"));
                connectionLogin = DriverManager.getConnection(
                        properties.getProperty("URL_LOGIN"),
                        properties.getProperty("USER_LOGIN"),
                        properties.getProperty("PWD_LOGIN")
                );
            }
        } catch (SQLException e) {
            logger.error("No se ha podido conectar a la base de datos login_nasa: {}", e.getMessage());
        } catch (IOException e) {
            logger.error("No se ha podido acceder al fichero de credenciales de la base de datos: {}", e.getMessage());
        } catch (NullPointerException e) {
            logger.error("No se ha encontrado una base de datos válida en el fichero de credenciales: {}", e.getMessage());
        }
        return connectionLogin;
    }

    public static Connection getConnectionNasa() {
        User activeUser = UserService.getActiveUser();
        if (activeUser == null)
            return null;

        try {
            if (connectionNasa == null || connectionNasa.isClosed()) {
                properties.load(Main.class.getResourceAsStream("db/credentials"));
                connectionNasa = DriverManager.getConnection(
                        properties.getProperty("URL_NASA"),
                        activeUser.getName(),
                        activeUser.getDbKey()
                );
            }
        } catch (SQLException e) {
            logger.error("No se ha podido conectar a la base de datos nasa: {}", e.getMessage());
        } catch (IOException e) {
            logger.error("No se ha podido acceder al fichero de credenciales de la base de datos: {}", e.getMessage());
        } catch (NullPointerException e) {
            logger.error("No se ha encontrado una base de datos válida en el fichero de credenciales: {}", e.getMessage());
        }
        return connectionNasa;
    }

    public static void closeNasa() {
        try {
            if (connectionNasa != null) {
                connectionNasa.close();
                connectionNasa = null;
            }
        } catch (SQLException e) {
            logger.error("No se ha podido cerrar la conexión con la base de datos: {}", e.getMessage());
        }
    }

    public static void close() {
        try {
            if (connectionLogin != null) {
                connectionLogin.close();
                connectionLogin = null;
            }

            if (connectionNasa != null) {
                connectionNasa.close();
                connectionNasa = null;
            }
        } catch (SQLException e) {
            logger.error("No se ha podido cerrar la conexión con la base de datos: {}", e.getMessage());
        }
    }
}

