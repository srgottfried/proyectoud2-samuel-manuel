package damm.it.proyectoud1samuelmanuel.daos.requests;

import damm.it.proyectoud1samuelmanuel.models.Request;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class RequestCacheDAO implements RequestDAO {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public void create(Request request) {
        try {
            Files.createDirectories(Path.of(".cache/users/" + request.getUser()));
        } catch (IOException e) {
            logger.error("Error al crear el directorio de caché del usuario {}: {}", request.getUser(),e.getMessage());
            return;
        }

        try (ObjectOutputStream cache = new ObjectOutputStream(new FileOutputStream(".cache/users/" + request.getUser() + "/lastRequest.cache"))) {
            cache.writeObject(request);
        } catch (IOException e) {
            logger.error("Error al guardar en caché los datos de la última petición del usuario {}: {}", request.getUser(), e.getMessage());
        }
    }

    @Override
    public Request read(String name) {
        if (!Files.exists(Path.of(".cache/users/" + name + "/lastRequest.cache")))
            return null;

        Request request;

        try (ObjectInputStream cache = new ObjectInputStream(new FileInputStream(".cache/users/" + name + "/lastRequest.cache"))) {
            request = (Request) cache.readObject();
        } catch (IOException | ClassNotFoundException e) {
            logger.error("Error al leer de caché los datos de la última petición del usuario {}: {}", name, e.getMessage());
            return null;
        }

        return request;
    }

    @Override
    public void update(Request request) {
        create(request);
    }

    @Override
    public void delete(String name) {
        Path path = Path.of(".cache/users/" + name + "/lastRequest.cache");

        try {
            if (!Files.exists(path))
                throw new IOException("No existe el fichero.");

            Files.delete(path);
        } catch (IOException e) {
            logger.error("Error al borrar de caché los datos de la última petición del usuario {}: {}", name, e.getMessage());
        }
    }

    @Override
    public boolean exists(String name) {
        return Files.exists(Path.of(".cache/users/" + name + "/lastRequest.cache"));
    }
}
