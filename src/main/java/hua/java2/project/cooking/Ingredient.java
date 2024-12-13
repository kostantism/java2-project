package hua.java2.project.cooking;
//
//public class Ingredient {
//
//    private String name;
//    private int quantity;
//    private String measurmentUnit;
//
//    public Ingredient(String name, int quantity, String measurmentUnit) {
//        this.name = name;
//        this.quantity = quantity;
//        this.measurmentUnit = measurmentUnit;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public int getQuantity() {
//        return quantity;
//    }
//
//    public void setQuantity(int quantity) {
//        this.quantity = quantity;
//    }
//
//    public String getMeasurmentUnit() {
//        return measurmentUnit;
//    }
//
//    public void setMeasurmentUnit(String measurmentUnit) {
//        this.measurmentUnit = measurmentUnit;
//    }
//}


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

//    public void updateMeasurement(String unit, int quantity) {
//        if (this.measurements.containsKey(unit)) {
//            this.measurements.put(unit, quantity);
//        } else {
//            System.out.println("Measurement unit not found.");
//        }
//    }
//
//    public Integer getQuantityForUnit(String unit) {
//        return this.measurements.getOrDefault(unit, null);
//    }
//
//    public void removeMeasurement(String unit) {
//        this.measurements.remove(unit);
//    }
//
//    @Override
//    public String toString() {
//        return "Ingredient{" +
//                "name='" + name + '\'' +
//                ", measurements=" + measurements +
//                '}';
//    }
}

