package damm.it.proyectoud2samuelmanuel.repositories.apod;

import damm.it.proyectoud2samuelmanuel.models.Apod;
import damm.it.proyectoud2samuelmanuel.repositories.CrudRepository;

import java.time.LocalDate;

public interface ApodRepository extends CrudRepository<Apod, LocalDate> {
}
