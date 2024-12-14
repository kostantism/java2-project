package hua.java2.project.cooking;

import java.util.HashMap;

public class Ingredient {

    private String name;
    private HashMap<String, Float> measurements;

    public Ingredient(String name) {
        this.name = name;
        this.measurements = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<String, Float> getMeasurements() {
        return measurements;
    }

    public void addMeasurement(String unit, float quantity) {
        this.measurements.put(unit, quantity);
    }
}
