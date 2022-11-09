package damm.it.proyectoud2samuelmanuel.daos;

import java.util.NoSuchElementException;

public interface DAO<T, I> {
    T get(I id) throws NoSuchElementException;
    void add(T obj);
    void update(T obj) throws NoSuchElementException;
    void remove(T obj) throws NoSuchElementException;
    boolean exists(I id);
}
