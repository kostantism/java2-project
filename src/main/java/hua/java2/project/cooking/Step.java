package hua.java2.project.cooking;

import java.util.ArrayList;
import java.util.Map;

public class Step {

    private String description;
    private float stepTime;
    private String timeUnit;

    private Map<String, Map<String, Float>> ingredients;
    private ArrayList<Cookware> cookwares;

    public Step(String description, float stepTime, String timeUnit,
                Map<String, Map<String, Float>> ingredients, ArrayList<Cookware> cookwares) {
        this.description = description;
        this.stepTime = stepTime;
        this.timeUnit = timeUnit;

        this.ingredients = ingredients;
        this.cookwares = cookwares;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String step) {
        this.description = description;
    }

    public float getStepTime() {
        return stepTime;
    }

    public void setStepTime(float stepTime) {
        this.stepTime = stepTime;
    }

    public String getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(String timeUnit) {
        this.timeUnit = timeUnit;
    }

    public Map<String, Map<String, Float>> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Map<String, Map<String, Float>> ingredients) {
        this.ingredients = ingredients;
    }

    public ArrayList<Cookware> getCookwares() {
        return cookwares;
    }

    public void setCookwares(ArrayList<Cookware> cookwares) {
        this.cookwares = cookwares;
    }
}
