package hua.java2.project.cooking;

import java.awt.*;
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
}
