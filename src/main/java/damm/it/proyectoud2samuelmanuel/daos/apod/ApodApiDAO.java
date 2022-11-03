package damm.it.proyectoud2samuelmanuel.daos.apod;

import com.fasterxml.jackson.databind.ObjectMapper;
import damm.it.proyectoud2samuelmanuel.entities.apod.ApodEntity;
import damm.it.proyectoud2samuelmanuel.models.Apod;
import damm.it.proyectoud2samuelmanuel.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URL;

public class ApodApiDAO implements ApodDAO {
    private static final Logger logger = LogManager.getLogger();

    private static final String QUERY = "https://api.nasa.gov/planetary/apod?api_key=%s&date=%s&thumbs=true";

    public Apod read(String date) {
        ApodEntity apodEntity;

        try {
            ObjectMapper om = new ObjectMapper();
            apodEntity = om.readValue(new URL(String.format(QUERY, UserService.getActiveUser().getApiKey(), date)), ApodEntity.class);
        } catch (IOException e) {
            logger.error("Error al obtener la imagen del dia {}", date);
            return null;
        }

        String entityDate = apodEntity.getDate();
        String entityUrl = apodEntity.getMediaType().equals("video") ? apodEntity.getThumbnailUrl() : apodEntity.getUrl();
        String entityTitle = apodEntity.getTitle();
        String entityExplanation = apodEntity.getExplanation();
        String entityCopyright = apodEntity.getCopyright();

        return new Apod(entityDate, entityUrl, entityTitle, entityExplanation, entityCopyright);
    }
}
