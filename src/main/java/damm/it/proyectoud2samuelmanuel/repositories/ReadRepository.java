package damm.it.proyectoud2samuelmanuel.repositories;

import java.util.NoSuchElementException;

public interface ReadRepository<T, I> extends Repository {
    T get(I id) throws NoSuchElementException;
}
