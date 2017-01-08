package es.unizar.sleg.prac2.x3270;

import es.unizar.sleg.prac2.task.GeneralTask;
import es.unizar.sleg.prac2.task.SpecificTask;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        if (state == X3270TerminalState.LEGACY_APP && cmd.equalsIgnoreCase("enter")) {
            // If the command is Enter and the terminal has reached the bottom
            // of the screen, issue another Enter
            String snap = snapshot();
            String[] lines = snap.split("\n");
            if (!lines[lines.length - 2].trim().isEmpty()) {
                return executeCommand(cmd);
            }
        }
        return builder.toString();
    }

    private String snapshot() throws IOException, InterruptedException {
        executeCommand("Snap");
        return executeCommand("Snap Ascii");
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
            //Thread.sleep(500);
            state = X3270TerminalState.LEGACY_APP;
            return true;
        }
        return false;
    }

    public void createGeneralTask(GeneralTask task) throws IOException, InterruptedException {
        if (state == X3270TerminalState.LEGACY_APP) {
            executeCommand("String(\"1\")");    // 1. ASSIGN TASKS
            executeCommand("Enter");
            executeCommand("String(\"1\")");    // 1. GENERAL TASK
            executeCommand("Enter");
            DateFormat df = new SimpleDateFormat("ddMM");
            executeCommand("String(\"" + df.format(task.getDate()) + "\")");    // ENTER DATE (DDMM)
            executeCommand("Enter");
            executeCommand("String(\"" + task.getDescription() + "\")");        // ENTER DESCRIPTION
            executeCommand("Enter");
            // Return to main menu
            executeCommand("String(\"3\")");
            executeCommand("Enter");
        }
    }

    public void createSpecificTask(SpecificTask task) throws IOException, InterruptedException {
        if (state == X3270TerminalState.LEGACY_APP) {
            executeCommand("String(\"1\")");    // 1. ASSIGN TASKS
            executeCommand("Enter");
            executeCommand("String(\"2\")");    // 2. SPECIFIC TASK
            executeCommand("Enter");
            DateFormat df = new SimpleDateFormat("ddMM");
            executeCommand("String(\"" + df.format(task.getDate()) + "\")");    // ENTER DATE (DDMM)
            executeCommand("Enter");
            executeCommand("String(\"" + task.getName() + "\")");               // ENTER NAME
            executeCommand("Enter");
            executeCommand("String(\"" + task.getDescription() + "\")");        // ENTER DESCRIPTION
            executeCommand("Enter");
            // Return to main menu
            executeCommand("String(\"3\")");
            executeCommand("Enter");
        }
    }

    public List<GeneralTask> getGeneralTasks() throws IOException, InterruptedException {
        if (state == X3270TerminalState.LEGACY_APP) {
            executeCommand("String(\"2\")");    // 2. VIEW TASKS
            executeCommand("Enter");
            executeCommand("String(\"1\")");    // 1. GENERAL TASKS
            executeCommand("Enter");
            String snap = snapshot();
            // Return to main menu
            executeCommand("String(\"3\")");    // 3. MAIN MENU
            executeCommand("Enter");
            List<GeneralTask> tasks = new ArrayList<>();
            Pattern p = Pattern.compile("TASK (\\d+): GENERAL (\\d)+ ----- (.+)\\n");
            Matcher m = p.matcher(snap);
            DateFormat df = new SimpleDateFormat("ddMM");
            while (m.find()) {
                int id = Integer.parseInt(m.group(3));
                Date date = null;
                try {
                    date = df.parse(m.group(4));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String description = m.group(5).trim();
                tasks.add(new GeneralTask(id, date, description));
            }
            return tasks;
        }
        return null;
    }

    public List<SpecificTask> getSpecificTasks() throws IOException, InterruptedException {
        if (state == X3270TerminalState.LEGACY_APP) {
            executeCommand("String(\"2\")");    // 2. VIEW TASKS
            executeCommand("Enter");
            executeCommand("String(\"2\")");    // 2. SPECIFIC TASKS
            executeCommand("Enter");
            String snap = snapshot();
            // Return to main menu
            executeCommand("String(\"3\")");    // 3. MAIN MENU
            executeCommand("Enter");
            List<SpecificTask> tasks = new ArrayList<>();
            // TODO: parse snapshot
            return tasks;
        }
        return null;
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
