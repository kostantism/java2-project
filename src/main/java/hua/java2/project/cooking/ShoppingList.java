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
            if(i.getQuantity() == 0){
                System.out.println("Όνομα: " + i.getName());
            } else {
                System.out.println("Όνομα: " + i.getName() + " Ποσότητα: " + i.getQuantity() + " " + i.getMeasurmentUnit());
            }
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

                } else if (readingIngredient) {///////////////////////
                    if ((char) data == '{') {

                        addIngredient(reader, (char) data, ingredient, quantity, unitMeasurment);

                        readingIngredient = false;

                    } else if ((char) data == ' ') {

                        boolean br = false;
                        String tmpIngredient = "";

                        while ((data = reader.read()) != -1 ) {
                            if ((char) data == '{') {

                                addIngredient(reader, (char) data, ingredient, quantity, unitMeasurment);

                                break;

                            } else if((char) data == '#' || (char) data == '~'){
                                boolean found = false;

                                for(Ingredient i : ingredients){
                                    if(i.getName().equals(ingredient)){
                                        i.setQuantity(i.getQuantity() + 1);
                                        found = true;
                                        break;
                                    }
                                }

                                if(!found) {
                                    ingredients.add(new Ingredient(ingredient, 1, ""));
                                }

                                br = true;
                                break;

                            } else {
                                tmpIngredient += (char) data;

                            }
                        }

                        if(br) {
                            break;
                        } else {
                            ingredient += tmpIngredient;

                            readingIngredient = false;
                        }

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
