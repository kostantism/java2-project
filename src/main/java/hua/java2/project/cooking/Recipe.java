package hua.java2.project.cooking;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Recipe implements Info {

    private String name;
    private List<Ingredient> ingredients;
    private List<Cookware> cookwares;
    private List<Step> steps;
    private int totalTime;

    public Recipe(String name, List<Ingredient> ingredients, List<Cookware> cookwares, List<Step> steps, int totalTime) {
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

    public void printInfo() {

    }

    //reads files
    public void readRecipe(File f) {
        try (FileReader reader = new FileReader(f)) {
            int data;

            while ((data = reader.read()) != -1) {
                System.out.print((char) data);
            }
        } catch (IOException e) {
            System.out.println("error");
        }
    }
}
