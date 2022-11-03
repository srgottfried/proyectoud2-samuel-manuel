package damm.it.proyectoud1samuelmanuel.daos.neos;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import damm.it.proyectoud1samuelmanuel.entities.noes.NeoEntity;
import damm.it.proyectoud1samuelmanuel.models.Neo;
import damm.it.proyectoud1samuelmanuel.models.NeoDay;
import damm.it.proyectoud1samuelmanuel.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URL;

public class NeoDayApiDAO implements NeoDayDAO {
    private static final Logger logger = LogManager.getLogger();

    private static final String QUERY = "https://api.nasa.gov/neo/rest/v1/feed?api_key=%s&start_date=%s&end_date=%s";

    public NeoDay read(String date) {
        ObjectMapper om = new ObjectMapper();
        NeoDay neos = new NeoDay(date);

        try {
            JsonNode tree = om.readTree(new URL(String.format(QUERY, UserService.getActiveUser().getApiKey(), date, date)));
            JsonNode specificTree = tree.get("near_earth_objects").get(date);

            NeoEntity neoEntity;

            for (int j = 0; j < specificTree.size(); j++) {
                neoEntity = om.treeToValue(specificTree.get(j), NeoEntity.class);

                String name = neoEntity.getName();
                double diameter = Math.floor((neoEntity.getEstimatedDiameter().getMeters().getEstimatedDiameterMax() + neoEntity.getEstimatedDiameter().getMeters().getEstimatedDiameterMin()) * 50.0) / 100.0;
                double minDistance = Math.floor(Double.parseDouble(neoEntity.getCloseApproachData().get(0).getMissDistance().getLunar()) * 100.0) / 100.0;
                double speed = Math.floor(Double.parseDouble(neoEntity.getCloseApproachData().get(0).getRelativeVelocity().getKilometersPerSecond()) * 100.0) / 100.0;
                boolean potentialllyHazardous = neoEntity.isIsPotentiallyHazardousAsteroid();

                neos.getNeos().add(new Neo(name, diameter, minDistance, speed, potentialllyHazardous));
            }
        } catch (IOException e) {
            logger.error("Error al obtener los datos de neos del dia {}", date);
            return null;
        }

        return neos;
    }
}
