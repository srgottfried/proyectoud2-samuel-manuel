package damm.it.proyectoud2samuelmanuel.repositories.apod;

import damm.it.proyectoud2samuelmanuel.daos.CrudDAO;
import damm.it.proyectoud2samuelmanuel.daos.apod.ApodApiDAO;
import damm.it.proyectoud2samuelmanuel.daos.apod.ApodCacheDAO;
import damm.it.proyectoud2samuelmanuel.models.Apod;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;

/**
 * Repositorio para las imágenes del dia.
 */
public class ApodRepositoryImpl implements ApodRepository {
    private final ApodApiDAO apiDao;
    private final ApodCacheDAO cacheDao;

    /**
     * Constructor del repositorio.
     */
    public ApodRepositoryImpl() {

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
        Apod apod;

        String dateStr = date == null ? LocalDate.now(ZoneOffset.UTC).toString() : date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        if ((apod = cacheDao.read(dateStr)) != null)
            return apod;

        if ((apod = apiDao.read(date == null ? "" : dateStr)) != null) {
            cacheDao.create(apod);
            return apod;
        }

        throw new NoSuchElementException("No se ha podido encontrar la imagen del dia.");
    }

    /**
     * Método para almacenar una imagen del dia.
     *
     * @param apod Imagen del dia a almacenar
     */
    @Override
    public void add(Apod apod) {
        cacheDao.create(apod);
    }

    /**
     * Método para actualizar una imagen del dia.
     *
     * @param apod Imagen del dia a actualizar
     */
    @Override
    public void update(Apod apod) {
        cacheDao.update(apod);
    }

    /**
     * Método para eliminar una imagen del dia.
     *
     * @param apod Imagen del dia a eliminar
     */
    @Override
    public void remove(Apod apod) {
        cacheDao.delete(apod.getDate());
    }

    /**
     * Método para comprobar si existen datos de un Apod.
     *
     * @param date Dia del que se quiere comprobar si existen datos
     * @return True si existen datos de ese dia, false en caso contrario
     */
    @Override
    public boolean exists(LocalDate date) {
        return cacheDao.exists(date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }
}
