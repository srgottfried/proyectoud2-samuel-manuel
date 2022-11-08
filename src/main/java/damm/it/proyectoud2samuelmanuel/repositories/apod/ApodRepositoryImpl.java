package damm.it.proyectoud2samuelmanuel.repositories.apod;

import damm.it.proyectoud2samuelmanuel.daos.apod.ApodDBDAO;
import damm.it.proyectoud2samuelmanuel.models.Apod;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;

/**
 * Repositorio para las imágenes del dia.
 */
public class ApodRepositoryImpl implements ApodRepository {
    private final ApodDBDAO apodDBDAO;

    /**
     * Constructor del repositorio.
     */
    public ApodRepositoryImpl() {
        apodDBDAO = new ApodDBDAO();
    }

    /**
     * Método para obtener la imagen de un dia dado.
     *
     * @param date Dia del que obtener la imagen
     * @return La imagen correspondiente al dia indicado
     * @throws NoSuchElementException Si no se puede obtener esa imagen del dia
     */
    @Override
    public Apod get(LocalDate date) throws NoSuchElementException {
        String dateStr = date == null ? LocalDate.now(ZoneOffset.UTC).toString() : date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return apodDBDAO.read(dateStr);
    }

    /**
     * Método para almacenar una imagen del dia.
     *
     * @param apod Imagen del dia a almacenar
     */
    @Override
    public void add(Apod apod) {
        apodDBDAO.create(apod);
    }

    /**
     * Método para actualizar una imagen del dia.
     *
     * @param apod Imagen del dia a actualizar
     */
    @Override
    public void update(Apod apod) {
        apodDBDAO.update(apod);
    }

    /**
     * Método para eliminar una imagen del dia.
     *
     * @param apod Imagen del dia a eliminar
     */
    @Override
    public void remove(Apod apod) {
        apodDBDAO.delete(apod.getDate());
    }

    /**
     * Método para comprobar si existen datos de un Apod.
     *
     * @param date Dia del que se quiere comprobar si existen datos
     * @return True si existen datos de ese dia, false en caso contrario
     */
    @Override
    public boolean exists(LocalDate date) {
        return apodDBDAO.exists(date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }
}
