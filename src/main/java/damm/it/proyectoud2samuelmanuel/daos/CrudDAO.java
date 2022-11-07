package damm.it.proyectoud2samuelmanuel.daos;

import java.util.List;

public interface CrudDAO<T> extends DAO {

    void create(T t);

    List<T> read();

    void update(T t);

    void delete(T t);

    boolean exists(T t);
}
