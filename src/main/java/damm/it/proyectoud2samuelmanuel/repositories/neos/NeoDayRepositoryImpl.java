package damm.it.proyectoud2samuelmanuel.repositories.neos;

import damm.it.proyectoud2samuelmanuel.daos.neos.NeoDayDBDAO;
import damm.it.proyectoud2samuelmanuel.daos.neos.NeoDayExtDAO;
import damm.it.proyectoud2samuelmanuel.models.Neo;
import damm.it.proyectoud2samuelmanuel.models.NeoDay;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * Repositorio para los datos de NEOs diarios.
 */
public class NeoDayRepositoryImpl implements NeoDayRepository {
    private NeoDayDBDAO neoDayDBDAO;
    private NeoDayExtDAO neoDayExtDAO;

    /**
     * Constructor del repositorio.
     */
    public NeoDayRepositoryImpl() {
        NeoDayDBDAO neoDayDBDAO = new NeoDayDBDAO();
        NeoDayExtDAO neoDayExtDAO = new NeoDayExtDAO();
    }

    /**
     * Método para obtener los datos de NEOs de un dia determinado.
     *
     * @param date Dia del que obtener los NEO
     * @return Los NEO para el dia especificado
     * @throws NoSuchElementException Si no se puede obtener los NEO
     */
    @Override
    public NeoDay get(LocalDate date) throws NoSuchElementException {
        String dateStr = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        neoDayDBDAO.read(dateStr);
        throw new NoSuchElementException("No se ha podido obtener los datos del NEO.");
    }

    @Override
    public void add(NeoDay obj) {

    }

    @Override
    public void update(NeoDay obj) {

    }

    @Override
    public void remove(NeoDay obj) {

    }

    @Override
    public boolean exists(LocalDate id) {
        return false;
    }


    /**
     * Método para obtener los datos de NEOs entre dos dias determinados.
     *
     * @param dateStart Fecha de inicio para obtener los datos
     * @param dateEnd   Fecha de finalización para obtener los datos
     * @param predicate Filtro de NEOs que serán devueltos
     * @return La lista de datos diarios de NEO coincidentes con los parámetros especificados
     * @throws NoSuchElementException Si no se pueden obtener los datos
     */
    @Override
    public List<NeoDay> getBetween(LocalDate dateStart, LocalDate dateEnd, Predicate<Neo> predicate) throws NoSuchElementException {
        int lapse = (int) DAYS.between(dateStart, dateEnd) + 1;
        List<NeoDay> neoDays = new LinkedList<>();

        for (int i = 0; i < lapse; i++) {
            LocalDate localDate = dateStart.plusDays(i);

            NeoDay neoDay = get(localDate);
            neoDay.getNeos().removeIf(predicate);

            if (!neoDay.getNeos().isEmpty())
                neoDays.add(neoDay);
        }
        return neoDays;
    }

    /**
     * Método para exportar los datos de NEO de un dia como json.
     *
     * @param neoDay  Datos de NEO a guardar
     * @param outFile El archivo en que se desean guardar los datos
     */
    @Override
    public void saveExternalJson(NeoDay neoDay, File outFile) {
        neoDayExtDAO.writeJson(neoDay, outFile);
    }

    /**
     * Método para exportar los datos de NEO de un dia como csv.
     *
     * @param neoDay  Datos de NEO a guardar
     * @param outFile El archivo en que se desean guardar los datos
     */
    @Override
    public void saveExternalCsv(NeoDay neoDay, File outFile) {
        neoDayExtDAO.writeCsv(neoDay, outFile);
    }
}
