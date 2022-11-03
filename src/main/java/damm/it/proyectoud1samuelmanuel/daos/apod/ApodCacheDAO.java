package damm.it.proyectoud1samuelmanuel.daos.apod;

import damm.it.proyectoud1samuelmanuel.models.Apod;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class ApodCacheDAO implements ApodDAO {
    private static final Logger logger = LogManager.getLogger();

    public void create(Apod apod) {
        try {
            Files.createDirectories(Path.of(".cache/apod"));
        } catch (IOException e) {
            logger.error("Error al crear el directorio de caché de Apod del dia: {}", e.getMessage());
            return;
        }

        try (ObjectOutputStream cache = new ObjectOutputStream(new FileOutputStream(".cache/apod/" + apod.getDate() + ".cache"))) {
            Image img = new Image(apod.getUrl());

            String extension = apod.getUrl().substring(apod.getUrl().lastIndexOf(".") + 1);

            File outFile = new File(".cache/apod/" + apod.getDate() + "." + extension);
            ImageIO.write(SwingFXUtils.fromFXImage(img, null), extension, outFile);

            apod.setUrl("file:" + Path.of(System.getProperty("user.dir")).toUri().relativize(outFile.toURI()));
            cache.writeObject(apod);
        } catch (IOException e) {
            logger.error("Error al guardar en caché la imagen del dia {}: {}", apod.getDate(), e.getMessage());
        }
    }

    public Apod read(String date) {
        if (!Files.exists(Path.of(".cache/apod/" + date + ".cache")))
            return null;

        Apod apod;

        try (ObjectInputStream cache = new ObjectInputStream(new FileInputStream(".cache/apod/" + date + ".cache"))) {
            apod = (Apod) cache.readObject();
        } catch (IOException | ClassNotFoundException e) {
            logger.error("Error al leer de caché la imagen del dia {}: {}", date, e.getMessage());
            return null;
        }

        return apod;
    }

    public void update(Apod apod) {
        create(apod);
    }

    public void delete(String date) {
        Path path = Path.of(".cache/apod/" + date + ".cache");

        if (!Files.exists(path))
            return;

        try {
            Files.delete(path);
        } catch (IOException e) {
            logger.error("Error al borrar de caché la imagen del dia {}: {}", date, e.getMessage());
        }
    }

    public boolean exists(String date) {
        return Files.exists(Path.of(".cache/apod/" + date + ".cache"));
    }
}
