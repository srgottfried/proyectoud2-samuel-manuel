package damm.it.proyectoud2samuelmanuel.models;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

public class Neo {
    private int id;
    private String name;
    private double diameter;
    private double minDistance;
    private double speed;
    private boolean hazardous;
    private LocalDate date;

    public Neo(String name, double diameter, double minDistance, double speed, boolean hazardous, LocalDate date) {
        this.name = name;
        this.diameter = diameter;
        this.minDistance = minDistance;
        this.speed = speed;
        this.hazardous = hazardous;
        this.date = date;
    }

    public Neo(int id, String name, double diameter, double minDistance, double speed, boolean hazardous, LocalDate date) {
        this.id = id;
        this.name = name;
        this.diameter = diameter;
        this.minDistance = minDistance;
        this.speed = speed;
        this.hazardous = hazardous;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDiameter() {
        return diameter;
    }

    public void setDiameter(double diameter) {
        this.diameter = diameter;
    }

    public double getMinDistance() {
        return minDistance;
    }

    public void setMinDistance(double minDistance) {
        this.minDistance = minDistance;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public boolean isHazardous() {
        return hazardous;
    }

    public void setHazardous(boolean hazardous) {
        this.hazardous = hazardous;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
