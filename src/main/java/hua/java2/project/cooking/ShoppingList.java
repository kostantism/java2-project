package hua.java2.project.cooking;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ShoppingList implements Info {

    MeasurementUnit ms = new MeasurementUnit("");

    private Map<String, Map<String, Float>> ingredients;

    public ShoppingList() {
        this.ingredients = new HashMap<>();
    }

    public void printInfo(int numOfPeople) {
        System.out.println("Λίστα αγορών:");
        System.out.println();
        for (Map.Entry<String, Map<String, Float>> ingredientEntry : ingredients.entrySet()) {
            String ingredientName = ingredientEntry.getKey();
            System.out.println("Όνομα: " + ingredientName);
            System.out.println("Ποσότητα:");
            for (Map.Entry<String, Float> measurementEntry : ingredientEntry.getValue().entrySet()) {
                if(measurementEntry.getKey().equals("gr") || measurementEntry.getKey().equals("kg") ||
                        measurementEntry.getKey().equals("ml") || measurementEntry.getKey().equals("l")) {
                        if(measurementEntry.getValue() != 0) {
                            System.out.println(" - " + ms.convert(measurementEntry.getValue() * numOfPeople, measurementEntry.getKey()));
                        }
                } else {
                    float quantity = measurementEntry.getValue();
                    if(quantity != 0) {
                        if (quantity % 1 == 0) {
                            System.out.println("  - " + (int) quantity * numOfPeople + " " + measurementEntry.getKey());
                        } else {
                            System.out.println("  - " + quantity * numOfPeople + " " + measurementEntry.getKey());
                        }
                    }
                }
            }
            System.out.println();
        }
    }

    //διαβαζει το αρχειο και αποθηκευει τα υλικα με τις ποσοτητες τους
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

                                addOrUpdateIngredient(ingredients, ingredient, "", quantity);

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
                        addOrUpdateIngredient(ingredients, ingredient, "", quantity);

                        readingIngredient = false;

                    } else {
                            ingredient += (char) data;
                    }
                }
            }

            if(!ingredient.isEmpty()) {
                addOrUpdateIngredient(ingredients, ingredient, "", quantity);
            }

        } catch (IOException e) {
            System.out.println("Εrror reading the file!\nTry again.");
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

        if(tmpQuantity.isEmpty()){
            tmpQuantity = "1";
        }
        if(tmpUnitMeasurment.isEmpty()){
            tmpUnitMeasurment = "";
        }

        quantity = Float.parseFloat(tmpQuantity);
        unitMeasurment = tmpUnitMeasurment;

        addOrUpdateIngredient(ingredients, ingredient, unitMeasurment, quantity);
    }

    //διαβαζει οσες συνταγες δωσει ο χρηστης
    public void readRecipes(String[] args, int argsLength) {
        int i;

        for(i = 1; i < argsLength; i++){
            readRecipe(args[i]);
        }
    }

    //εκτυπωνει την λιστα αγορων
    public void printShoppingList(String[] args, int argsLength) {
        int numOfPeople = Recipe.numOfPeople();

        readRecipes(args, args.length);

        printInfo(numOfPeople);
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
