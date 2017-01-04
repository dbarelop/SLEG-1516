package es.unizar.sleg.prac2.gui;

import es.unizar.sleg.prac2.x3270.X3270Terminal;

import javax.swing.*;

public class TaskManagerGui extends JFrame {
    private X3270Terminal terminal;

    private JButton refreshButton;
    private JButton newTaskButton;
    private JButton exitButton;
    private JTable table1;
    private JTable table2;
    private JPanel mainPanel;

    public TaskManagerGui(X3270Terminal terminal) {
        this.terminal = terminal;

        setTitle("MUSIC/SP Task Manager");
        setContentPane(mainPanel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        newTaskButton.addActionListener(e -> {
            JDialog newTaskDialog = new NewTaskDialog(terminal);
            newTaskDialog.pack();
            newTaskDialog.setVisible(true);
        });

        exitButton.addActionListener(e -> dispose());
    }
}
