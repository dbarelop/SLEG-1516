package es.unizar.sleg.prac2;

import es.unizar.sleg.prac2.gui.MUSICSPGui;
import es.unizar.sleg.prac2.x3270.X3270Terminal;

import javax.swing.*;
import java.io.IOException;

public class Main {

    private static void showGui() {
        JFrame mainFrame = new JFrame("MUSICSPGui");
        mainFrame.setContentPane(new MUSICSPGui().getMainPanel());
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.pack();
        mainFrame.setVisible(true);
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        X3270Terminal terminal = new X3270Terminal();
        terminal.connect();
        terminal.login("GRUPO_11", "");
        terminal.startLegacyApplication();
        String snap = terminal.snapshot().replaceAll("(?m)^\\s+$", "\n");
        System.out.println(snap);
        terminal.disconnect();
        terminal.close();
    }
}
