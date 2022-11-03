package damm.it.proyectoud2samuelmanuel.repositories.neos;

import damm.it.proyectoud2samuelmanuel.models.Neo;
import damm.it.proyectoud2samuelmanuel.models.NeoDay;
import damm.it.proyectoud2samuelmanuel.repositories.CrudRepository;

import java.io.File;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

public interface NeoDayRepository extends CrudRepository<NeoDay, LocalDate> {
    List<NeoDay> getBetween(LocalDate dateStart, LocalDate dateEnd, Predicate<Neo> predicate) throws NoSuchElementException;
    void saveExternalJson(NeoDay neoDay, File outFile);
    void saveExternalCsv(NeoDay neoDay, File outFile);
}
