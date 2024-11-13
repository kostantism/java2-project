package hua.java2.project.cooking;

public class Main {
    public static void main(String[] args) {

        if(args.length == 0) {
            System.out.println("No arguments provided.");

        } else if(args.length == 1) {
            System.out.println("Print recipe.");

        } else if(args.length >= 2) {
            System.out.println("Print shopping list.");

        }

    }
}