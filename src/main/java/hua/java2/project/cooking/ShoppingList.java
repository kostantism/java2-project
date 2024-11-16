package hua.java2.project.cooking;

import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ShoppingList implements Info {

    private List<Ingredient> ingredients;

    public ShoppingList(List<Ingredient> ingredients) {
        this.ingredients = new ArrayList<>();
    }

    public void printInfo(){
        System.out.println("Shopping List:");
        for(Ingredient i : ingredients){
            System.out.println("Name: " + i.getName() + " Quantity: " + i.getQuantity() + " " + i.getMeasurmentUnit());
        }
    }

    //reads files
    public void readRecipe(String f) {
        File file = new File(f);

        try (FileReader reader = new FileReader(file)) {
            int data;

            while ((data = reader.read()) != -1) {

                if((char) data == '@') {
                    String ingredient = "";
                    while ((data = reader.read()) != -1 && (char) data != '{' && (char) data != ' ') {
                        ingredient += (char) data;
                    }

                    ingredients.add(new Ingredient(ingredient, 0, "k"));
                }
            }

        } catch (IOException e) {
            System.out.println("error");
        }
    }

    public void readRecipes(String[] args, int argsLength) {
        int i;

        for(i = 1; i < argsLength; i++){
            readRecipe(args[i]);
        }

        printInfo();
    }

}
