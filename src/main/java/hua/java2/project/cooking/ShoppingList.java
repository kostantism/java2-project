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
        System.out.println("Λίστα αγορών:");
        System.out.println();
        for(Ingredient i : ingredients){
            System.out.println("Όνομα: " + i.getName() + " Ποσότητα: " + i.getQuantity() + " " + i.getMeasurmentUnit());
        }
        System.out.println();
    }

    //reads files
    public void readRecipe(String f) {
        File file = new File(f);

        try (FileReader reader = new FileReader(file)) {
            int data;
            String ingredient = "";
            int quantity = 0;
            String unitMeasurment = "";
            boolean readingIngredient = false;

            while ((data = reader.read()) != -1) {

                if((char) data == '@') {
                    readingIngredient = true;
                    ingredient = "";
                    quantity = 0;
                    unitMeasurment = "";

                } else if (readingIngredient) {
                    if ((char) data == '{') {

                        addIngredient(reader, (char) data, ingredient, quantity, unitMeasurment);

                        readingIngredient = false;

                    } else if ((char) data == ' ') {

                        try (FileReader secReader = new FileReader(file)) {

                            String tmpIngredient = "";

                            while ((data = secReader.read()) != -1) {
                                if ((char) data == '{') {
                                    ingredient += ' ' + tmpIngredient;

                                    addIngredient(reader, (char) data, ingredient, quantity, unitMeasurment);

                                    break;

                                } else if ((char) data == '#' || (char) data == '~' || (char) data == '@' || (char) data == '.' || (char) data == ',') {
                                    boolean found = false;

                                    for (Ingredient i : ingredients) {
                                        if (i.getName().equals(ingredient)) {
                                            i.setQuantity(i.getQuantity() + 1);
                                            found = true;
                                            break;
                                        }
                                    }

                                    if (!found) {
                                        ingredients.add(new Ingredient(ingredient, 1, ""));
                                        ingredient = "";
                                    }

                                    break;

                                } else {
                                    tmpIngredient += (char) data;
                                }

                            }

                            readingIngredient = false;

                        } catch (IOException e) {
                            System.out.println("error");
                        }

                    } else if ((char) data == '#' || (char) data == '~' || (char) data == '.' || (char) data == ',' ) {
                        boolean found = false;

                        for (Ingredient i : ingredients) {
                            if (i.getName().equals(ingredient)) {
                                i.setQuantity(i.getQuantity() + 1);
                                found = true;
                                break;
                            }
                        }

                        if (!found) {
                            ingredients.add(new Ingredient(ingredient, 1, ""));
                            ingredient = "";
                        }

                        readingIngredient = false;

                    } else {
                            ingredient += (char) data;
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("error");
        }
    }

    private void addIngredient(FileReader reader, int data, String ingredient, int quantity, String unitMeasurment) throws IOException {
        String tmpQuantity = "";
        String tmpUnitMeasurment = "";
        boolean readingUnitMeasurment = false;

        while ((data = reader.read()) != -1 && (char) data != '}') {
            if ((char) data == '%') {
                readingUnitMeasurment = true;
            } else if (readingUnitMeasurment) {
                tmpUnitMeasurment += (char) data;
            } else {
                tmpQuantity += (char) data;
            }
        }

        if(tmpQuantity.equals("")){
            tmpQuantity = "1";
        }
        if(tmpUnitMeasurment.equals("")){
            tmpUnitMeasurment = "";
        }

        quantity = Integer.parseInt(tmpQuantity);
        unitMeasurment = tmpUnitMeasurment;

        boolean found = false;

        for(Ingredient i : ingredients){
            if(i.getName().equals(ingredient)){
                i.setQuantity(i.getQuantity() + quantity);
                found = true;
                break;
            }
        }

        if(!found) {
            ingredients.add(new Ingredient(ingredient, quantity, unitMeasurment));
        }
    }

    public void readRecipes(String[] args, int argsLength) {
        int i;

        for(i = 1; i < argsLength; i++){
            readRecipe(args[i]);
        }
    }

    public void printShoppingList(String[] args, int argsLength) {
        readRecipes(args, args.length);

        printInfo();
    }

}

//import java.io.*;
//import java.util.*;
//
//public class ShoppingList {
//
//    private List<Ingredient> ingredients;
//
//    public ShoppingList() {
//        this.ingredients = new ArrayList<>();
//    }
//
//    public void printInfo() {
//        System.out.println("Λίστα αγορών:");
//        System.out.println();
//        for (Ingredient i : ingredients) {
//            System.out.println("Όνομα: " + i.getName() + " Ποσότητα: " + i.getQuantity() + " " + i.getMeasurmentUnit());
//        }
//        System.out.println();
//    }
//
//    public void readRecipe(String filePath) {
//        File file = new File(filePath);
//
//        try (FileReader reader = new FileReader(file)) {
//            int data;
//            StringBuilder buffer = new StringBuilder();
//            String ingredient = "";
//            int quantity = 0;
//            String unitMeasurment = "";
//            boolean readingIngredient = false;
//
//            while ((data = reader.read()) != -1) {
//                char currentChar = (char) data;
//
//                if (currentChar == '@') {
//                    readingIngredient = true;
//                    ingredient = "";
//                    quantity = 0;
//                    unitMeasurment = "";
//
//                } else if (readingIngredient) {
//                    if (currentChar == '{') {
//                        ingredient = buffer.toString().trim();
//                        buffer.setLength(0);
//                        String[] details = extractDetails(reader);
//                        quantity = Integer.parseInt(details[0]);
//                        unitMeasurment = details[1];
//                        addOrUpdateIngredient(ingredient, quantity, unitMeasurment);
//                        readingIngredient = false;
//
////                    } else if(currentChar == ' ') {
////
////                        boolean br = false;
////                        StringBuilder tmpBuffer = new StringBuilder();
////
////                        while ((data = reader.read()) != -1){
////                            currentChar = (char) data;
////
////                            if(currentChar == '{'){
////                                ingredient = buffer.toString();
////                                buffer.setLength(0);
////                                String[] details = extractDetails(reader);
////                                quantity = Integer.parseInt(details[0]);
////                                unitMeasurment = details[1];
////                                addOrUpdateIngredient(ingredient, quantity, unitMeasurment);
////
////                                break;
////
////                            } else if ( currentChar == '#' || currentChar == '~' || currentChar == '.' || currentChar == ',') {
////                                boolean found = false;
////
////                                for (Ingredient i : ingredients) {
////                                    if (i.getName().equals(ingredient)) {
////                                        i.setQuantity(i.getQuantity() + 1);
////                                        found = true;
////                                        break;
////                                    }
////                                }
////
////                                if (!found) {
////                                    ingredients.add(new Ingredient(ingredient, 1, ""));
////                                    ingredient = "";
////                                }
////
////                                br = true;
////                                break;
////
////
////                            } else {
////                                tmpBuffer.append(currentChar);
////                            }
////                        }
////
////                        if (br) {
////                            break;
////                        } else {
////                            buffer.append(tmpBuffer);
////
////                            readingIngredient = false;
////                        }
//
//                    } else {
//                        buffer.append(currentChar);
//                    }
//                }
//            }
//
//        } catch (IOException e) {
//            System.out.println("Σφάλμα κατά την ανάγνωση του αρχείου: " + e.getMessage());
//        }
//    }
//
//    private String[] extractDetails(FileReader reader) throws IOException {
//        StringBuilder quantityBuffer = new StringBuilder();
//        StringBuilder unitBuffer = new StringBuilder();
//        boolean readingUnit = false;
//        int data;
//
//        while ((data = reader.read()) != -1 && (char) data != '}') {
//            char currentChar = (char) data;
//
//            if (currentChar == '%') {
//                readingUnit = true;
//            } else if (readingUnit) {
//                unitBuffer.append(currentChar);
//            } else {
//                quantityBuffer.append(currentChar);
//            }
//        }
//
//        return new String[]{quantityBuffer.toString(), unitBuffer.toString()};
//    }
//
//    private void addOrUpdateIngredient(String name, int quantity, String unit) {
//        for (Ingredient i : ingredients) {
//            if (i.getName().equals(name)) {
//                i.setQuantity(i.getQuantity() + quantity);
//                return;
//            }
//        }
//        ingredients.add(new Ingredient(name, quantity, unit));
//    }
//
//    public void readRecipes(String[] args, int argsLength) {
//
//        for (int i = 1; i < argsLength; i++) {
//            readRecipe(args[i]);
//        }
//    }
//
//    public void printShoppingList(String[] args, int argsLength) {
//
//        readRecipes(args , argsLength);
//
//        printInfo();
//    }
//}
