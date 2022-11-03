package damm.it.proyectoud2samuelmanuel.daos.requests;

import damm.it.proyectoud2samuelmanuel.models.Request;

public interface RequestDAO extends damm.it.proyectoud2samuelmanuel.daos.DAO<Request> {
    void create(Request request);
    Request read(String name);
    void update(Request request);
    void delete(String name);
    boolean exists(String name);
}
