package es.unizar.sleg.prac2.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MUSICSPGui {
    private JPanel mainPanel;
    private JButton tarea1Button;
    private JLabel label;
    private JButton tarea2Button;
    private JTable table1;
    private JButton nuevaTareaButton;
    private JButton salirButton;
    private JButton tarea3Button;

    public MUSICSPGui() {
        tarea1Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                label.setText("prueba");
            }
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
