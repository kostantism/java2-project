package hua.java2.project.cooking;

import java.util.Objects;

public class Main {
    public static void main(String[] args) {

        if(args.length == 0) {
            System.out.println("No arguments provided.");

        } else if(args.length == 1 && !args[0].equals("-list")) {
            System.out.println("Print recipe.");

            //1η λειτουργια

        } else if(args[0].equals("-list") && args.length > 1) {
            System.out.println("Print shopping list.");

            //2η λειτουργια

        } else if(args[0].equals("-list") && args.length == 1) {
            System.out.println("You should provide a recipe.");

        } else {
            System.out.println("You should type '-list'.");
            System.out.println("Doulevei re bro?");

        }

        //hgfjfjh
    }
}