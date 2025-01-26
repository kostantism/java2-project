package hua.java2.project.cooking;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
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
        mainPanel.setBackground(new Color(223, 222, 222, 255));

        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        titleLabel = new JLabel("Λίστα Συνταγών", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(25, 25, 112));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        messageLabel = new JLabel("Επιλέξτε μια συνταγή για να την δείτε, να παράξετε λίστα αγορών ή να την εκτελέσετε", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        messageLabel.setForeground(new Color(0, 0, 0));
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(messageLabel);

        list = new JList<>();
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setFont(new Font("Arial", Font.PLAIN, 16));
        list.setBackground(new Color(255, 250, 240));
        list.setForeground(new Color(0, 0, 0));
        list.setSelectionBackground(new Color(173, 216, 230));
        list.setSelectionForeground(new Color(0, 0, 0));

        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBackground(new Color(240, 248, 255));
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(25, 25, 112), 1));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        this.add(mainPanel);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        list.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 1) {
                    String selectedRecipe = list.getSelectedValue();
                    if(selectedRecipe != null) {
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
        executeRecipeButton.setFont(new Font("Arial", Font.BOLD, 14));
        executeRecipeButton.setBackground(new Color(60, 179, 113));
        executeRecipeButton.setForeground(Color.WHITE);
        executeRecipeButton.setFocusPainted(false);
        executeRecipeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        executeRecipeButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
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

        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        titleLabel = new JLabel("Επιλογές Συνταγής: " + selectedRecipe, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(25, 25, 112));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        list = new JList<>();
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setFont(new Font("Arial", Font.PLAIN, 16));
        list.setBackground(new Color(255, 250, 240));
        list.setForeground(new Color(0, 0, 0));
        list.setSelectionBackground(new Color(173, 216, 230));
        list.setSelectionForeground(new Color(0, 0, 0));

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
        File selectedFile = getFile();

        // Δημιουργία JDialog
        JDialog dialog = new JDialog(frame, "Καταχώρηση Συνταγών", true);
        dialog.setSize(540, 160);
        dialog.setLayout(new BoxLayout(dialog.getContentPane(), BoxLayout.Y_AXIS));
        dialog.getContentPane().setBackground(new Color(240, 248, 255));

        // Δημιουργία μηνύματος
        JLabel messageLabel = new JLabel("Καταχωρήστε τις συνταγές που θέλετε:");
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        messageLabel.setForeground(new Color(25, 25, 112));
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        dialog.add(messageLabel);

        // Προσθήκη απόστασης
        dialog.add(Box.createRigidArea(new Dimension(0, 10)));

        // Δημιουργία JTextField
        JTextField inputField = new JTextField();
        inputField.setMaximumSize(new Dimension(300, 30));
        inputField.setAlignmentX(Component.CENTER_ALIGNMENT);
        inputField.setFont(new Font("Arial", Font.PLAIN, 14));
        inputField.setForeground(Color.BLACK);
        inputField.setBackground(Color.WHITE);
        inputField.setText(selectedFile.getName());
        dialog.add(inputField);

        // Προσθήκη απόστασης
        dialog.add(Box.createRigidArea(new Dimension(0, 20)));

        // Δημιουργία JPanel για κουμπιά
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setBackground(new Color(240, 248, 255));

        // Δημιουργία JButton για καταχώρηση συνταγής
        JButton submitButton = new JButton("Καταχώρηση Συνταγής");
        submitButton.setFont(new Font("Arial", Font.BOLD, 14));
        submitButton.setBackground(new Color(60, 179, 113));
        submitButton.setForeground(Color.WHITE);
        submitButton.setFocusPainted(false);

        // Δημιουργία JButton για νέα επιλογή συνταγής
        JButton nextRecipeButton = new JButton("Επιλογή Νέας Συνταγής");
        nextRecipeButton.setFont(new Font("Arial", Font.BOLD, 14));
        nextRecipeButton.setBackground(new Color(30, 144, 255));
        nextRecipeButton.setForeground(Color.WHITE);
        nextRecipeButton.setFocusPainted(false);

        // Δημιουργία JButton για τέλος
        JButton stopButton = new JButton("Τέλος");
        stopButton.setFont(new Font("Arial", Font.BOLD, 14));
        stopButton.setBackground(new Color(255, 69, 0));
        stopButton.setForeground(Color.WHITE);
        stopButton.setFocusPainted(false);

        // Προσθήκη κουμπιών στο JPanel
        buttonPanel.add(submitButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPanel.add(nextRecipeButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPanel.add(stopButton);

        // Προσθήκη του buttonPanel στο dialog
        dialog.add(buttonPanel);

        // Αποθήκευση των συνταγών
        ArrayList<String> recipes = new ArrayList<>();

        // Ακροατής για το κουμπί καταχώρησης
        submitButton.addActionListener(e -> {
            try {
                recipes.add(selectedFile.getName());
                inputField.setText("");
                inputField.requestFocus();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Παρακαλώ εισάγετε μία συνταγή!", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Ακροατής για το κουμπί νέας συνταγής
        nextRecipeButton.addActionListener(e -> {
            inputField.setText(getFile().getName());
        });

        // Ακροατής για το κουμπί τέλους
        stopButton.addActionListener(e -> {
            try {
                if (!inputField.getText().isEmpty()) {
                    recipes.add(selectedFile.getName());
                }
                dialog.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Παρακαλώ καταχωρήστε τις συνταγές!", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Εμφάνιση του διαλόγου
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);

        return recipes;
    }

    private File getFile() {
        // Δημιουργία του JFrame (προαιρετικό)
        JFrame fileChooserFrame = new JFrame();
        fileChooserFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fileChooserFrame.setSize(600, 500);

        // Δημιουργία ενός JFileChooser
        JFileChooser fileChooser = new JFileChooser();

        // Ρύθμιση της αρχικής διαδρομής (προαιρετικά)
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));

        // Άνοιγμα του παραθύρου διαλόγου
        int result = fileChooser.showOpenDialog(fileChooserFrame);

        File selectedFile = fileChooser.getSelectedFile();

        return selectedFile;
    }


}
