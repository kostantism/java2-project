package hua.java2.project.cooking;

import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class ShoppingList implements Info {

    private List<Ingredient> ingredients;

    public ShoppingList(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public void printInfo(){
        System.out.println("Shopping List:");
        for(Ingredient i : ingredients){
            System.out.println("Name: " + i.getName() + " Quantity: " + i.getQuantity() + " " + i.getMeasurmentUnit());
        }
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

    public void readRecipes(String[] args, int argsLength) {

    }

}
