package damm.it.proyectoud1samuelmanuel.daos.users;

import damm.it.proyectoud1samuelmanuel.Main;
import damm.it.proyectoud1samuelmanuel.models.User;
import damm.it.proyectoud1samuelmanuel.services.CypherService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class UserPropDAO implements UserDAO {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public User read(String loginData) {
        String[] encryptUsers;

        try(InputStream in = Main.class.getResourceAsStream("users.dat"); BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(in)))) {
            encryptUsers = reader.lines().toArray(String[]::new);
        } catch (IOException e) {
            logger.error("Error al leer el fichero de usuarios: {}", e.getMessage());
            return null;
        }

        String userRaw;
        for (String encryptUser : encryptUsers) {
            if ((userRaw = CypherService.decryptUsers(encryptUser, loginData)) != null) {
                String[] tokens = userRaw.split(":");
                if (tokens.length != 2)
                    return null;

                return new User(tokens[0], tokens[1]);
            }
        }

        return null;
    }
}
