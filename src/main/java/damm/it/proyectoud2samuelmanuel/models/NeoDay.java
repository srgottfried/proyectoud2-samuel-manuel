package damm.it.proyectoud2samuelmanuel.models;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class NeoDay {
    private List<Neo> neos;
    private LocalDate date;

    public NeoDay(LocalDate date) {
        this.date = date;
        this.neos = new ArrayList<>();
    }

    public NeoDay(LocalDate date, List<Neo> neos) {
        this.date = date;
        this.neos = neos;
    }

    public List<Neo> getNeos() {
        return neos;
    }
    public void setNeos(List<Neo> neos) {
        this.neos = neos;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
