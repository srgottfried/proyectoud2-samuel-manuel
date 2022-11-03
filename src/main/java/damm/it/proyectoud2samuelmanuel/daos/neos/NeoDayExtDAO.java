package damm.it.proyectoud2samuelmanuel.daos.neos;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import damm.it.proyectoud2samuelmanuel.models.Neo;
import damm.it.proyectoud2samuelmanuel.models.NeoDay;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class NeoDayExtDAO implements NeoDayDAO {
    private static final Logger logger = LogManager.getLogger();

    public void writeJson(NeoDay neoDay, File outfile) {
        ObjectMapper mapper =  new ObjectMapper();

        try {
            mapper.writeValue(outfile, neoDay);
        } catch (IOException e) {
            logger.error("Error al guardar en el fichero {} los datos de NEOs del dia {}: {}", outfile, neoDay.getDate(), e.getMessage());
        }
    }

    public void writeCsv(NeoDay neoDay, File outfile) {
        CsvMapper mapper =  new CsvMapper();
        CsvSchema schema = mapper.schemaFor(Neo.class);
        StringBuilder csv = new StringBuilder(schema.getColumnDesc().replaceAll("[\\[\\]\"]", "")).append("\n");

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outfile))) {
            for (Neo neo : neoDay.getNeos())
                csv.append(mapper.writer(schema.withUseHeader(false)).writeValueAsString(neo));

            bw.write(csv.toString());
        } catch (IOException e) {
            logger.error("Error al guardar en el fichero {} los datos de NEOs del dia {}: {}", outfile, neoDay.getDate(), e.getMessage());
        }
    }
}
