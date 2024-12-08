package hua.java2.project.cooking;

import java.io.File;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {

        //test object
        Recipe r = new Recipe(null, null, null, null, 0);
        ShoppingList sl = new ShoppingList(null);
        Time t = new Time(0, "");

        if(args.length == 0) {
            System.out.println("No arguments provided.");

        } else if(args.length == 1 && !args[0].equals("-list")) {
            System.out.println();

            //1η λειτουργια

            r.printRecipeInfo(args[0]);

        } else if(args[0].equals("-list") && args.length > 1) {
            System.out.println();

            //2η λειτουργια

            sl.printShoppingList(args, args.length);

            System.out.println(t.convert(80, "minutes"));

        } else if(args[0].equals("-list") && args.length == 1) {
            System.out.println("You should provide a recipe.");

        } else {
            System.out.println("You should type '-list'.");

        }

    }
}