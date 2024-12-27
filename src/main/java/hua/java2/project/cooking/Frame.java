package hua.java2.project.cooking;

import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {

    private JLabel titleLabel;
    private JLabel messageLabel;
    private JList<String> list;

    public Frame() {
        super();

//        JPanel mainPanel = new JPanel();
//        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
//
////        this.setLayout(new BorderLayout());
//
//        titleLabel = new JLabel("Λίστα Συνταγών", SwingConstants.CENTER);
////        label.setSize(100, 100);
//        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
//
//        JPanel titlePanel = new JPanel(new BorderLayout());
//        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 30, 0)); // 50px απόσταση από την κορυφή
//        titlePanel.add(titleLabel, BorderLayout.CENTER);
////
////        titleLabel.setBorder(BorderFactory.createEmptyBorder(20,10,10, 10));
////        this.add(titleLabel, BorderLayout.NORTH);
//
//        mainPanel.add(titlePanel);
//
//        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
//
//        messageLabel = new JLabel("Επιλέξτε μια συνταγή", SwingConstants.CENTER);
//        messageLabel.setFont(new Font("Arial", Font.ITALIC, 16));
//        messageLabel.setForeground(Color.GRAY);
//        messageLabel.setBorder(BorderFactory.createEmptyBorder(10,0,10, 0));
////        this.add(messageLabel, BorderLayout.NORTH);
//        mainPanel.add(messageLabel);

        // Δημιουργία του κεντρικού panel με BoxLayout για κατακόρυφη διάταξη
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));  // Εισάγουμε BoxLayout (κατακόρυφα)

        // Δημιουργία του JLabel για τον τίτλο
        titleLabel = new JLabel("Λίστα Συνταγών", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Στοίχιση στο κέντρο
        mainPanel.add(titleLabel);

        // Προσθήκη περιθωρίου για την απόσταση ανάμεσα στα labels
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));  // Απόσταση 20px μεταξύ των δύο labels

        // Δημιουργία του JLabel για το μήνυμα
        messageLabel = new JLabel("Επιλέξτε μια συνταγή", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Στοίχιση στο κέντρο
        mainPanel.add(messageLabel);

        // Προσθήκη του mainPanel στο JFrame
//        this.add(mainPanel);

        list = new JList<>();
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setFont(new Font("Arial", Font.PLAIN, 16));

        JScrollPane scrollPane = new JScrollPane(list);
        mainPanel.add(scrollPane, BorderLayout.CENTER);


//        this.getContentPane().add(label);

        this.add(mainPanel);

    }

    public void printRecipeList(String[] args, int argsLength) {
        DefaultListModel<String> model = new DefaultListModel<>();
        for (int i = 1; i < argsLength; i++) {
            model.addElement(args[i]); // Προσθέτει κάθε στοιχείο στη λίστα
        }
        list.setModel(model);
    }


}
