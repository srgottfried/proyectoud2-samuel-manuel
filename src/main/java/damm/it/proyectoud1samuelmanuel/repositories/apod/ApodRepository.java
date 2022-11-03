package damm.it.proyectoud1samuelmanuel.repositories.apod;

import damm.it.proyectoud1samuelmanuel.models.Apod;
import damm.it.proyectoud1samuelmanuel.repositories.CrudRepository;

import java.time.LocalDate;

public interface ApodRepository extends CrudRepository<Apod, LocalDate> {
}
