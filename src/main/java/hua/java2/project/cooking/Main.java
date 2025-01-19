package hua.java2.project.cooking;

import javax.swing.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        Recipe r = new Recipe(null, null, null, null, 0);
        ShoppingList sl = new ShoppingList();

        Frame frame = new Frame();

        if(args.length == 1) {
//            System.out.println("No arguments provided.");

            String[] recipes = frame.getRecipes(frame).toArray(new String[0]);

            frame.printRecipeList(0, recipes);

        } else if(args.length == 2 && !args[1].equals("-list")) {
            System.out.println();

            frame.showSelectedRecipe(args[1]);

            //1η λειτουργια εκτυπωση πληροφοριων μιας συνταγης

//            r.printRecipeInfo(args[0]);

        } else if(args[1].equals("-list") && args.length > 2) {

            frame.printRecipeList(2, args);

            //2η λειτουργια εκτυπωση λιστας αγορας

//            sl.printShoppingList(args, args.length);
//            sl.printRecipeList(args, args.length);

        } else if(args[0].equals("-list")) {
            System.out.println("You should provide a recipe.");

        } else if(args[1].equals("-list")) {
            System.out.println("You should provide a recipe.");

        } else {
//            System.out.println("You should type '-list'.");

        }
    }


}