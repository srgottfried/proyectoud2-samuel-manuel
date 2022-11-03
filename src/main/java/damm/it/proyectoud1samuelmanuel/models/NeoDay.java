package damm.it.proyectoud1samuelmanuel.models;

import java.io.Serial;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class NeoDay implements Serializable {
    @Serial
    private static final long serialVersionUID = 1;

    private final List<Neo> neos;
    private final String date;

    public NeoDay(String date) {
        neos = new LinkedList<>();
        this.date = date;
    }

    public List<Neo> getNeos() {
        return neos;
    }

    public String getDate() {
        return date;
    }
}
