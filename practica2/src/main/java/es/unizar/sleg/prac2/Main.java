package es.unizar.sleg.prac2;

import es.unizar.sleg.prac2.x3270.X3270Terminal;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        X3270Terminal terminal = new X3270Terminal();
        terminal.connect();
        String snap = terminal.snapshot().replaceAll("(?m)^\\s+$", "\n");
        System.out.println(snap);
        terminal.disconnect();
        terminal.close();
    }
}
