package damm.it.proyectoud2samuelmanuel.models;

import java.io.Serial;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class NeoDay implements Serializable {
    @Serial
    private static final long serialVersionUID = 1;

    private List<Neo> neos;
    private String date;

    public NeoDay(String date) {
        neos = new LinkedList<>();
        this.date = date;
    }

    public NeoDay(String date, List neos) {
        this.neos = neos;
        this.date = date;
    }

    public List<Neo> getNeos() {
        return neos;
    }

    public String getDate() {
        return date;
    }

    public void setNeos(List<Neo> neos) {
        this.neos = neos;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
