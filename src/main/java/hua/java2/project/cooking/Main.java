package hua.java2.project.cooking;

import javax.swing.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        Recipe r = new Recipe(null, null, null, null, 0);
        ShoppingList sl = new ShoppingList();

        Frame frame = new Frame();

        if(args.length == 0) {
            System.out.println("No arguments provided.");

            String[] recipes = frame.getRecipes(frame).toArray(new String[0]);

            frame.printRecipeList(recipes);

//            for (String recipe : recipes) {
//                System.out.println("Όνομα: " + recipe);
//                System.out.println();
//            }

        } else if(args.length == 1 && !args[0].equals("-list")) {
            System.out.println();

            //1η λειτουργια εκτυπωση πληροφοριων μιας συνταγης

//            r.printRecipeInfo(args[0]);

        } else if(args[0].equals("-list") && args.length > 1) {
            System.out.println();

            frame.printRecipeList(args);

            //2η λειτουργια εκτυπωση λιστας αγορας

//            sl.printShoppingList(args, args.length);
//            sl.printRecipeList(args, args.length);

        } else if(args[0].equals("-list")) {
            System.out.println("You should provide a recipe.");

        } else {
            System.out.println("You should type '-list'.");

        }
    }
}