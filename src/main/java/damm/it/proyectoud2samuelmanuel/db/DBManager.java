//package damm.it.proyectoud2samuelmanuel.db;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import damm.it.proyectoud2samuelmanuel.daos.NeoDAO;
//import damm.it.proyectoud2samuelmanuel.entities.neos.NeoEntity;
//import damm.it.proyectoud2samuelmanuel.models.Neo;
//import damm.it.proyectoud2samuelmanuel.models.NeoDay;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//import java.io.IOException;
//import java.net.URL;
//import java.sql.Connection;
//import java.sql.Date;
//import java.time.LocalDate;
//
//public abstract class DBManager {
//    private static final Logger logger = LogManager.getLogger();
//    private Connection connection = ConnectionManager.getConnection("nasa");
//    private static final String QUERY = "https://api.nasa.gov/neo/rest/v1/feed?api_key=%s&start_date=%s&end_date=%s";
//
//    private static void update() {
//        NeoDAO neoDAO = new NeoDAO();
//        LocalDate date = LocalDate.of(2022, 11, 1);
//        while (!date.equals(LocalDate.now())) {
//            date = date.plusDays(1);
//
//            NeoDay neoDay = read(date.toString());
//            neoDay.getNeos().forEach(n -> {
//                neoDAO.add((new Neo(
//                        n.getName(),
//                        n.getDiameter(),
//                        n.getMinDistance(),
//                        n.getSpeed(),
//                        n.isHazardous(),
//                        Date.valueOf(neoDay.getDate()).toLocalDate()
//                )));
//            });
//        }
//
//
//    }
//
//    public static void main(String[] args) {
//        try {
//            update();
//        } catch (Exception e) {
//        e.printStackTrace();
//        }
//    }
//
//
//    private static NeoDay read(String date) {
//        ObjectMapper om = new ObjectMapper();
//        NeoDay neos = new NeoDay(date);
//
//        try {
//            JsonNode tree = om.readTree(new URL(String.format(QUERY, "DEMO_KEY", date, date)));
//            JsonNode specificTree = tree.get("near_earth_objects").get(date);
//
//            NeoEntity neoEntity;
//
//            for (int j = 0; j < specificTree.size(); j++) {
//                neoEntity = om.treeToValue(specificTree.get(j), NeoEntity.class);
//
//                String name = neoEntity.getName();
//                double diameter = Math.floor((neoEntity.getEstimatedDiameter().getMeters().getEstimatedDiameterMax() + neoEntity.getEstimatedDiameter().getMeters().getEstimatedDiameterMin()) * 50.0) / 100.0;
//                double minDistance = Math.floor(Double.parseDouble(neoEntity.getCloseApproachData().get(0).getMissDistance().getLunar()) * 100.0) / 100.0;
//                double speed = Math.floor(Double.parseDouble(neoEntity.getCloseApproachData().get(0).getRelativeVelocity().getKilometersPerSecond()) * 100.0) / 100.0;
//                boolean potentialllyHazardous = neoEntity.isIsPotentiallyHazardousAsteroid();
//
//                neos.getNeos().add(new Neo(name, diameter, minDistance, speed, potentialllyHazardous));
//            }
//        } catch (IOException e) {
//            logger.error("Error al obtener los datos de neos del dia {}", date);
//            return null;
//        }
//
//        return neos;
//    }
//
//}
