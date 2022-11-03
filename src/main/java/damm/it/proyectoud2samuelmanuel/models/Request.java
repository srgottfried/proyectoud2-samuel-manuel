package damm.it.proyectoud2samuelmanuel.models;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Clase para modelar consultas realizadas.
 */
public class Request implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String user;
    private String opName;
    private String name;
    private String opDiameter;
    private String diameter;
    private String opMaxAprox;
    private String maxAprox;
    private String opSpeed;
    private String speed;
    private boolean onlyPD;
    private LocalDate startDate;
    private LocalDate endDate;

    public Request() {}

    public Request(String user, String opName, String name, String opDiameter, String diameter, String opMaxAprox, String maxAprox, String opSpeed, String speed, boolean onlyPD, LocalDate startDate, LocalDate endDate) {
        this.user = user;
        this.opName = opName;
        this.name = name;
        this.opDiameter = opDiameter;
        this.diameter = diameter;
        this.opMaxAprox = opMaxAprox;
        this.maxAprox = maxAprox;
        this.opSpeed = opSpeed;
        this.speed = speed;
        this.onlyPD = onlyPD;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getUser() {
        return user;
    }

    public String getOpName() {
        return opName;
    }

    public String getName() {
        return name;
    }

    public String getOpDiameter() {
        return opDiameter;
    }

    public String getDiameter() {
        return diameter;
    }

    public String getOpMaxAprox() {
        return opMaxAprox;
    }

    public String getMaxAprox() {
        return maxAprox;
    }

    public String getOpSpeed() {
        return opSpeed;
    }

    public String getSpeed() {
        return speed;
    }

    public boolean isOnlyPD() {
        return onlyPD;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}
