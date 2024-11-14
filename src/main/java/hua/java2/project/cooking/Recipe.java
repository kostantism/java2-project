package hua.java2.project.cooking;

import java.util.ArrayList;
import java.util.List;

public class Recipe {

    private String name;
    private List<Ingredient> ingredients;
    private List<Cookware> cookwares;
    private List<Step> steps;
    private int totalTime;

    public Recipe(String name, Ingredient[] ingredients, Cookware[] cookwares, Step[] steps, int totalTime) {
        this.name = name;
        this.ingredients = new ArrayList<>();
        this.cookwares = new ArrayList<>();
        this.steps = new ArrayList<>();
        this.totalTime = totalTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }
}
