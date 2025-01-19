package hua.java2.project.cooking;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ShoppingList implements Info {

    Recipe r = new Recipe(null, null,null,null,0);
    static MeasurementUnit ms = new MeasurementUnit("");

    private Map<String, Map<String, Float>> ingredients;

    public ShoppingList() {
        this.ingredients = new HashMap<>();
    }

    //εκτυπωση λιστας αγορας
//    public void printInfo(int numOfPeople) {
//        System.out.println("Λίστα αγορών:");
//        System.out.println();
////        r.printIngredients(numOfPeople, ingredients);
//    }

    public void printInfo(int numOfPeople, Frame frame, JPanel mainPanel) {
//        mainPanel.removeAll();
//
//        mainPanel.setBorder(new EmptyBorder(10, 10, 0, 0));
//
//        JLabel label;
//
//        label = new JLabel("Λίστα αγορών: ", SwingConstants.CENTER);
//        label.setFont(new Font("Arial", Font.BOLD, 20));
//        label.setAlignmentX(Component.LEFT_ALIGNMENT);
//        mainPanel.add(label);
//
//        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
//
//        r.printIngredients(numOfPeople, ingredients, mainPanel);

        mainPanel.removeAll();

        mainPanel.setLayout(new BorderLayout()); // Χρήση BorderLayout για ευκολία
        mainPanel.setBorder(new EmptyBorder(10, 0, 0, 0));
        mainPanel.setBackground(new Color(223, 222, 222, 255));

        // Προσθήκη τίτλου στο πάνω μέρος του mainPanel
        JLabel titleLabel = new JLabel("Λίστα αγορών", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(25, 25, 112));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Δημιουργία ξεχωριστού panel για τα υπόλοιπα στοιχεία
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        listPanel.setBackground(new Color(255, 250, 240));
        listPanel.setForeground(new Color(0, 0, 0));

        JLabel label;

        // Προσθήκη υλικών στη λίστα
        label = new JLabel("Υλικά για αγορά:", SwingConstants.LEFT);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        listPanel.add(label);

        listPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Προσθήκη των υλικών με τη μέθοδο r.printIngredients
        r.printIngredients(numOfPeople, ingredients, listPanel);

        // Προσθήκη του listPanel σε JScrollPane
        JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBackground(new Color(240, 248, 255)); // Απαλό μπλε για το φόντο του JScrollPane 240, 248, 255
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(25, 25, 112), 1));

        // Προσθήκη του scrollPane στο κεντρικό μέρος του mainPanel
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Ανανεώστε το frame για να εμφανιστούν οι αλλαγές
        frame.revalidate();
        frame.repaint();
    }

    //διαβαζει το αρχειο και αποθηκευει τα υλικα με τις ποσοτητες τους
    public void readRecipe(String f) {
        File file = new File(f);
        FileReader reader = null;

        try {
            reader = new FileReader(file);

            int data;
            String ingredient = "";
            int quantity = 0;
            String unitMeasurment = "";
            boolean readingIngredient = false;

            while ((data = reader.read()) != -1) {

                //βρηκε υλικο
                if((char) data == '@') {
                    readingIngredient = true;
                    ingredient = "";
                    quantity = 0;
                    unitMeasurment = "";

                    //διαβαζει υλικο
                } else if (readingIngredient) {
                    if ((char) data == '{') {

                        addIngredient(reader, (char) data, ingredient, quantity, unitMeasurment);

                        readingIngredient = false;

                        //βρηκε κενο οσο διαβαζε υλικο
                    } else if ((char) data == ' ') {

                        String tmpIngredient = "";

                        //ξεκιναει να διαβαζει υλικο σε περιπτωση που βρει { για υλικο με πανω απο μια λεξη
                        while ((data = reader.read()) != -1) {

                            if ((char) data == '{') {
                                ingredient += ' ' + tmpIngredient;

                                addIngredient(reader, (char) data, ingredient, quantity, unitMeasurment);

                                readingIngredient = false;

                                break;

                                //βρηκε αλλο συμβολο
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

                        //βρηκε σκευος, χρονο η αλλο συμβολο
                    } else if ((char) data == '#' || (char) data == '~' || (char) data == '.' || (char) data == ',' ) {
                        addOrUpdateIngredient(ingredients, ingredient, "", quantity);

                        readingIngredient = false;

                    } else {
                        ingredient += (char) data;
                    }
                }
            }

            //σε περιπτωση που στο τελος υπαρχει υλικο χωρις συμβολο ωστε να γινει αποθηκευση
            if(!ingredient.isEmpty()) {
                addOrUpdateIngredient(ingredients, ingredient, "", quantity);
            }

        } catch (IOException e) {
            System.out.println("Εrror reading the file!\nTry again.");
        }
        finally {
            r.closeQuietly(reader);
        }
    }

    //διαβαζει ποσοστητα και μοναδα μετρησης και αποθηκευει το υλικο
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
    private void readRecipes(String[] args, int argsLength) {
        int i;

        for(i = 0; i < argsLength; i++){//////////////////
            readRecipe(args[i]);
        }
    }

    //διαβαζει συνταγες και εκτυπωνει τη λιστα αγορων
    public void printShoppingList(String[] args, Frame frame, JPanel mainPanel) {
        int numOfPeople = r.showNumOfPeopleDialog(frame);

        readRecipes(args, args.length);

        printInfo(numOfPeople, frame, mainPanel);
    }

    public void printShoppingList(String f, Frame frame, JPanel mainPanel) {
        int numOfPeople = r.showNumOfPeopleDialog(frame);

        readRecipe(f);

        printInfo(numOfPeople, frame, mainPanel);
    }

    //προσθετει νεο υλικο η ενημερωνει μονο την ποσοτητα του
    public static void addOrUpdateIngredient(Map<String, Map<String, Float>> ingredients,
                                             String ingredient, String unit, float quantity) {

        if(unit.equals("gr") || unit.equals("kg")){
            quantity = ms.addGr(quantity, unit);
            unit = "gr";
        } else if(unit.equals("ml") || unit.equals("l")) {
            quantity = ms.addMl(quantity, unit);
            unit = "ml";
        }

        if (ingredients.containsKey(ingredient)) {
            Map<String, Float> units = ingredients.get(ingredient);

            if (units.containsKey(unit)) {
                units.put(unit, units.get(unit) + quantity);
            } else {
                units.put(unit, quantity);
            }
        } else {
            Map<String, Float> newUnits = new HashMap<>();
            newUnits.put(unit, quantity);
            ingredients.put(ingredient, newUnits);
        }
    }

    //////////////////////////////////////////////////////////////////2o meros
    public void printRecipeList(String[] args, int argsLength) {
        for(int i = 1; i < argsLength; i++){
            System.out.println(args[i]);
        }
    }

}
