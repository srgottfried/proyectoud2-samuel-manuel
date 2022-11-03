package damm.it.proyectoud1samuelmanuel.models;

import java.io.Serial;
import java.io.Serializable;

public class Neo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1;

    private String name;
    private double diameter;
    private double minDistance;
    private double speed;
    private boolean hazardous;

    public Neo(String name, double diameter, double minDistance, double speed, boolean hazardous) {
        this.name = name;
        this.diameter = diameter;
        this.minDistance = minDistance;
        this.speed = speed;
        this.hazardous = hazardous;
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
}
