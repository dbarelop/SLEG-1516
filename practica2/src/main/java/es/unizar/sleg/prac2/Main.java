package es.unizar.sleg.prac2;

import es.unizar.sleg.prac2.gui.TaskManagerGui;
import es.unizar.sleg.prac2.x3270.X3270Terminal;

import java.io.IOException;

public class Main {

    private static void showGui(X3270Terminal terminal) {
        TaskManagerGui gui = new TaskManagerGui(terminal);
        gui.pack();
        gui.setVisible(true);
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        X3270Terminal terminal = new X3270Terminal();
        showGui(terminal);
    }
}
