package hua.java2.project.cooking;

public class Ingredient {

    private String name;
    private int quantity;
    private String measurmentUnit;

    public Ingredient(String name, int quantity, String measurmentUnit) {
        this.name = name;
        this.quantity = quantity;
        this.measurmentUnit = measurmentUnit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getMeasurmentUnit() {
        return measurmentUnit;
    }

    public void setMeasurmentUnit(String measurmentUnit) {
        this.measurmentUnit = measurmentUnit;
    }
}
