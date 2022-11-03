package damm.it.proyectoud1samuelmanuel.repositories.requests;

import damm.it.proyectoud1samuelmanuel.daos.requests.RequestCacheDAO;
import damm.it.proyectoud1samuelmanuel.daos.requests.RequestDAO;
import damm.it.proyectoud1samuelmanuel.models.Request;

import java.util.NoSuchElementException;

/**
 * Repositorio para las peticiones.
 */
public class RequestRepositoryImpl implements RequestRepository {
    private final RequestDAO requestDAO;

    /**
     * Constructor del repositorio.
     */
    public RequestRepositoryImpl() {
        requestDAO = new RequestCacheDAO();
    }

    /**
     * Método para obtener los datos de la última petición de un usuario.
     *
     * @param name Nombre del usuario del que obtener la última petición
     * @return La última petición realizada por el usuario
     * @throws NoSuchElementException Si no se pueden obtener la petición
     */
    @Override
    public Request get(String name) throws NoSuchElementException {
        Request request;
        if ((request = requestDAO.read(name)) != null)
            return request;

        throw new NoSuchElementException("No se ha podido obtener los datos de la Request.");
    }

    /**
     * Método para almacenar la última petición de un usuario.
     *
     * @param request La petición a almacenar
     */
    @Override
    public void add(Request request) {
        requestDAO.create(request);
    }

    /**
     * Método para actualizar la última petición de un usuario.
     *
     * @param request La petición a actualizar
     */
    @Override
    public void update(Request request) {
        requestDAO.update(request);
    }

    /**
     * Método para eliminar la última petición de un usuario.
     *
     * @param request La petición a eliminar
     */
    @Override
    public void remove(Request request) {
        requestDAO.delete(request.getUser());
    }

    /**
     * Método para comprobar si un usuario tiene una última petición.
     * @param name Nombre del usuario a comprobar
     * @return True en caso exista una última petición y false en caso contrario
     */
    @Override
    public boolean exists(String name) {
        return requestDAO.exists(name);
    }
}
