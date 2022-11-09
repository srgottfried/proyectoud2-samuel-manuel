package damm.it.proyectoud2samuelmanuel.daos;

import damm.it.proyectoud2samuelmanuel.models.Request;
import damm.it.proyectoud2samuelmanuel.models.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.NoSuchElementException;

public class RequestDAO implements DAO<Request, User> {
    private static final Logger logger = LogManager.getLogger();

    public RequestDAO() {
    }

    @Override
    public Request get(User user) throws NoSuchElementException {
        if (!Files.exists(Path.of(".cache/users/" + user.getName() + "/lastRequest.cache")))
            throw new NoSuchElementException("No se ha encontrado la petición");

        Request request;

        try (ObjectInputStream cache = new ObjectInputStream(new FileInputStream(".cache/users/" + user.getName() + "/lastRequest.cache"))) {
            request = (Request) cache.readObject();
        } catch (IOException | ClassNotFoundException e) {
            logger.error("No se ha ha podido leer los datos de la última petición del usuario {} de caché: {}", user.getName(), e.getMessage());
            throw new NoSuchElementException("No se ha encontrado la petición");
        }

        return request;
    }

    @Override
    public void add(Request request) {
        try {
            Files.createDirectories(Path.of(".cache/users/" + request.getUser()));
        } catch (IOException e) {
            logger.error("No se ha podido crear el directorio del usuario {} de caché: {}", request.getUser(),e.getMessage());
            return;
        }

        try (ObjectOutputStream cache = new ObjectOutputStream(new FileOutputStream(".cache/users/" + request.getUser() + "/lastRequest.cache"))) {
            cache.writeObject(request);
        } catch (IOException e) {
            logger.error("No se ha podido guardar los datos de la última petición del usuario {} en caché: {}", request.getUser(), e.getMessage());
        }
    }

    @Override
    public void update(Request request) throws NoSuchElementException {
        add(request);
    }

    @Override
    public void remove(Request request) throws NoSuchElementException {
        Path path = Path.of(".cache/users/" + request.getUser() + "/lastRequest.cache");

        try {
            if (!Files.exists(path))
                throw new IOException("No existe el fichero.");

            Files.delete(path);
        } catch (IOException e) {
            logger.error("No se ha podido borrar los datos de la última petición del usuario {} de caché: {}", request.getUser(), e.getMessage());
            throw new NoSuchElementException("No se ha encontrado la petición");
        }
    }

    @Override
    public boolean exists(User user) {
        return Files.exists(Path.of(".cache/users/" + user.getName() + "/lastRequest.cache"));
    }
}
