package es.unizar.sleg.prac2.gui;

import es.unizar.sleg.prac2.task.GeneralTask;
import es.unizar.sleg.prac2.task.SpecificTask;
import es.unizar.sleg.prac2.x3270.X3270Terminal;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.event.*;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class NewTaskDialog extends JDialog {
    private final DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM");

    private X3270Terminal terminal;

    private JPanel contentPane;
    private JButton cancelButton;
    private JButton submitButton;
    private JComboBox<String> taskTypeComboBox;
    private JFormattedTextField dateTextField;
    private JTextField nameTextField;
    private JTextArea descriptionTextArea;

    public NewTaskDialog(X3270Terminal terminal) {
        this.terminal = terminal;

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(submitButton);
        // Call onCancel() when the window is closed
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
        // Call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        cancelButton.addActionListener(e -> onCancel());

        submitButton.addActionListener(e -> onSubmit());

        taskTypeComboBox.addItemListener(e -> {
            if (e.getItem().equals("General task")) {
                nameTextField.setEnabled(false);
            } else if (e.getItem().equals("Specific task")) {
                nameTextField.setEnabled(true);
            }
        });
    }

    private void onSubmit() {
        try {
            switch (taskTypeComboBox.getSelectedIndex()) {
                case 0:
                    GeneralTask generalTask = new GeneralTask(DATE_FORMAT.parse(dateTextField.getText()), descriptionTextArea.getText());
                    terminal.createGeneralTask(generalTask);
                    break;
                case 1:
                    SpecificTask specificTask = new SpecificTask(DATE_FORMAT.parse(dateTextField.getText()), descriptionTextArea.getText(), nameTextField.getText());
                    terminal.createSpecificTask(specificTask);
                    break;
            }
            dispose();
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, "Invalid date format", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (InterruptedException | IOException e) {
            JOptionPane.showMessageDialog(null, "There was an error creating the task", "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
        }
    }

    private void onCancel() {
        dispose();
    }

    private void createUIComponents() throws ParseException {
        taskTypeComboBox = new JComboBox<>(new String[]{"General task", "Specific task"});
        dateTextField = new JFormattedTextField(DATE_FORMAT);
        MaskFormatter dateMask = new MaskFormatter("##/##");
        dateMask.install(dateTextField);
    }
}
