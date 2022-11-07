package damm.it.proyectoud2samuelmanuel.db;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public abstract class ConnectionManager {
    private static Path credentialsPath = Path.of("src/main/resources/damm/it/proyectoud2samuelmanuel/db/credentials");

    public static Path getCredentialsPath() {
        return credentialsPath;
    }

    public static void setCredentialsPath(Path newPath) {
        credentialsPath = newPath;
    }

    public static Connection getConnection(String databaseName) {
        Properties properties = new Properties();
        try {
            properties.load(Files.newBufferedReader(credentialsPath));
            String[] credentials = properties.getProperty(databaseName).split("%");
            return DriverManager.getConnection(
                    credentials[0],
                    credentials[1],
                    credentials[2]
            );
        } catch (SQLException e) {
            System.out.println("Error al conectarse a la base de datos.");
        } catch (IOException e) {
            System.out.println("Error al acceder al fichero de credenciales de la base de datos.");
        } catch (NullPointerException e) {
            System.out.println("Base de datos no encontrada en fichero de credenciales.");
        }
        return null;
    }

    public static void main(String[] args) throws SQLException {
        Connection c = ConnectionManager.getConnection("login_nasa");
        if (c != null) {
            c.close();
        }

    }

}

