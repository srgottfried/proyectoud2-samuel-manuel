package damm.it.proyectoud1samuelmanuel.repositories;

import java.util.NoSuchElementException;

public interface CrudRepository<T, I> extends Repository<T, I> {
    T get(I id) throws NoSuchElementException;
    void add(T obj);
    void update(T obj);
    void remove(T obj);
    boolean exists(I id);
}
