package es.unizar.sleg.prac2.gui;

import com.sun.java.swing.ui.StatusBar;
import es.unizar.sleg.prac2.exception.AuthenticationException;
import es.unizar.sleg.prac2.task.GeneralTask;
import es.unizar.sleg.prac2.task.SpecificTask;
import es.unizar.sleg.prac2.x3270.X3270Terminal;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskManagerGui extends JFrame {
    private X3270Terminal terminal;

    private JButton refreshButton;
    private JButton newTaskButton;
    private JButton exitButton;
    private JTable generalTasksTable;
    private JTable specificTasksTable;
    private JPanel mainPanel;
    private JPanel statusBarPanel;
    private StatusBar statusBar;
    private JMenuBar menuBar;
    private JTextField userTextField;
    private JPasswordField passwordField;
    private JMenu file;
    private JMenuItem connectMenuItem;
    private JMenuItem disconnectMenuItem;
    private JMenuItem exitMenuItem;

    private List<GeneralTask> generalTasks = new ArrayList<>();
    private List<SpecificTask> specificTasks = new ArrayList<>();

    public TaskManagerGui(X3270Terminal terminal) {
        this.terminal = terminal;

        setTitle("MUSIC/SP Task Manager");
        setContentPane(mainPanel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        statusBar.setMessage("Ready");

        createMenuBar();

        generalTasksTable.setModel(new GeneralTaskTableModel(generalTasks));
        specificTasksTable.setModel(new SpecificTaskTableModel(specificTasks));

        newTaskButton.addActionListener(e -> {
            JDialog newTaskDialog = new NewTaskDialog(terminal);
            newTaskDialog.pack();
            newTaskDialog.setVisible(true);
        });

        exitButton.addActionListener(e -> {
            terminal.close();
            dispose();
        });

        refreshButton.addActionListener(e -> refreshTasks());
    }

    private void createMenuBar() {
        menuBar = new JMenuBar();
        file = new JMenu("File");
        connectMenuItem = new JMenuItem("Connect");
        disconnectMenuItem = new JMenuItem("Disconnect");
        exitMenuItem = new JMenuItem("Exit");
        connectMenuItem.setEnabled(true);
        disconnectMenuItem.setEnabled(false);

        connectMenuItem.addActionListener(e -> connect());
        disconnectMenuItem.addActionListener(e -> disconnect());
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

    private void createUIComponents() {
        statusBarPanel = new StatusBar();
        // workaround
        statusBar = (StatusBar) statusBarPanel;
    }

    private void connect() {
        String[] options = new String[]{ "Login", "Cancel" };
        JPanel loginPanel = getLoginPanel();
        int option = JOptionPane.showOptionDialog(null, loginPanel, "Login", JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        if (option == 0) {
            connectMenuItem.setEnabled(false);
            statusBar.setMessage("Logging in...");
            new Thread(() -> {
                try {
                    String user = userTextField.getText();
                    String password = new String(passwordField.getPassword());
                    if (terminal.connect()) {
                        terminal.login(user, password);
                        terminal.startLegacyApplication();
                        disconnectMenuItem.setEnabled(true);
                        refreshButton.setEnabled(true);
                        newTaskButton.setEnabled(true);
                    } else {
                        connectMenuItem.setEnabled(true);
                        JOptionPane.showMessageDialog(null, "Error trying to connect to the mainframe", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (AuthenticationException e) {
                    connectMenuItem.setEnabled(true);
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                } catch (IOException | InterruptedException e1) {
                    connectMenuItem.setEnabled(true);
                    JOptionPane.showMessageDialog(null, "Error trying to connect to the mainframe", "Error", JOptionPane.ERROR_MESSAGE);
                } finally {
                    statusBar.setMessage("Ready");
                }
            }).start();
        }
    }

    private void disconnect() {
        try {
            terminal.disconnect();
            connectMenuItem.setEnabled(true);
            disconnectMenuItem.setEnabled(false);
            refreshButton.setEnabled(false);
            newTaskButton.setEnabled(false);
        } catch (IOException | InterruptedException e1) {
            JOptionPane.showMessageDialog(null, "There was an error when disconnecting from the mainframe", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refreshTasks() {
        statusBar.setMessage("Refreshing tasks...");
        refreshButton.setEnabled(false);
        newTaskButton.setEnabled(false);
        new Thread(() -> {
            try {
                generalTasks.clear();
                specificTasks.clear();
                generalTasks.addAll(terminal.getGeneralTasks());
                specificTasks.addAll(terminal.getSpecificTasks());
                ((AbstractTableModel) generalTasksTable.getModel()).fireTableDataChanged();
                ((AbstractTableModel) specificTasksTable.getModel()).fireTableDataChanged();
                statusBar.setMessage("Ready");
                refreshButton.setEnabled(true);
                newTaskButton.setEnabled(true);
            } catch (InterruptedException | IOException e1) {
                JOptionPane.showMessageDialog(null, "There was an error fetching the tasks from the mainframe", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }).start();
    }

    private class GeneralTaskTableModel extends AbstractTableModel {

        private final String[] COLUMN_NAMES = { "#", "Fecha", "Descripción" };
        private final List<GeneralTask> generalTasks;

        private GeneralTaskTableModel(List<GeneralTask> generalTasks) {
            this.generalTasks = generalTasks;
        }

        @Override
        public int getRowCount() {
            return generalTasks.size();
        }

        @Override
        public int getColumnCount() {
            return 3;
        }

        @Override
        public String getColumnName(int columnIndex) {
            return COLUMN_NAMES[columnIndex];
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            switch (columnIndex) {
                case 0: return Integer.class;
                case 1: return Date.class;
                case 2: return String.class;
                default: return null;
            }
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            switch (columnIndex) {
                case 0: return generalTasks.get(rowIndex).getId();
                case 1: return generalTasks.get(rowIndex).getDate();
                case 2: return generalTasks.get(rowIndex).getDescription();
                default: return null;
            }
        }
    }

    private class SpecificTaskTableModel extends AbstractTableModel {

        private final String[] COLUMN_NAMES = { "#", "Fecha", "Nombre", "Descripción" };
        private final List<SpecificTask> specificTasks;

        private SpecificTaskTableModel(List<SpecificTask> specificTasks) {
            this.specificTasks = specificTasks;
        }

        @Override
        public int getRowCount() {
            return specificTasks.size();
        }

        @Override
        public int getColumnCount() {
            return 4;
        }

        @Override
        public String getColumnName(int columnIndex) {
            return COLUMN_NAMES[columnIndex];
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            switch (columnIndex) {
                case 0: return Integer.class;
                case 1: return Date.class;
                case 2: return String.class;
                case 3: return String.class;
                default: return null;
            }
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            switch (columnIndex) {
                case 0: return specificTasks.get(rowIndex).getId();
                case 1: return specificTasks.get(rowIndex).getDate();
                case 2: return specificTasks.get(rowIndex).getName();
                case 3: return specificTasks.get(rowIndex).getDescription();
                default: return null;
            }
        }
    }

}
