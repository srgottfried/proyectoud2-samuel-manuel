package damm.it.proyectoud2samuelmanuel.daos;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import damm.it.proyectoud2samuelmanuel.services.SqlService;
import damm.it.proyectoud2samuelmanuel.models.Neo;
import damm.it.proyectoud2samuelmanuel.models.NeoDay;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Predicate;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * Clase que implementa el acceso a datos de NeoDay.
 */
public class NeoDayDAO implements DAO<NeoDay, LocalDate> {
    private final static Logger logger = LogManager.getLogger();
    private final static NeoDAO neoDAO = new NeoDAO();

    public NeoDayDAO() {
    }

    /**
     * Obtiene un NeoDay a partir  de una fecha de dada.
     * @param localDate
     * @return NeoDay
     * @throws NoSuchElementException
     */
    @Override
    public NeoDay get(LocalDate localDate) throws NoSuchElementException {
        return new NeoDay(localDate, neoDAO.getByDate(localDate, null));
    }

    /**
     * Obtiene lista de NeoDays a partir de un intervalo temporal dado. Admite un predicado para filtrar elementos.
     * @param dateStart
     * @param dateEnd
     * @param predicate
     * @return Lista de neos
     * @throws NoSuchElementException
     */
    public List<NeoDay> getByLapse(LocalDate dateStart, LocalDate dateEnd, Predicate<Neo> predicate) throws NoSuchElementException {
        int lapse = (int)DAYS.between(dateStart, dateEnd) + 1;
        List<NeoDay> neoDays = new ArrayList<>();

        for (int i = 0; i < lapse; i++) {
            LocalDate localDate = dateStart.plusDays(i);

            List<Neo> neos = neoDAO.getByDate(localDate, predicate);
            if (!neos.isEmpty())
                neoDays.add(new NeoDay(localDate, neos));
        }
        return neoDays;
    }

    /**
     * Añade NeoDay.
     * @param neoDay
     */
    @Override
    public void add(NeoDay neoDay) {
        neoDay.getNeos().forEach(neoDAO::add);
    }

    /**
     * Actualiza NeoDay.
     * @param neoDay
     * @throws NoSuchElementException
     */
    @Override
    public void update(NeoDay neoDay) throws NoSuchElementException {
        neoDay.getNeos().forEach(neoDAO::update);
    }

    /**
     * Borra NeoDay.
     * @param neoDay
     * @throws NoSuchElementException
     */
    @Override
    public void remove(NeoDay neoDay) throws NoSuchElementException {
        neoDay.getNeos().forEach(neoDAO::remove);
    }

    /**
     * Comprueba existencia de Neos por fecha.
     * @param localDate
     * @return si existe.
     */
    @Override
    public boolean exists(LocalDate localDate) {
        String query = """
            SELECT count(*)
                FROM neos
            WHERE date = ?
            """;

        try (PreparedStatement ps = Objects.requireNonNull(SqlService.getConnectionNasa()).prepareStatement(query)) {
            ps.setString(1, localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next())
                return resultSet.getInt("id") != 0;

        } catch (SQLException e) {
            logger.error("No se ha podido comprobar la existencia del NEO de la base de datos: {}", e.getMessage());
        }

        return false;
    }

    /**
     * Almacena datos en Json.
     * @param neoDay
     * @param outfile
     */
    public void saveJson(NeoDay neoDay, File outfile) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JSR310Module());

        try {
            mapper.writeValue(outfile, neoDay);
        } catch (IOException e) {
            logger.error("No se ha podido guardar en el fichero {} los datos de NEOs del dia {}: {}", outfile, neoDay.getDate(), e.getMessage());
        }
    }

    /**
     * Almacena datos en CSV.
     * @param neoDay
     * @param outfile
     */
    public void saveCsv(NeoDay neoDay, File outfile) {
        CsvMapper mapper =  new CsvMapper();
        CsvSchema schema = mapper.schemaFor(Neo.class);
        StringBuilder csv = new StringBuilder(schema.getColumnDesc().replaceAll("[\\[\\]\"]", "")).append("\n");
        mapper.registerModule(new JSR310Module());

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outfile))) {
            for (Neo neo : neoDay.getNeos())
                csv.append(mapper.writer(schema.withUseHeader(false)).writeValueAsString(neo));

            bw.write(csv.toString());
        } catch (IOException e) {
            logger.error("No se ha podido guardar en el fichero {} los datos de NEOs del dia {}: {}", outfile, neoDay.getDate(), e.getMessage());
        }
    }
}
