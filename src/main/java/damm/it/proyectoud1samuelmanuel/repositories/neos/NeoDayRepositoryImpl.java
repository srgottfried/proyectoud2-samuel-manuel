package damm.it.proyectoud1samuelmanuel.repositories.neos;

import damm.it.proyectoud1samuelmanuel.daos.neos.NeoDayApiDAO;
import damm.it.proyectoud1samuelmanuel.daos.neos.NeoDayCacheDAO;
import damm.it.proyectoud1samuelmanuel.daos.neos.NeoDayExtDAO;
import damm.it.proyectoud1samuelmanuel.models.Neo;
import damm.it.proyectoud1samuelmanuel.models.NeoDay;

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
    private final NeoDayApiDAO neoDayApiDAO;
    private final NeoDayCacheDAO neoDayCacheDAO;
    private final NeoDayExtDAO neoDayExtDAO;

    /**
     * Constructor del repositorio.
     */
    public NeoDayRepositoryImpl() {
        neoDayApiDAO = new NeoDayApiDAO();
        neoDayCacheDAO = new NeoDayCacheDAO();
        neoDayExtDAO = new NeoDayExtDAO();
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
        NeoDay neoDay;

        String dateStr = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        if ((neoDay = neoDayCacheDAO.read(dateStr)) != null)
            return neoDay;

        if ((neoDay = neoDayApiDAO.read(dateStr)) != null) {
            neoDayCacheDAO.create(neoDay);
            return neoDay;
        }

        throw new NoSuchElementException("No se ha podido obtener los datos del NEO.");
    }

    /**
     * Método para almacenar un nuevo dia de NEOs.
     *
     * @param neoDay Los NEOs para un determinado dia
     */
    @Override
    public void add(NeoDay neoDay) {
        neoDayCacheDAO.create(neoDay);
    }

    /**
     * Método para actualizar los datos de NEOs de un determinado dia.
     *
     * @param neoDay Datos de NEOs a actualizar.
     */
    @Override
    public void update(NeoDay neoDay) {
        neoDayCacheDAO.update(neoDay);
    }

    /**
     * Método para eliminar los datos de NEOs de un determinado dia.
     *
     * @param neoDay Datos de NEO a eliminar.
     */
    @Override
    public void remove(NeoDay neoDay) {
        neoDayCacheDAO.delete(neoDay.getDate());
    }

    /**
     * Método para obtener los datos de NEOs entre dos dias determinados.
     *
     * @param dateStart Fecha de inicio para obtener los datos
     * @param dateEnd Fecha de finalización para obtener los datos
     * @param predicate Filtro de NEOs que serán devueltos
     * @return La lista de datos diarios de NEO coincidentes con los parámetros especificados
     * @throws NoSuchElementException Si no se pueden obtener los datos
     */
    @Override
    public List<NeoDay> getBetween(LocalDate dateStart, LocalDate dateEnd, Predicate<Neo> predicate) throws NoSuchElementException {
        int lapse = (int)DAYS.between(dateStart, dateEnd) + 1;
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
     * Método para comprobar si existen datos de un NEO.
     *
     * @param date Dia del que se quiere comprobar si existen datos
     * @return True si existen datos de ese dia, false en caso contrario
     */
    @Override
    public boolean exists(LocalDate date) {
        return neoDayCacheDAO.exists(date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }

    /**
     * Método para exportar los datos de NEO de un dia como json.
     *
     * @param neoDay Datos de NEO a guardar
     * @param outFile El archivo en que se desean guardar los datos
     */
    @Override
    public void saveExternalJson(NeoDay neoDay, File outFile) {
        neoDayExtDAO.writeJson(neoDay, outFile);
    }

    /**
     * Método para exportar los datos de NEO de un dia como csv.
     *
     * @param neoDay Datos de NEO a guardar
     * @param outFile El archivo en que se desean guardar los datos
     */
    @Override
    public void saveExternalCsv(NeoDay neoDay, File outFile) {
        neoDayExtDAO.writeCsv(neoDay, outFile);
    }
}
