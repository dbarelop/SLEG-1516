package es.unizar.sleg.prac2.x3270;

import java.io.*;

public class X3270Terminal {

    private static final int S3270_PORT = 5000;
    private static final String MAINFRAME_HOST = "155.210.152.51";
    private static final int MAINFRAME_PORT = 101;
    private final Process PROCESS;

    public X3270Terminal() throws IOException, InterruptedException {
        PROCESS = Runtime.getRuntime().exec("s3270 -scriptport " + S3270_PORT);
        Thread.sleep(1000);
    }

    public void close() {
        PROCESS.destroy();
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

    public void connect() throws IOException, InterruptedException {
        executeCommand("connect(" + MAINFRAME_HOST + ":" + MAINFRAME_PORT + ")");
    }

    public void disconnect() throws IOException, InterruptedException {
        executeCommand("disconnect");
    }

    public String snapshot() throws IOException, InterruptedException {
        executeCommand("snap");
        return executeCommand("snap Ascii");
    }
}
