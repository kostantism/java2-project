package hua.java2.project.cooking;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
        readIngredient(f);
        readCookware(f);
        readStep(f);
    }

    public void printInfo(){
        System.out.println("Yλικά: ");
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

        System.out.println();

        System.out.println("Σκεύοι: ");
        System.out.println();

        for(Cookware ckwrs : cookwares){
            System.out.println(ckwrs.getName());
        }
        System.out.println();

        System.out.println("Συνολικός Χρόνος: ");
        System.out.println();

        System.out.println(t.convert(totalTime, "minutes"));
        System.out.println();

        System.out.println("Αναλυτικά τα βήματα: ");
        System.out.println();

//        int counter = 1;
//        for (Step stps : steps){
//            System.out.println(counter + ". " + stps.getDescription());
//            System.out.println();
//            counter++;
//        }

        int counter = 1;
        for (Step stps : steps) {
            // Print the basic details for each step
            System.out.println(counter + ". " + stps.getDescription());
            System.out.println();

            if(stps.getStepTime() != 0) {
                System.out.println("Χρόνος βήματος: " + t.convert(stps.getStepTime(), "minutes"));
            }
            System.out.println();

            System.out.println("Υλικά βήματος:");
            System.out.println();

            for (Map.Entry<String, Map<String, Float>> ingredientEntry : stps.getIngredients().entrySet()) {
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

            System.out.println("Σκέυοι βήματος:");
            System.out.println();

            for (Cookware cwrs : stps.getCookwares()) {
                System.out.println("Όνομα: " + cwrs.getName());
                System.out.println();
            }

            System.out.println(); // Empty line for better readability
            counter++;
        }

    }

    public void readIngredient(String f) {
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

                                ShoppingList.addOrUpdateIngredient(ingredients, ingredient, "", 1);

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

                        ShoppingList.addOrUpdateIngredient(ingredients, ingredient, "", 1);

                        readingIngredient = false;

                    } else {
                        ingredient += (char) data;
                    }
                }
            }

            if(!ingredient.equals("")) {
                ShoppingList.addOrUpdateIngredient(ingredients, ingredient, "", 1);
            }

        } catch (IOException e) {
            System.out.println("error");
        }
    }

    public void readCookware(String f) {
        File file = new File(f);

        try (FileReader reader = new FileReader(file)) {
            int data;
            String cookware = "";
            boolean readingckwr = false;

            while ((data = reader.read()) != -1) {
                char currentChar = (char) data;

                if (currentChar == '#') {
                    cookware = "";
                    readingckwr = true;
                } else if (readingckwr) {
                    if (currentChar == '{') {
                        readingckwr = false;
                    }else if (currentChar == ' ' ) {
                        String tmpCookware = "";

                        while ((data = reader.read()) != -1) {
                            currentChar = (char) data;

                            if (currentChar == '{') {

                                if (!cookware.isEmpty()) {
                                    cookware += " ";
                                }

                                cookware += tmpCookware.trim();
                                if (!cookwareExists(cookware, cookwares)) {
                                    cookwares.add(new Cookware(cookware));
                                }
                                readingckwr = false;
                                break;

                            } else if (currentChar == '@' || currentChar == '~' || currentChar== '.' || currentChar == ',' || currentChar == '#') {

                                if (!cookwareExists(cookware, cookwares)) {
                                    cookwares.add(new Cookware(cookware));
                                }
                                if (currentChar == '#'){
                                    cookware = "";
                                    tmpCookware="";

                                }else{
                                    readingckwr = false;
                                }
                                break;

                            } else {
                                tmpCookware += currentChar;
                            }

                        }

                    } else if (currentChar == '@' || currentChar == '.' || currentChar == ',' || currentChar == '~' ) {
                        if (!cookwareExists(cookware, cookwares)) {
                            cookwares.add(new Cookware(cookware));
                        }
                        cookware = "";
                        readingckwr = false;
                    } else {
                        cookware += currentChar;
                    }
                }
            }

            if (!cookware.isEmpty()) {
                if (!cookwareExists(cookware, cookwares)) {
                    cookwares.add(new Cookware(cookware));
                }
            }

        } catch (IOException e) {
            System.out.println("error");
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

                            singlestep += '{';//////////////////////

                            if (!cookwareExists(cookware, tmpCookwares)) {
                                tmpCookwares.add(new Cookware(cookware));
                            }

                            readingckwr = false;

                        } else if (currentChar == ' ' ) {
                            String tmpCookware = "";

                            singlestep += ' ';

                            while ((data = reader.read()) != -1) {
                                currentChar = (char) data;

                                if (currentChar == '{') {
//                                    found = true;
                                    singlestep += tmpCookware;///////////
                                    singlestep += '{';////////////////

                                    if (!cookware.isEmpty()) {
                                        cookware += " ";
                                    }

//                                    singlestep += ' ' + tmpCookware;

                                    cookware += tmpCookware.trim();
                                    if (!cookwareExists(cookware, tmpCookwares)) {
                                        tmpCookwares.add(new Cookware(cookware));
                                    }
                                    readingckwr = false;
                                    break;

                                } else if (currentChar == '@' || currentChar == '~' || currentChar== '.' || currentChar == ',' || currentChar == '#') {

                                    singlestep += tmpCookware;
                                    singlestep += currentChar;

                                    if (!cookwareExists(cookware, tmpCookwares)) {
                                        tmpCookwares.add(new Cookware(cookware));
                                    }

                                    /////////////////////////////
                                    if ((char) data == '@') {
                                        readingIngredient = true;
                                        readingckwr = false;
                                        ingredient = "";
//                                        tmpIngredient = "";

                                    } else if((char) data == '#') {
                                        cookware = "";
//                                        readingckwr = true;
//                                        readingIngredient = false;

                                    } else if((char) data == '~' && reader.read() == '{') {
                                        readingTime = true;
                                        readingckwr = false;
//                                        tmpTime = "";
//                                        tmpTimeUnit = "";
                                        time = 0;///////////////////
                                        timeUnit = "";//////////
//                                        found = true;

                                    } else {
                                        readingckwr = false;
                                    }
                                    ////////////////////////////

//                                    if (currentChar == '#') {
//                                        cookware = "";
//                                        tmpCookware="";
//
//                                    } else {
//                                        readingckwr = false;
//                                    }
                                    break;

                                } else {
                                    tmpCookware += currentChar;
//                                    singlestep += currentChar;//////////////
                                }

                            }

                        } else if (currentChar == '.' || currentChar == ',' || currentChar == '~') {
                            if (!cookwareExists(cookware, tmpCookwares)) {
                                tmpCookwares.add(new Cookware(cookware));
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

            if (!cookware.isEmpty()) {
                if (!cookwareExists(cookware, tmpCookwares)) {
                    tmpCookwares.add(new Cookware(cookware));
                }
            }

            if (!singlestep.isEmpty()) {
                steps.add(new Step(singlestep, time, timeUnit, tmpIngredients, tmpCookwares));
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

        ShoppingList.addOrUpdateIngredient(ingredients, ingredient, unitMeasurment, quantity);
    }

    public void printRecipeInfo(String f) {
        readRecipe(f);

        printInfo();
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

        if(tmpQuantity.equals("")){
            tmpQuantity = "1";
        }
        if(tmpUnitMeasurment.equals("")){
            tmpUnitMeasurment = "";
        }

        quantity = Float.parseFloat(tmpQuantity);
        unitMeasurment = tmpUnitMeasurment;

        tmpMeasurements.put(unitMeasurment, quantity);
        searchIngredient(ingredient, tmpIngredients, tmpMeasurements);

        return singleStep + tmpStep;
    }

}

