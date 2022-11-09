package damm.it.proyectoud2samuelmanuel.daos;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
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
import java.util.function.Predicate;

import static java.time.temporal.ChronoUnit.DAYS;

public class NeoDayDAO implements DAO<NeoDay, LocalDate> {
    private final static Logger logger = LogManager.getLogger();
    private final static NeoDAO neoDAO = new NeoDAO();
    private final Connection connection;

    public NeoDayDAO() {
        this.connection = SqlService.getConnectionNasa();
    }

    @Override
    public NeoDay get(LocalDate localDate) throws NoSuchElementException {
        return new NeoDay(localDate, neoDAO.getByDate(localDate, null));
    }

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

    @Override
    public void add(NeoDay neoDay) {
        neoDay.getNeos().forEach(neoDAO::add);
    }

    @Override
    public void update(NeoDay neoDay) throws NoSuchElementException {
        neoDay.getNeos().forEach(neoDAO::update);
    }

    @Override
    public void remove(NeoDay neoDay) throws NoSuchElementException {
        neoDay.getNeos().forEach(neoDAO::remove);
    }

    @Override
    public boolean exists(LocalDate localDate) {
        String query = """
            SELECT count(*)
                FROM neos
            WHERE date = ?
            """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                return resultSet.getInt("id") != 0;

        } catch (SQLException e) {
            logger.error("No se ha podido comprobar la existencia del NEO de la base de datos: {}", e.getMessage());
        }

        return false;
    }

    public void saveJson(NeoDay neoDay, File outfile) {
        ObjectMapper mapper =  new ObjectMapper();

        try {
            mapper.writeValue(outfile, neoDay);
        } catch (IOException e) {
            logger.error("No se ha podido guardar en el fichero {} los datos de NEOs del dia {}: {}", outfile, neoDay.getDate(), e.getMessage());
        }
    }

    public void saveCsv(NeoDay neoDay, File outfile) {
        CsvMapper mapper =  new CsvMapper();
        CsvSchema schema = mapper.schemaFor(Neo.class);
        StringBuilder csv = new StringBuilder(schema.getColumnDesc().replaceAll("[\\[\\]\"]", "")).append("\n");

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outfile))) {
            for (Neo neo : neoDay.getNeos())
                csv.append(mapper.writer(schema.withUseHeader(false)).writeValueAsString(neo));

            bw.write(csv.toString());
        } catch (IOException e) {
            logger.error("No se ha podido guardar en el fichero {} los datos de NEOs del dia {}: {}", outfile, neoDay.getDate(), e.getMessage());
        }
    }
}
