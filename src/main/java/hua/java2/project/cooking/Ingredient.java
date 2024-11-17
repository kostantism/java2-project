package hua.java2.project.cooking;

public class Ingredient {

    private String name;
    private String quantity;
    private String measurmentUnit;

    public Ingredient(String name, String quantity, String measurmentUnit) {
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

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getMeasurmentUnit() {
        return measurmentUnit;
    }

    public void setMeasurmentUnit(String measurmentUnit) {
        this.measurmentUnit = measurmentUnit;
    }
}
