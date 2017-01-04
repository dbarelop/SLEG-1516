package es.unizar.sleg.prac2.x3270;

import java.io.*;

public class X3270Terminal {

    private static final int S3270_PORT = 5000;
    private static final String MAINFRAME_HOST = "155.210.152.51";
    private static final int MAINFRAME_PORT = 101;
    private final Process PROCESS;
    private X3270TerminalState state = X3270TerminalState.DISCONNECTED;

    public X3270Terminal() throws IOException, InterruptedException {
        // TODO: using x3270 for testing, for "production" use s3270
        PROCESS = Runtime.getRuntime().exec("x3270 -scriptport " + S3270_PORT);
        Thread.sleep(1000);
    }

    public void close() {
        PROCESS.destroy();
        state = null;
    }

    private String executeCommand(String cmd) throws IOException, InterruptedException {
        Process p = Runtime.getRuntime().exec(new String[] { "x3270if", "-t", Integer.toString(S3270_PORT), cmd });
        p.waitFor();
        Thread.sleep(500);
        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
        BufferedReader errorReader = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        String err = errorReader.readLine();
        if (err != null) {
            // TODO: throw exception
            System.err.println(err);
        }
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
            builder.append(System.getProperty("line.separator"));
        }
        return builder.toString();
    }

    public boolean connect() throws IOException, InterruptedException {
        if (state == X3270TerminalState.DISCONNECTED) {
            executeCommand("Connect(" + MAINFRAME_HOST + ":" + MAINFRAME_PORT + ")");
            String snap = snapshot();
            if (snap.contains("Press the ENTER key to view next page when you see this message --->  More...")) {
                state = X3270TerminalState.CONNECTED_SCREEN;
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public void disconnect() throws IOException, InterruptedException {
        executeCommand("Disconnect");
        state = X3270TerminalState.DISCONNECTED;
    }

    public boolean login(String username, String password) throws IOException, InterruptedException {
        if (state == X3270TerminalState.CONNECTED_SCREEN) {
            executeCommand("Enter");
            state = X3270TerminalState.LOGIN_SCREEN;
        }
        if (state == X3270TerminalState.LOGIN_SCREEN) {
            executeCommand("String(\"" + username + "\")");
            executeCommand("Tab");
            executeCommand("String(\"" + password + "\")");
            executeCommand("Enter");
            String snap = snapshot();
            if (snap.contains("Userid is not authorized")) {
                // Clear the username field
                executeCommand("EraseEOF");
            } else if (snap.contains("Userid last signed on")) {
                state = X3270TerminalState.LOGGED_IN_SCREEN;
                return true;
            }
        }
        return false;
    }

    public boolean startLegacyApplication() throws IOException, InterruptedException {
        final String LEGACY_APP = "tareas.c";
        if (state == X3270TerminalState.LOGGED_IN_SCREEN) {
            executeCommand("Enter");
            state = X3270TerminalState.MAIN_MENU;
        }
        if (state == X3270TerminalState.MAIN_MENU) {
            executeCommand("String(\"" + LEGACY_APP + "\")");
            executeCommand("Enter");
            Thread.sleep(2000);
            state = X3270TerminalState.LEGACY_APP;
            return true;
        }
        return false;
    }

    public String snapshot() throws IOException, InterruptedException {
        executeCommand("Snap");
        return executeCommand("Snap Ascii");
    }

    public boolean isConnected() {
        return state != X3270TerminalState.DISCONNECTED;
    }

    private enum X3270TerminalState {
        DISCONNECTED,
        CONNECTED_SCREEN,
        LOGIN_SCREEN,
        LOGGED_IN_SCREEN,
        MAIN_MENU,
        LEGACY_APP
    }
}
