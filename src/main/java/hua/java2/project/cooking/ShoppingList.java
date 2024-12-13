package hua.java2.project.cooking;

import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingList implements Info {

    MeasurementUnit ms = new MeasurementUnit("");

    //private List<Ingredient> ingredients;
    private Map<String, Map<String, Float>> ingredients;


//    public ShoppingList(List<Ingredient> ingredients) {
//        this.ingredients = new ArrayList<>();
//    }

    public ShoppingList() {
        this.ingredients = new HashMap<>();
    }

//    public void printInfo(){
//        System.out.println("Λίστα αγορών:");
//        System.out.println();
//        for(Ingredient i : ingredients){
//            System.out.println("Όνομα: " + i.getName() + " Ποσότητα: " + i.getQuantity() + " " + i.getMeasurmentUnit());
//        }
//        System.out.println();
//    }

    public void printInfo() {
        System.out.println("Λίστα αγορών:");
        System.out.println();
        for (Map.Entry<String, Map<String, Float>> ingredientEntry : ingredients.entrySet()) {
            String ingredientName = ingredientEntry.getKey();
            System.out.println("Όνομα: " + ingredientName);
            System.out.println("Ποσότητα:");
            for (Map.Entry<String, Float> measurementEntry : ingredientEntry.getValue().entrySet()) {
                if(measurementEntry.getKey().equals("gr") || measurementEntry.getKey().equals("kg") ||
                        measurementEntry.getKey().equals("ml") || measurementEntry.getKey().equals("l")) {
                        System.out.println(" - " + ms.convert(measurementEntry.getValue(), measurementEntry.getKey()));
                } else {
                    float quantity = measurementEntry.getValue();
                    if(quantity % 1 == 0) {
                        System.out.println("  - " + (int) quantity + " " + measurementEntry.getKey());
                    } else {
                        System.out.println("  - " + quantity + " " + measurementEntry.getKey());
                    }
                }
            }
            System.out.println();
        }
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

                        String tmpIngredient = "";

                        while ((data = reader.read()) != -1) {

                            if ((char) data == '{') {
                                ingredient += ' ' + tmpIngredient;

                                addIngredient(reader, (char) data, ingredient, quantity, unitMeasurment);

                                readingIngredient = false;

                                break;

                            } else if ((char) data == '#' || (char) data == '~' || (char) data == '@' || (char) data == '.' || (char) data == ',') {
//                                boolean found = false;

//                                for (Ingredient i : ingredients) {
//                                    if (i.getName().equals(ingredient)) {
//                                        i.setQuantity(i.getQuantity() + 1);
//                                        found = true;
//                                        break;
//                                    }
//                                }
//
//                                if (!found) {
//                                    ingredients.add(new Ingredient(ingredient, 1, ""));
//                                    ingredient = "";
//                                }

                                addOrUpdateIngredient(ingredients, ingredient, "", 1);

                                if((char) data == '@'){
                                    ingredient = "";
                                    tmpIngredient = "";
                                } else {
                                    readingIngredient = false;
                                }

                                break;

                            } else {
                                tmpIngredient += (char) data;
                            }

                        }

                    } else if ((char) data == '#' || (char) data == '~' || (char) data == '.' || (char) data == ',' ) {
//                        boolean found = false;

//                        for (Ingredient i : ingredients) {
//                            if (i.getName().equals(ingredient)) {
//                                i.setQuantity(i.getQuantity() + 1);
//                                found = true;
//                                break;
//                            }
//                        }
//
//                        if (!found) {
//                            ingredients.add(new Ingredient(ingredient, 1, ""));
//                            ingredient = "";
//                        }

                        addOrUpdateIngredient(ingredients, ingredient, "", 1);

                        readingIngredient = false;

                    } else {
                            ingredient += (char) data;
                    }
                }
            }
            if(!ingredient.equals("")) {
//                boolean found = false;

//                for (Ingredient i : ingredients) {
//                    if (i.getName().equals(ingredient)) {
//                        i.setQuantity(i.getQuantity() + 1);
//                        found = true;
//                        break;
//                    }
//                }
//
//                if (!found) {
//                    ingredients.add(new Ingredient(ingredient, 1, ""));
//                    ingredient = "";
//                }

                addOrUpdateIngredient(ingredients, ingredient, "", 1);
            }

        } catch (IOException e) {
            System.out.println("error");
        }
    }

    private void addIngredient(FileReader reader, int data, String ingredient, float quantity, String unitMeasurment) throws IOException {
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

        quantity = Float.parseFloat(tmpQuantity);
        unitMeasurment = tmpUnitMeasurment;

//        boolean found = false;
//
//        for(Ingredient i : ingredients){
//            if(i.getName().equals(ingredient)){
//                i.setQuantity(i.getQuantity() + quantity);
//                found = true;
//                break;
//            }
//        }
//
//        if(!found) {
//            ingredients.add(new Ingredient(ingredient, quantity, unitMeasurment));
//        }

        addOrUpdateIngredient(ingredients, ingredient, unitMeasurment, quantity);
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


    public static void addOrUpdateIngredient(Map<String, Map<String, Float>> ingredients,
                                             String ingredient, String unit, float quantity) {

        if(unit.equals("gr") || unit.equals("kg")){
            quantity = MeasurementUnit.addGr(quantity, unit);
            unit = "gr";
        } else if(unit.equals("ml") || unit.equals("l")) {
            quantity = MeasurementUnit.addMl(quantity, unit);
            unit = "ml";
        }

        // Check if the ingredient already exists in the outer map
        if (ingredients.containsKey(ingredient)) {
            // If the ingredient exists, get the inner map of unit measurements
            Map<String, Float> units = ingredients.get(ingredient);

            // Check if the unit exists in the inner map
            if (units.containsKey(unit)) {
                // If the unit exists, update its quantity
                units.put(unit, units.get(unit) + quantity);
            } else {
                // If the unit doesn't exist, add it with the new quantity
                units.put(unit, quantity);
            }
        } else {
            // If the ingredient doesn't exist, create a new entry with the unit and quantity
            Map<String, Float> newUnits = new HashMap<>();
            newUnits.put(unit, quantity);
            ingredients.put(ingredient, newUnits);
        }
    }

}
