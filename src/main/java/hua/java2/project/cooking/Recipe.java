package hua.java2.project.cooking;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Recipe implements Info {

    Time t = new Time(0, "");

    private String name;
    private ArrayList<Ingredient> ingredients; //@ , @ {quantity%MeasurementUnit}
    private ArrayList<Cookware> cookwares; // #, # {}
    private ArrayList<Step> steps; // newline
    private float totalTime; // ~{ti%me}

    public Recipe(String name, ArrayList<Ingredient> ingredients, ArrayList<Cookware> cookwares, ArrayList<Step> steps, float totalTime) {
        this.name = name;
        this.ingredients = new ArrayList<>();
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

        for(Ingredient ingr : ingredients){
            if(ingr.getQuantity() == 0){
                System.out.println("Όνομα: " + ingr.getName());
            } else {
                System.out.println("Όνομα: " + ingr.getName() + " Ποσότητα: " + ingr.getQuantity() + " " + ingr.getMeasurmentUnit());
            }
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

        int counter = 1;
        for (Step stps : steps){
            System.out.println(counter + ". " + stps.getDescription());
            System.out.println();
            counter++;
        }

    }

    public void readIngredient(String f){
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

                    if (currentChar == ' ') {
                        String tmpCookware = "";

                        while ((data = reader.read()) != -1) {
                            currentChar = (char) data;

                            if (currentChar == '{') {
                                cookware += tmpCookware;
                                cookwares.add(new Cookware(cookware));
                                readingckwr = false;
                                break;
                            } else if (currentChar == '@' || currentChar == '~') {

                                if (!cookwareExists(cookware)) {
                                    cookwares.add(new Cookware(cookware));
                                }
                                readingckwr = false;
                                break;
                            } else {
                                tmpCookware += currentChar;
                            }

                        }

                    } else {
                        cookware += currentChar;
                    }

                }
            }

        } catch (IOException e) {
            System.out.println("error");
        }
    }

    private boolean cookwareExists(String cookware) {
        for (Cookware ckwr : cookwares) {
            if (ckwr.getName().equals(cookware)) {
                return true;
            }
        }
        return false;
    }


    public void readStep(String f){
        File file = new File(f);
        try (FileReader reader = new FileReader(file)) {
            int data;
            boolean isNewline = false;
            String singlestep ="";

            String timeUnit ="";
            float time = 0;/////////
            String tmpTimeUnit ="";
            String tmpTime = "";
            boolean readingTime = false;
            boolean readingTimeUnit = false;

            totalTime = 0;

            while ((data = reader.read()) != -1) {
                char currentChar = (char) data;

                if ((currentChar == '\n' || currentChar == '\r') && (reader.read() == '\n' || reader.read() == '\r')) {
                    if (isNewline) {

                        //steps.add(new Step(singlestep, time, timeUnit));
                        steps.add(new Step(singlestep, time, "minutes"));

                        time = 0;
                        singlestep = "";

                    }
                    isNewline = true;
                } else {

                    boolean found = false;

                    if(currentChar == '~' && reader.read() == '{') {
                        found = true;
                        readingTime = true;
                        //time = 0;

                        if(tmpTimeUnit.equals("minutes")){
                            totalTime += Float.parseFloat(tmpTime);
                        } else if(tmpTimeUnit.equals("hours")){
                            totalTime += Float.parseFloat(tmpTime) * 60;
                        }

                        tmpTime = "";
                        tmpTimeUnit ="";

                    } else if (readingTime) {
                        if (currentChar == '%') {

                            time += Float.parseFloat(tmpTime);

                            readingTimeUnit = true;

                        } else if (readingTimeUnit) {
                            if(currentChar == '}') {
                                readingTimeUnit = false;
                                readingTime = false;
                                timeUnit = tmpTimeUnit;

                            } else {
                                tmpTimeUnit += currentChar;
                            }

                        } else {
                            tmpTime += currentChar;
                        }
                    }

                    singlestep += currentChar;
                    if(found){
                        singlestep += '{';
                    }
                    isNewline = false;
                }

            }

            if(!tmpTime.equals("")){
                if(tmpTimeUnit.equals("minutes")){
                    totalTime += Float.parseFloat(tmpTime);
                } else if(tmpTimeUnit.equals("hours")){
                    totalTime += Float.parseFloat(tmpTime) * 60;
                }
            }

            if (!singlestep.equals("")) {
                steps.add(new Step(singlestep, time, timeUnit));
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

    public void printRecipeInfo(String f) {
        readRecipe(f);

        printInfo();
    }
}
