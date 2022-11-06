package damm.it.proyectoud2samuelmanuel.daos;

import java.util.List;

public interface DBDAO<T> extends DAO {

    void create(T t);

    List<T> read();

    void update(T t);

    void update(int i, T t);

    void delete(T t);

    void delete(int i);
}
