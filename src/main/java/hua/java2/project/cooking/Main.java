package hua.java2.project.cooking;

import java.io.File;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {

        //test object
        Recipe r = new Recipe(null, null, null, null, 0);
        ShoppingList sl = new ShoppingList(null);

        if(args.length == 0) {
            System.out.println("No arguments provided.");

        } else if(args.length == 1 && !args[0].equals("-list")) {
            System.out.println("Print recipe.");

            //1η λειτουργια

            System.out.println(args[0]);
            r.readRecipe(args[0]);

        } else if(args[0].equals("-list") && args.length > 1) {
            System.out.println();

            //2η λειτουργια

            sl.readRecipes(args, args.length);

        } else if(args[0].equals("-list") && args.length == 1) {
            System.out.println("You should provide a recipe.");

        } else {
            System.out.println("You should type '-list'.");

        }

    }
}