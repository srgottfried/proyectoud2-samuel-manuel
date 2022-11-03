package damm.it.proyectoud1samuelmanuel.daos.neos;

import damm.it.proyectoud1samuelmanuel.models.NeoDay;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class NeoDayCacheDAO implements NeoDayDAO {
    private static final Logger logger = LogManager.getLogger();

    public void create(NeoDay neoDay) {
        try {
            Files.createDirectories(Path.of(".cache/neos"));
        } catch (IOException e) {
            logger.error("Error al crear el directorio de caché de NEOs del dia: {}", e.getMessage());
            return;
        }

        try (ObjectOutputStream cache = new ObjectOutputStream(new FileOutputStream(".cache/neos/" + neoDay.getDate() + ".cache"))) {
            cache.writeObject(neoDay);
        } catch (IOException e) {
            logger.error("Error al guardar en caché los datos de NEOs del dia {}: {}", neoDay.getDate(), e.getMessage());
        }
    }

    public NeoDay read(String date) {
        if (!Files.exists(Path.of(".cache/neos/" + date + ".cache")))
            return null;

        NeoDay neo;

        try (ObjectInputStream cache = new ObjectInputStream(new FileInputStream(".cache/neos/" + date + ".cache"))) {
            neo = (NeoDay) cache.readObject();
        } catch (IOException | ClassNotFoundException e) {
            logger.error("Error al leer de caché los datos de NEOs del dia {}: {}", date, e.getMessage());
            return null;
        }

        return neo;
    }

    public void update(NeoDay neoDay) {
        create(neoDay);
    }

    public void delete(String date) {
        Path path = Path.of(".cache/neos/" + date + ".cache");

        try {
            if (!Files.exists(path))
                throw new IOException("No existe el fichero.");

            Files.delete(path);
        } catch (IOException e) {
            logger.error("Error al borrar de caché los datos de NEOs del dia {}: {}", date, e.getMessage());
        }
    }

    public boolean exists(String date) {
        return Files.exists(Path.of(".cache/neos/" + date + ".cache"));
    }
}
