package damm.it.proyectoud2samuelmanuel.db;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public abstract class ConnectionManager {
    private static Path defaultCredentialPath = Path.of("src/main/resources/damm/it/proyectoud2samuelmanuel");

    public static Path getDefaultCredentialPath() {
        return defaultCredentialPath;
    }

    public static void setDefaultCredentialPath(Path newPath) {
        defaultCredentialPath = newPath;
    }

    public static Connection getConnection() {
        Properties properties = new Properties();
        try {
            properties.load(Files.newBufferedReader(defaultCredentialPath));
            return DriverManager.getConnection(
                    properties.getProperty("url"),
                    properties.getProperty("user"),
                    properties.getProperty(("password"))
            );
        } catch (SQLException e) {
            System.out.println("Error al conectarse a la base de datos.");
        } catch (IOException e) {
            System.out.println("Error al acceder al fichero de credenciales de la base de datos.");
        }
        return null;
    }
}

