package hua.java2.project.cooking;

import javax.swing.*;
import java.util.ArrayList;

import gr.hua.dit.oop2.countdown.Countdown;
import gr.hua.dit.oop2.countdown.CountdownFactory;
import gr.hua.dit.oop2.countdown.Notifier;

public class Main {
//    public static void main(String[] args) {
//
//        Recipe r = new Recipe(null, null, null, null, 0);
//        ShoppingList sl = new ShoppingList();
//
//        Frame frame = new Frame();
//
//        if(args.length == 1) {//////////////htan 0
//            System.out.println("No arguments provided.");
//
//            String[] recipes = frame.getRecipes(frame).toArray(new String[0]);
//
//            frame.printRecipeList(0, recipes);
//
////            for (String recipe : recipes) {
////                System.out.println("Όνομα: " + recipe);
////                System.out.println();
////            }
//
//        } else if(args.length == 2 && !args[1].equals("-list")) {///////////htan 1 kai 0
//            System.out.println();
//
//            frame.showSelectedRecipe(args[1]);//////////htan 0
//
//            //1η λειτουργια εκτυπωση πληροφοριων μιας συνταγης
//
////            r.printRecipeInfo(args[0]);
//
//        } else if(args[1].equals("-list") && args.length > 1) { ///////////////htan 0
//            System.out.println();
//
//            frame.printRecipeList(2, args);/////////htan 1
//
//            //2η λειτουργια εκτυπωση λιστας αγορας
//
////            sl.printShoppingList(args, args.length);
////            sl.printRecipeList(args, args.length);
//
//        } else if(args[0].equals("-list")) {
//            System.out.println("You should provide a recipe.");
//
//        } else {
//            System.out.println("You should type '-list'.");
//
//        }
//    }

    public static void main(String[] args) {
//        System.out.println("-------------------------------------------------------------------------------------------");
//        System.out.println(System.getProperty("java.class.path"));
        // Δημιουργία αντίστροφης μέτρησης για 10 δευτερόλεπτα
        Countdown countdown = CountdownFactory.countdown("MyCountdown", 10);

        System.out.println("apomenoun: " + countdown.secondsRemaining());

        // Προσθήκη ειδοποίησης όταν τελειώσει η αντίστροφη μέτρηση
        countdown.addNotifier(new Notifier() {
            @Override
            public void finished(Countdown c) {
                System.out.println("Η αντίστροφη μέτρηση " + c.getName() + " ολοκληρώθηκε!");
            }
        });

        // Εκκίνηση αντίστροφης μέτρησης
        countdown.start();

//        System.out.println("apomenoun: " + countdown.secondsRemaining());

//         Εμφάνιση των υπολοίπων δευτερολέπτων κάθε δευτερόλεπτο
        while (countdown.secondsRemaining() > 0) {
            System.out.println("Δευτερόλεπτα που απομένουν: " + countdown.secondsRemaining());
            try {
                Thread.sleep(1000); // Αναμονή 1 δευτερολέπτου
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("error");
            }
//            System.out.println("apomenoun: " + countdown.secondsRemaining());
        }

        // Διακοπή αντίστροφης μέτρησης
        countdown.stop();
    }


}