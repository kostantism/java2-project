package hua.java2.project.cooking;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Recipe implements Info {

    Time t = new Time(0, "");
    ShoppingList sl = new ShoppingList();
    MeasurementUnit ms = new MeasurementUnit("");

    private String name;
    private Map<String, Map<String, Float>> ingredients;
    private ArrayList<Cookware> cookwares;
    private ArrayList<Step> steps;
    private float totalTime;

    public Recipe(String name, ArrayList<Ingredient> ingredients, ArrayList<Cookware> cookwares, ArrayList<Step> steps, float totalTime) {
        this.name = name;
        this.ingredients = new HashMap<>();
        this.cookwares = new ArrayList<>();
        this.steps = new ArrayList<>();
        this.totalTime = totalTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(float totalTime) {
        this.totalTime = totalTime;
    }

    //reads files
    public void readRecipe(String f) {
        readStep(f);
    }

    public void printInfo(int numOfPeople){
        System.out.println("\nYλικά συνταγής: \n");

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

        System.out.println("\nΣκεύοι συνταγής: \n");

        for(Cookware ckwrs : cookwares){
            System.out.println(ckwrs.getName());
        }

        System.out.println("\nΣυνολικός Χρόνος συνταγής: \n");

        System.out.println(t.convert(totalTime, "minutes"));

        System.out.println("\nΑναλυτικά τα βήματα της συνταγής: \n");

        int counter = 1;
        for (Step stps : steps) {
            // Print the basic details for each step
            System.out.println(counter + ". " + stps.getDescription());
            System.out.println();

            if(stps.getStepTime() != 0) {
                System.out.println("Χρόνος βήματος: " + t.convert(stps.getStepTime(), "minutes"));
            }

            System.out.println("\nΥλικά βήματος:\n");

            for (Map.Entry<String, Map<String, Float>> ingredientEntry : stps.getIngredients().entrySet()) {
                String ingredientName = ingredientEntry.getKey();
                System.out.println("Όνομα: " + ingredientName);
                System.out.println("Ποσότητα:");
                for (Map.Entry<String, Float> measurementEntry : ingredientEntry.getValue().entrySet()) {
                    if(measurementEntry.getKey().equals("gr") || measurementEntry.getKey().equals("kg") ||
                            measurementEntry.getKey().equals("ml") || measurementEntry.getKey().equals("l")) {
                        System.out.println(" - " + ms.convert(measurementEntry.getValue() * numOfPeople, measurementEntry.getKey()));
                    } else {
                        float quantity = measurementEntry.getValue();
                        if(quantity % 1 == 0) {
                            System.out.println("  - " + (int) quantity * numOfPeople + " " + measurementEntry.getKey());
                        } else {
                            System.out.println("  - " + quantity * numOfPeople + " " + measurementEntry.getKey());
                        }
                    }
                }
                System.out.println();
            }

            System.out.println("Σκέυοι βήματος:\n");

            for (Cookware cwrs : stps.getCookwares()) {
                System.out.println("Όνομα: " + cwrs.getName());
                System.out.println();
            }

            System.out.println();
            counter++;
        }
    }

    private boolean cookwareExists(String cookware, ArrayList<Cookware> cookwares) {
        for (Cookware ckwr : cookwares) {
            if (ckwr.getName().equals(cookware)) {
                return true;
            }
        }
        return false;
    }

    public void readStep(String f) {
        File file = new File(f);

        try (FileReader reader = new FileReader(file)) {
            int data;
            boolean isNewline = false;
            String singlestep = "";

            String timeUnit = "";
            float time = 0;
            String tmpTimeUnit = "";
            String tmpTime = "";
            boolean readingTime = false;
            boolean readingTimeUnit = false;
            totalTime = 0;

            String ingredient = "";
            int quantity = 0;
            String unitMeasurment = "";
            boolean readingIngredient = false;
            HashMap<String, Map<String, Float>> tmpIngredients = new HashMap<>();
            HashMap<String, Float> tmpMeasurements = new HashMap<>();

            String cookware = "";
            boolean readingckwr = false;
            ArrayList<Cookware> tmpCookwares = new ArrayList<>();

            while ((data = reader.read()) != -1) {
                char currentChar = (char) data;

                if ((currentChar == '\n' || currentChar == '\r') && (reader.read() == '\n' || reader.read() == '\r')) {
                    if (isNewline) {

                        steps.add(new Step(singlestep, time, "minutes", tmpIngredients, tmpCookwares));

                        tmpIngredients = new HashMap<>();
                        tmpMeasurements = new HashMap<>();
                        tmpCookwares = new ArrayList<>();

                        time = 0;
                        singlestep = "";

                    }
                    isNewline = true;

                } else {

                    boolean found = false;

                    if (currentChar == '~' && reader.read() == '{') {
                        found = true;
                        readingTime = true;

                        if (tmpTimeUnit.equals("minutes")) {
                            totalTime += Float.parseFloat(tmpTime);
                        } else if (tmpTimeUnit.equals("hours")) {
                            totalTime += Float.parseFloat(tmpTime) * 60;
                        }

                        tmpTime = "";
                        tmpTimeUnit = "";

                    } else if (readingTime) {

                        if (currentChar == '%') {

                            readingTimeUnit = true;

                        } else if (readingTimeUnit) {

                            if (currentChar == '}') {
                                readingTimeUnit = false;
                                readingTime = false;
                                timeUnit = tmpTimeUnit;

                                if (timeUnit.equals("minutes")) {
                                    time += Float.parseFloat(tmpTime);
                                } else if (timeUnit.equals("hours")) {
                                    time += Float.parseFloat(tmpTime) * 60;
                                }

                            } else {
                                tmpTimeUnit += currentChar;
                            }

                        } else {
                            tmpTime += currentChar;
                        }

                    } else if ((char) data == '@') {
                        readingIngredient = true;
                        ingredient = "";
                        quantity = 0;
                        unitMeasurment = "";

                    } else if (readingIngredient) {
                        if ((char) data == '{') {

                            singlestep += '{';

                            singlestep = addIngredient(reader, ingredient, tmpMeasurements, tmpIngredients, singlestep);

                            readingIngredient = false;

                        } else if ((char) data == ' ') {
                            String tmpIngredient = "";

                            singlestep += ' ';

                            while ((data = reader.read()) != -1) {
                                if ((char) data == '{') {
                                    ingredient += ' ' + tmpIngredient;
                                    singlestep += '{';

                                    singlestep = addIngredient(reader, ingredient, tmpMeasurements, tmpIngredients, singlestep);

                                    readingIngredient = false;

                                    break;

                                } else if ((char) data == '#' || (char) data == '~' || (char) data == '@' || (char) data == '.' || (char) data == ',') {

                                    ShoppingList.addOrUpdateIngredient(ingredients, ingredient, "", quantity);

                                    singlestep += (char) data;

                                    tmpMeasurements.put(unitMeasurment, (float) 1);
                                    searchIngredient(ingredient, tmpIngredients, tmpMeasurements);

                                    if ((char) data == '@') {
                                        ingredient = "";
                                        tmpIngredient = "";

                                    } else if((char) data == '#') {
                                        cookware = "";
                                        readingckwr = true;
                                        readingIngredient = false;

                                    } else if((char) data == '~' && reader.read() == '{') {
                                        readingTime = true;
                                        tmpTime = "";
                                        tmpTimeUnit = "";
                                        found = true;

                                    } else {
                                        readingIngredient = false;

                                    }

                                    break;

                                } else {
                                    tmpIngredient += (char) data;
                                    singlestep += (char) data;
                                }
                            }

                        } else if ((char) data == '#' || (char) data == '~' || (char) data == '.' || (char) data == ',') {

                            ShoppingList.addOrUpdateIngredient(ingredients, ingredient, "", quantity);

                            tmpMeasurements.put(unitMeasurment, (float) 1);
                            searchIngredient(ingredient, tmpIngredients, tmpMeasurements);

                            readingIngredient = false;

                        } else {
                            ingredient += (char) data;
                        }

                    } else if (currentChar == '#') {
                        cookware = "";
                        readingckwr = true;

                    } else if (readingckwr) {
                        if (currentChar == '{') {

                            singlestep += '{';

                            if (!cookwareExists(cookware, tmpCookwares)) {
                                tmpCookwares.add(new Cookware(cookware));
                            }

                            if (!cookwareExists(cookware, cookwares)) {
                                cookwares.add(new Cookware(cookware));
                            }
                            readingckwr = false;

                        } else if (currentChar == ' ' ) {
                            String tmpCookware = "";

                            singlestep += ' ';

                            while ((data = reader.read()) != -1) {
                                currentChar = (char) data;

                                if (currentChar == '{') {
                                    singlestep += tmpCookware;
                                    singlestep += '{';

                                    if (!cookware.isEmpty()) {
                                        cookware += " ";
                                    }

                                    cookware += tmpCookware.trim();
                                    if (!cookwareExists(cookware, tmpCookwares)) {
                                        tmpCookwares.add(new Cookware(cookware));
                                    }

                                    if (!cookwareExists(cookware, cookwares)) {
                                        cookwares.add(new Cookware(cookware));
                                    }

                                    cookware = "";
                                    readingckwr = false;
                                    break;

                                } else if (currentChar == '@' || currentChar == '~' || currentChar== '.' || currentChar == ',' || currentChar == '#') {

                                    singlestep += tmpCookware;

                                    if (!cookwareExists(cookware, tmpCookwares)) {
                                        tmpCookwares.add(new Cookware(cookware));
                                    }

                                    if (!cookwareExists(cookware, cookwares)) {
                                        cookwares.add(new Cookware(cookware));
                                    }

                                    if ((char) data == '@') {
                                        readingIngredient = true;
                                        readingckwr = false;
                                        ingredient = "";

                                    } else if((char) data == '#') {
                                        cookware = "";

                                    } else if((char) data == '~' && reader.read() == '{') {
                                        found = true;
                                        readingTime = true;
                                        readingckwr = false;
                                        time = 0;
                                        timeUnit = "";

                                    } else {
                                        readingckwr = false;
                                    }

                                    break;

                                } else {
                                    tmpCookware += currentChar;
                                }

                            }

                        } else if (currentChar == '.' || currentChar == ',' || currentChar == '~') {
                            if (!cookwareExists(cookware, tmpCookwares)) {
                                tmpCookwares.add(new Cookware(cookware));
                            }

                            if (!cookwareExists(cookware, cookwares)) {
                                cookwares.add(new Cookware(cookware));
                            }
                            readingckwr = false;

                        } else {
                            cookware += currentChar;
                        }
                    }

                    if(currentChar != '{') {
                        singlestep += currentChar;
                    }

                    if (found) {
                        singlestep += '{';
                    }

                    isNewline = false;
                }
            }

            if (!tmpTime.isEmpty()) {
                if (tmpTimeUnit.equals("minutes")) {
                    totalTime += Float.parseFloat(tmpTime);
                } else if (tmpTimeUnit.equals("hours")) {
                    totalTime += Float.parseFloat(tmpTime) * 60;
                }
            }

            if(!ingredient.isEmpty()) {
                ShoppingList.addOrUpdateIngredient(ingredients, ingredient, "", 1);
                tmpMeasurements.put(unitMeasurment, (float) 1);
                searchIngredient(ingredient, tmpIngredients, tmpMeasurements);
            }

            if (!cookware.isEmpty()) {
                if (!cookwareExists(cookware, tmpCookwares)) {
                    tmpCookwares.add(new Cookware(cookware));
                }

                if (!cookwareExists(cookware, cookwares)) {
                    cookwares.add(new Cookware(cookware));
                }
            }

            if (!singlestep.isEmpty()) {
                steps.add(new Step(singlestep, time, timeUnit, tmpIngredients, tmpCookwares));
            }

        } catch (IOException e) {
            System.out.println("Εrror reading the file!\nTry again.");
        }
    }

    public void printRecipeInfo(String f) {
        int numOfPeople = numOfPeople();

        readRecipe(f);

        printInfo(numOfPeople);
    }

    private String searchIngredient(String ingredient, Map<String, Map<String, Float>> tmpIngredients, HashMap<String, Float> tmpMeasurements) {
        for (Map.Entry<String, Map<String, Float>> ingredientEntry : ingredients.entrySet()) {
            String ingredientName = ingredientEntry.getKey();

            if (ingredientName.equals(ingredient)) {
                // Add the found ingredient and its values to tmpIngredients
                tmpIngredients.put(ingredientName, tmpMeasurements);
                return ingredientName;
            }

        }
        return "notFound";
    }

    private String addIngredient(FileReader reader, String ingredient, HashMap<String, Float> tmpMeasurements,
                               Map<String, Map<String, Float>> tmpIngredients, String singleStep) throws IOException {
        String tmpQuantity = "";
        String tmpUnitMeasurment = "";
        boolean readingUnitMeasurment = false;
        float quantity = 0;
        String unitMeasurment = "";
        String tmpStep = "";
        int data;

        tmpMeasurements = new HashMap<>();

        while ((data = reader.read()) != -1 && (char) data != '}') {
            if ((char) data == '%') {
                readingUnitMeasurment = true;
            } else if (readingUnitMeasurment) {
                tmpUnitMeasurment += (char) data;
            } else {
                tmpQuantity += (char) data;
            }
            tmpStep += (char) data;
        }

        tmpStep += '}';

        if(tmpQuantity.isEmpty()){
            tmpQuantity = "1";
        }
        if(tmpUnitMeasurment.isEmpty()){
            tmpUnitMeasurment = "";
        }

        quantity = Float.parseFloat(tmpQuantity);
        unitMeasurment = tmpUnitMeasurment;

        ShoppingList.addOrUpdateIngredient(ingredients, ingredient, unitMeasurment, quantity);

        if(unitMeasurment.equals("gr") || unitMeasurment.equals("kg")){
            quantity = MeasurementUnit.addGr(quantity, tmpUnitMeasurment);
            unitMeasurment = "gr";
        } else if(unitMeasurment.equals("ml") || unitMeasurment.equals("l")) {
            quantity = MeasurementUnit.addMl(quantity, tmpUnitMeasurment);
            unitMeasurment = "ml";
        }

        tmpMeasurements.put(unitMeasurment, quantity);
        searchIngredient(ingredient, tmpIngredients, tmpMeasurements);

        return singleStep + tmpStep;
    }

    public static int numOfPeople() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Type the number of people you want to cook for.");

        return scanner.nextInt();
    }

}

