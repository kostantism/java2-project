package hua.java2.project.cooking;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class Frame extends JFrame {

    Recipe r = new Recipe(null, null, null, null, 0);
    ShoppingList sl = new ShoppingList();

    private JLabel titleLabel;
    private JLabel messageLabel;
    private JPanel mainPanel;
    private JList<String> list;

    public Frame() {
        super();

        this.setTitle("Βοηθός Μάγειρας");
        this.setSize(1400, 800);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        titleLabel = new JLabel("Λίστα Συνταγών", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));  // Απόσταση 20px μεταξύ των δύο labels

        messageLabel = new JLabel("Επιλέξτε μια συνταγή για να την δείτε, να παράξετε λίστα αγορών ή να την εκτελέσετε", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Στοίχιση στο κέντρο
        mainPanel.add(messageLabel);

        list = new JList<>();
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setFont(new Font("Arial", Font.PLAIN, 16));

        JScrollPane scrollPane = new JScrollPane(list);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        this.add(mainPanel);

//        frame.printRecipeList(args, args.length);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        list.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 1) {
                    String selectedRecipe = list.getSelectedValue();
                    if(selectedRecipe != null) {
//                        JOptionPane.showMessageDialog(Frame.this, "Επιλέξατε τη συνταγή: " + selectedRecipe);
                        showSelectedRecipe(selectedRecipe);
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }

        });



        // Δημιουργία JButton
        JButton executeRecipeButton = new JButton("Παραγωγή Λίστας Αγορών");
        executeRecipeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(executeRecipeButton);

        executeRecipeButton.addActionListener(e -> {
            try {
                ListModel<String> model = list.getModel();
                String[] recipeList = new String[model.getSize()];

                for (int i = 0; i < model.getSize(); i++) {
                    recipeList[i] = model.getElementAt(i);
                }

                sl.printShoppingList(recipeList, Frame.this, mainPanel);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Σφάλμα στη δημιουργία της λίστας αγορών!", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
            }
        });

    }

    public void printRecipeList(int start, String[] args) {
        DefaultListModel<String> model = new DefaultListModel<>();
        for (int i = start; i < args.length; i++) {
            model.addElement(args[i]);
        }
        list.setModel(model);
    }

    public void showSelectedRecipe(String selectedRecipe) {
        mainPanel.removeAll();

        titleLabel = new JLabel("Επιλογές Συνταγής: " + selectedRecipe, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        list = new JList<>();
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setFont(new Font("Arial", Font.PLAIN, 16));

        DefaultListModel<String> model = new DefaultListModel<>();
        model.addElement("Εμφάνιση Συνταγής");
        model.addElement("Παραγωγή Λίστας Αγορών");
        model.addElement("Εκτέλεση Συνταγής");
        list.setModel(model);

        JScrollPane scrollPane = new JScrollPane(list);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.revalidate();
        mainPanel.repaint();

        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 1) {
                    String selectedOption = list.getSelectedValue();
                    if(selectedOption != null) {
//                        JOptionPane.showMessageDialog(Frame.this, "Επιλέξατε τη: " + selectedOption);
                        if(selectedOption.equals("Εμφάνιση Συνταγής")) {
                            r.printRecipeInfo(selectedRecipe, Frame.this, mainPanel);

                        } else if(selectedOption.equals("Παραγωγή Λίστας Αγορών")) {
                            sl.printShoppingList(selectedRecipe, Frame.this, mainPanel);

                        } else if(selectedOption.equals("Εκτέλεση Συνταγής")) {
                            r.executeRecipe(selectedRecipe, Frame.this, mainPanel);
                        }

                    }
                }
            }

        });

    }

    public ArrayList<String> getRecipes(Frame frame) {
        // Δημιουργία JDialog
        JDialog dialog = new JDialog(frame, "Καταχώρηση Συνταγών", true);
        dialog.setSize(300, 200);
        dialog.setLayout(new BoxLayout(dialog.getContentPane(), BoxLayout.Y_AXIS));

        // Δημιουργία μηνύματος
        JLabel messageLabel = new JLabel("Καταχωρήστε τις συνταγές που θέλετε:");
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
        JButton submitButton = new JButton("Καταχώρηση συνταγής");
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        dialog.add(submitButton);

        // Δημιουργία JButton
        JButton stop = new JButton("Τέλος");
        stop.setAlignmentX(Component.CENTER_ALIGNMENT);
        dialog.add(stop);

        // Αποθήκευση του αριθμού
        ArrayList<String> recipes = new ArrayList<>();

        // Ακροατής για το κουμπί
        submitButton.addActionListener(e -> {
            try {
                recipes.add(inputField.getText());
                inputField.setText("");
                inputField.requestFocus();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Παρακαλώ εισάγετε μία συνταγή!", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
            }
        });

        stop.addActionListener(e -> {
            try {
                if(!inputField.getText().isEmpty()) {
                    recipes.add(inputField.getText());
                }
                dialog.dispose(); // Κλείσιμο του διαλόγου
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Παρακαλώ καταχωρήστε τις συνταγές!", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Εμφάνιση του διαλόγου
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);

        return recipes;
    }


}
