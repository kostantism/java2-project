package hua.java2.project.cooking;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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
        this.setSize(500, 500);

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

    }

    public void printRecipeList(String[] args, int argsLength) {
        DefaultListModel<String> model = new DefaultListModel<>();
        for (int i = 1; i < argsLength; i++) {
            model.addElement(args[i]);
        }
        list.setModel(model);
    }

    private void showSelectedRecipe(String selectedRecipe) {
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
                            r.printRecipeInfo(selectedRecipe);

                        } else if(selectedOption.equals("Παραγωγή Λίστας Αγορών")) {

                        } else if(selectedOption.equals("Εκτέλεση Συνταγής")) {

                        }

                    }
                }
            }

        });

    }


}
