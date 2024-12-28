package hua.java2.project.cooking;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

public class Recipe implements Info {

    Time t = new Time(0, "");
    MeasurementUnit ms = new MeasurementUnit("");

    private String name;
    private Map<String, Map<String, Float>> ingredients;
    private ArrayList<Cookware> cookwares;
    private ArrayList<Step> steps;
    private float totalTime;

    private int numOfPeople;

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

    //εκτυπωνει τις πληροφοριες της συνταγης
    public void printInfo(int numOfPeople){
        System.out.println("\nYλικά συνταγής: \n");

        printIngredients(numOfPeople, ingredients);

        System.out.println("\nΣκεύοι συνταγής: \n");

        printCookwares(cookwares);

        System.out.println("\nΣυνολικός Χρόνος συνταγής: \n");

        System.out.println(t.convert(totalTime, "minutes"));

        System.out.println("\nΑναλυτικά τα βήματα της συνταγής: \n");

        printSteps(numOfPeople);
    }

    public void printInfoFrame(int numOfPeople, JPanel mainPanel){
        JLabel label;

        label = new JLabel("Yλικά συνταγής: ", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 20));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(label);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        label = new JLabel("Σκεύοι συνταγής: ", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 20));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(label);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        label = new JLabel("Συνολικός Χρόνος συνταγής: ", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 20));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(label);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        label = new JLabel("Αναλυτικά τα βήματα της συνταγής: ", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 20));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(label);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    }

    //αναζητηση αν υπαρχει ηδη το σκευος
    private boolean cookwareExists(String cookware, ArrayList<Cookware> cookwares) {
        for (Cookware ckwr : cookwares) {
            if (ckwr.getName().equals(cookware)) {
                return true;
            }
        }
        return false;
    }

    //διαβαζει το αρχειο με την συνταγη και αποθηκευει βηματα, υλικα με ποσοτητες, σκεοι κα χρονο
    public void readRecipe(String f) {
        File file = new File(f);
        FileReader reader = null;

        try {
            reader = new FileReader(file);

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

                //τελος βηματος
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

                    //βρηκε χρονο
                    if (currentChar == '~' && reader.read() == '{') {
                        found = true;
                        readingTime = true;

                        //μετατροπη σε λεπτα
                        if (tmpTimeUnit.equals("minutes")) {
                            totalTime += Float.parseFloat(tmpTime);
                        } else if (tmpTimeUnit.equals("hours")) {
                            totalTime += Float.parseFloat(tmpTime) * 60;
                        }

                        tmpTime = "";
                        tmpTimeUnit = "";

                    //διαβαζει χρονο
                    } else if (readingTime) {

                        //βρηκε μοναδα χρονου
                        if (currentChar == '%') {

                            readingTimeUnit = true;

                        //διαβαζει μοναδα χρονου
                        } else if (readingTimeUnit) {

                            if (currentChar == '}') {
                                readingTimeUnit = false;
                                readingTime = false;
                                timeUnit = tmpTimeUnit;

                                //μετατροπη σε λεπτα
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

                    //βρηκε υλικο
                    } else if ((char) data == '@') {
                        readingIngredient = true;
                        ingredient = "";
                        quantity = 0;
                        unitMeasurment = "";

                    //διαβαζει υλικο
                    } else if (readingIngredient) {
                        if ((char) data == '{') {

                            singlestep += '{';

                            singlestep = addIngredient(reader, ingredient, tmpMeasurements, tmpIngredients, singlestep);

                            readingIngredient = false;

                        //βρηκε κενο οσο διαβαζε υλικο
                        } else if ((char) data == ' ') {
                            String tmpIngredient = "";

                            singlestep += ' ';

                            //ξεκιναει να διαβαζει υλικο σε περιπτωση που βρει { για υλικο με πανω απο μια λεξη
                            while ((data = reader.read()) != -1) {
                                if ((char) data == '{') {
                                    ingredient += ' ' + tmpIngredient;
                                    singlestep += '{';

                                    singlestep = addIngredient(reader, ingredient, tmpMeasurements, tmpIngredients, singlestep);

                                    readingIngredient = false;

                                    break;

                                    //βρηκε αλλο συμβολο οποτε το υλικο εχει μια λεξη χωρις ποσοτητα
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

                        //βρηκε σκευος, χρονο η αλλο συμβολο
                        } else if ((char) data == '#' || (char) data == '~' || (char) data == '.' || (char) data == ',') {

                            ShoppingList.addOrUpdateIngredient(ingredients, ingredient, "", quantity);

                            tmpMeasurements.put(unitMeasurment, (float) 1);
                            searchIngredient(ingredient, tmpIngredients, tmpMeasurements);

                            readingIngredient = false;

                        } else {
                            ingredient += (char) data;
                        }

                    //βρηκε σκευος
                    } else if (currentChar == '#') {
                        cookware = "";
                        readingckwr = true;

                    //διαβαζει σκευος
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

                        //βρηκε κενο οσο διαβαζε σκευος
                        } else if (currentChar == ' ' ) {
                            String tmpCookware = "";

                            singlestep += ' ';

                            //ξεκιναει να διαβαζει σκευος σε περιπτωση που βρει { για σκευος με πανω απο μια λεξη
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

                                    //βρηκε αλλο συμβολο
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

                        //βρηκε χρονο η αλλο συμβολο
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

            //σε περιπτωση που στο τελος υπαρχει χρονος χωρις συμβολο ωστε να γινει αποθηκευση
            if (!tmpTime.isEmpty()) {
                if (tmpTimeUnit.equals("minutes")) {
                    totalTime += Float.parseFloat(tmpTime);
                } else if (tmpTimeUnit.equals("hours")) {
                    totalTime += Float.parseFloat(tmpTime) * 60;
                }
            }

            //σε περιπτωση που στο τελος υπαρχει υλικο χωρις συμβολο ωστε να γινει αποθηκευση
            if(!ingredient.isEmpty()) {
                ShoppingList.addOrUpdateIngredient(ingredients, ingredient, "", 1);
                tmpMeasurements.put(unitMeasurment, (float) 1);
                searchIngredient(ingredient, tmpIngredients, tmpMeasurements);
                tmpIngredients = new HashMap<>();
            }

            //σε περιπτωση που στο τελος υπαρχει σκευος χωρις συμβολο ωστε να γινει αποθηκευση
            if (!cookware.isEmpty()) {
                if (!cookwareExists(cookware, tmpCookwares)) {
                    tmpCookwares.add(new Cookware(cookware));
                }

                if (!cookwareExists(cookware, cookwares)) {
                    cookwares.add(new Cookware(cookware));
                }
            }

            //σε περιπτωση που στο τελευταιο βημα δεν υπαρχει συμβολο ωστε να γινει αποθηκευση
            if (!singlestep.isEmpty()) {
                steps.add(new Step(singlestep, time, timeUnit, tmpIngredients, tmpCookwares));
            }

        } catch (IOException e) {
            System.out.println("Error reading the file!\nTry again.");
        }
        finally {
            closeQuietly(reader);
        }
    }

    //διαβαζει την συνταγη και εκτυπωνει τις πληροφοριες της
    public void printRecipeInfo(String f, Frame frame, JPanel mainPanel) {
//        int numOfPeople = numOfPeople();
//        showNumOfPeoplePanel(frame, mainPanel);

//        int numOfPeople = getNumOfPeople(frame, mainPanel);
//        int numOfPeople = showNumOfPeoplePanel(frame, mainPanel);
        int numOfPeople = showNumOfPeopleDialog(frame);

        readRecipe(f);

        printInfo(numOfPeople);


    }



    //αναζητηση αν υπαρχει το υλικο
    private String searchIngredient(String ingredient, Map<String, Map<String, Float>> tmpIngredients, HashMap<String, Float> tmpMeasurements) {
        for (Map.Entry<String, Map<String, Float>> ingredientEntry : ingredients.entrySet()) {
            String ingredientName = ingredientEntry.getKey();

            if (ingredientName.equals(ingredient)) {
                tmpIngredients.put(ingredientName, tmpMeasurements);
                return ingredientName;
            }

        }
        return "notFound";
    }

    //διαβαζει ποσοτητα και μοναδα μετρησης και αποθηκευει το υλικο
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

        //αποθηκευει το υλικο στον πινακα με ολα τα υλικα της συνταγης
        ShoppingList.addOrUpdateIngredient(ingredients, ingredient, unitMeasurment, quantity);

        if(unitMeasurment.equals("gr") || unitMeasurment.equals("kg")){
            quantity = ms.addGr(quantity, tmpUnitMeasurment);
            unitMeasurment = "gr";
        } else if(unitMeasurment.equals("ml") || unitMeasurment.equals("l")) {
            quantity = ms.addMl(quantity, tmpUnitMeasurment);
            unitMeasurment = "ml";
        }

        //αποθηκευει τα υλικα στο μαπ για τα υλικα του καθε βηματος
        tmpMeasurements.put(unitMeasurment, quantity);
        searchIngredient(ingredient, tmpIngredients, tmpMeasurements);

        return singleStep + tmpStep;
    }

    //διαβαζει το πληθος των ατομων
    public int numOfPeople() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Γράψτε το πλήθος των ατόμων για τα οποία θέλετε να μαγειρέψετε.");

        return scanner.nextInt();
    }

    //////////////////////////////////////////////////////////
//    public int showNumOfPeoplePanel(Frame frame, JPanel mainPanel) {
//        CountDownLatch latch = new CountDownLatch(1);
//
//
//        // Καθαρισμός του mainPanel
//        mainPanel.removeAll();
//
//        // Δημιουργία του JLabel για το μήνυμα
//        JLabel messageLabel = new JLabel("Γράψτε το πλήθος των ατόμων για τα οποία θέλετε να μαγειρέψετε:");
//        messageLabel.setFont(new Font("Arial", Font.PLAIN, 14));
//        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
//        mainPanel.add(messageLabel);
//
//        // Προσθήκη απόστασης
//        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
//
//        // Δημιουργία JTextField για την εισαγωγή του αριθμού
//        JTextField inputField = new JTextField();
//        inputField.setMaximumSize(new Dimension(200, 30)); // Μέγιστο μέγεθος του πεδίου
//        inputField.setAlignmentX(Component.CENTER_ALIGNMENT);
//        mainPanel.add(inputField);
//
//        // Προσθήκη απόστασης
//        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
//
//        // Δημιουργία JButton για την επιβεβαίωση
//        JButton submitButton = new JButton("Επιβεβαίωση");
//        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
//        mainPanel.add(submitButton);
//
//
//        // Ακροατής για το κουμπί
//        submitButton.addActionListener(e -> {
//            try {
//                numOfPeople = Integer.parseInt(inputField.getText());
//                JOptionPane.showMessageDialog(frame, "Πλήθος ατόμων: " + numOfPeople);
//                latch.countDown();
//
//                // Εδώ μπορείς να συνεχίσεις με άλλες λειτουργίες, π.χ., αλλαγή του mainPanel
//            } catch (NumberFormatException ex) {
//                JOptionPane.showMessageDialog(frame, "Παρακαλώ εισάγετε έναν έγκυρο αριθμό!", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
//            }
//
//        });
//
//        // Επανασχεδιασμός του mainPanel
//        mainPanel.revalidate();
//        mainPanel.repaint();
//
////        try {
////            latch.await(); // Περιμένει μέχρι να καλέσει κάποιος το `latch.countDown()`
////        } catch (InterruptedException ex) {
////            ex.printStackTrace();
////        }
//
//        return numOfPeople;
//
//    }

    public int showNumOfPeopleDialog(Frame frame) {
        // Δημιουργία JDialog
        JDialog dialog = new JDialog(frame, "Πλήθος Ατόμων", true);
        dialog.setSize(300, 200);
        dialog.setLayout(new BoxLayout(dialog.getContentPane(), BoxLayout.Y_AXIS));

        // Δημιουργία μηνύματος
        JLabel messageLabel = new JLabel("Γράψτε το πλήθος των ατόμων για τα οποία θέλετε να μαγειρέψετε:");
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        dialog.add(messageLabel);

        // Προσθήκη απόστασης
        dialog.add(Box.createRigidArea(new Dimension(0, 10)));

        // Δημιουργία JTextField
        JTextField inputField = new JTextField();
        inputField.setMaximumSize(new Dimension(200, 30));
        inputField.setAlignmentX(Component.CENTER_ALIGNMENT);
        dialog.add(inputField);

        // Προσθήκη απόστασης
        dialog.add(Box.createRigidArea(new Dimension(0, 10)));

        // Δημιουργία JButton
        JButton submitButton = new JButton("Επιβεβαίωση");
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        dialog.add(submitButton);

        // Αποθήκευση του αριθμού
        final int[] numOfPeople = {0};

        // Ακροατής για το κουμπί
        submitButton.addActionListener(e -> {
            try {
                numOfPeople[0] = Integer.parseInt(inputField.getText());
                dialog.dispose(); // Κλείσιμο του διαλόγου
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Παρακαλώ εισάγετε έναν έγκυρο αριθμό!", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Εμφάνιση του διαλόγου
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);

        // Επιστροφή του αριθμού
        return numOfPeople[0];
    }


//    public int getNumOfPeople(Frame frame, JPanel mainPanel) {
//        showNumOfPeoplePanel(frame, mainPanel);
//
//        return numOfPeople;
//    }
    /////////////////////////////////////////////////////////

    //κλεινει το αρχειο
    public void closeQuietly(Reader reader) {
        try {
            if(reader != null) {
                reader.close();
            }
        } catch (IOException e) {
            System.err.println("Failed to close the file");
        }
    }

    public void printIngredients(int numOfPeople, Map<String, Map<String, Float>> ingredients) {
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

    private void printSteps(int numOfPeople) {
        int counter = 1;
        for (Step stps : steps) {
            System.out.println(counter + ". " + stps.getDescription());
            System.out.println();

            if(stps.getStepTime() != 0) {
                System.out.println("Χρόνος βήματος: " + t.convert(stps.getStepTime(), "minutes"));
            }

            System.out.println("\nΥλικά βήματος:\n");

            printIngredients(numOfPeople, stps.getIngredients());

            System.out.println("Σκέυοι βήματος:\n");

            printCookwares(stps.getCookwares());

            System.out.println();
            counter++;
        }
    }

    private void printCookwares(ArrayList<Cookware> cookwares) {
        for (Cookware cwrs : cookwares) {
            System.out.println("Όνομα: " + cwrs.getName());
            System.out.println();
        }
    }

}

