package es.unizar.sleg.prac2.gui;

import es.unizar.sleg.prac2.task.GeneralTask;
import es.unizar.sleg.prac2.task.SpecificTask;
import es.unizar.sleg.prac2.x3270.X3270Terminal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.util.List;

public class TaskManagerGui extends JFrame {
    private X3270Terminal terminal;

    private JButton refreshButton;
    private JButton newTaskButton;
    private JButton exitButton;
    private JTable table1;
    private JTable table2;
    private JPanel mainPanel;
    private JMenuBar menuBar;
    private JTextField userTextField;
    private JPasswordField passwordField;

    private List<GeneralTask> generalTasks;
    private List<SpecificTask> specificTasks;

    public TaskManagerGui(X3270Terminal terminal) {
        this.terminal = terminal;

        setTitle("MUSIC/SP Task Manager");
        setContentPane(mainPanel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        createMenuBar();

        newTaskButton.addActionListener(e -> {
            JDialog newTaskDialog = new NewTaskDialog(terminal);
            newTaskDialog.pack();
            newTaskDialog.setVisible(true);
        });

        exitButton.addActionListener(e -> dispose());
        refreshButton.addActionListener(e -> {
            try {
                generalTasks = terminal.getGeneralTasks();
                specificTasks = terminal.getSpecificTasks();
            } catch (InterruptedException | IOException e1) {
                JOptionPane.showMessageDialog(null, "There was an error fetching the tasks from the mainframe", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void createMenuBar() {
        menuBar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenuItem connectMenuItem = new JMenuItem("Connect");
        JMenuItem disconnectMenuItem = new JMenuItem("Disconnect");
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        connectMenuItem.setEnabled(true);
        disconnectMenuItem.setEnabled(false);

        connectMenuItem.addActionListener(e -> {
            String[] options = new String[]{ "Login", "Cancel" };
            JPanel loginPanel = getLoginPanel();
            int option = JOptionPane.showOptionDialog(null, loginPanel, "Login", JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
            if (option == 0) {
                try {
                    String user = userTextField.getText();
                    String password = new String(passwordField.getPassword());
                    if (terminal.connect()) {
                        terminal.login(user, password);
                        terminal.startLegacyApplication();
                        connectMenuItem.setEnabled(false);
                        disconnectMenuItem.setEnabled(true);
                        refreshButton.setEnabled(true);
                        newTaskButton.setEnabled(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "There was an error when trying to connect to the mainframe", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (IOException | InterruptedException e1) {
                    JOptionPane.showMessageDialog(null, "There was an error when trying to connect to the mainframe", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        disconnectMenuItem.addActionListener(e -> {
            try {
                terminal.disconnect();
                connectMenuItem.setEnabled(true);
                disconnectMenuItem.setEnabled(false);
                refreshButton.setEnabled(false);
                newTaskButton.setEnabled(false);
            } catch (IOException | InterruptedException e1) {
                JOptionPane.showMessageDialog(null, "There was an error when disconnecting from the mainframe", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        exitMenuItem.addActionListener(e -> {
            terminal.close();
            System.exit(0);
        });

        file.add(connectMenuItem);
        file.add(disconnectMenuItem);
        file.add(exitMenuItem);
        menuBar.add(file);
        setJMenuBar(menuBar);
    }

    private JPanel getLoginPanel() {
        JLabel userLabel = new JLabel("Username");
        JLabel passwordLabel = new JLabel("Password");
        userTextField = new JTextField();
        passwordField = new JPasswordField();
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridLayout(2, 2));
        loginPanel.add(userLabel);
        loginPanel.add(userTextField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);
        return loginPanel;
    }
}