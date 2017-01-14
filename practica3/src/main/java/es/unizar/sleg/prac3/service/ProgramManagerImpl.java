package es.unizar.sleg.prac3.service;

import es.unizar.sleg.prac3.domain.Program;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract1;
import net.sourceforge.tess4j.TesseractException;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ProgramManagerImpl implements ProgramManager {

    private static final Logger logger = Logger.getLogger(ProgramManager.class.getName());
    private static final String CAPTURES_DIR = "capture";
    private static final long DELAY_KEYPRESS = 10;
    private static final long DELAY_BETWEEN_KEYS = 150;
    private static final long DELAY_SCREENSHOT = 100;

    private Robot robot;
    private ITesseract ocr;

    public ProgramManagerImpl() throws InterruptedException, IOException {
        try {
            File capturesDir = new File(CAPTURES_DIR);
            if (capturesDir.isDirectory())
                FileUtils.cleanDirectory(capturesDir);
            else
                capturesDir.mkdir();
            robot = new Robot();
            ocr = new Tesseract1();
            ocr.setLanguage("spa");
            startDatabase();
        } catch (IOException | AWTException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    private void startDatabase() throws IOException, InterruptedException {
        logger.fine("Starting the database program...");
        Process dosbox = Runtime.getRuntime().exec("dosbox msdos-database/gwbasic.bat -noconsole");
        Thread.sleep(3000);
    }

    String readScreen() throws InterruptedException, IOException {
        File capture = captureScreen();
        if (capture != null) {
            try {
                String ocrResult = ocr.doOCR(capture);
                logger.finest("Text recognized with Tesseract: \n" + ocrResult);
                return ocrResult;
            } catch (TesseractException e) {
                logger.log(Level.SEVERE, e.getMessage(), e);
            }
        }
        return null;
    }

    private File captureScreen() throws InterruptedException {
        logger.fine("Capturing screen...");
        robot.keyPress(KeyEvent.VK_CONTROL); robot.keyPress(KeyEvent.VK_F5);
        Thread.sleep(DELAY_KEYPRESS);
        robot.keyRelease(KeyEvent.VK_CONTROL); robot.keyRelease(KeyEvent.VK_F5);
        File capturesDir = new File(CAPTURES_DIR);
        // Give dosbox enough time to process the screenshot and write it to the filesystem
        Thread.sleep(DELAY_SCREENSHOT);
        File[] captures = capturesDir.listFiles();
        if (captures != null) {
            File capture = captures[captures.length - 1];
            logger.info("Generated screen capture in file " + capture.getName());
            return capture;
        } else {
            logger.warning("No capture file generated");
            return null;
        }
    }

    private void keySequence(int[] keys) throws InterruptedException {
        for (int k : keys) {
            // If the key to press is a letter, hold SHIFT
            if (k >= KeyEvent.VK_A && k <= KeyEvent.VK_Z) {
                robot.keyPress(KeyEvent.VK_SHIFT);
            }
            robot.keyPress(k); Thread.sleep(DELAY_KEYPRESS); robot.keyRelease(k); robot.keyRelease(KeyEvent.VK_SHIFT); Thread.sleep(DELAY_BETWEEN_KEYS);
        }
    }

    void goToProgramList(int page) throws InterruptedException {
        keySequence(new int[]{ KeyEvent.VK_6, KeyEvent.VK_ENTER });
        while (page-- > 0) {
            keySequence(new int[]{ KeyEvent.VK_SPACE });
        }
    }

    void pressEnter() throws InterruptedException {
        keySequence(new int[]{ KeyEvent.VK_ENTER });
    }

    @Override
    public int getNumPrograms() {
        logger.fine("Getting the number of programs in the system...");
        final int[] keysSearch = { KeyEvent.VK_4 };
        final int[] keysReturn = { KeyEvent.VK_ENTER };
        try {
            keySequence(keysSearch);
            String result = readScreen().replace("?", "7");
            keySequence(keysReturn);
            Pattern regex = Pattern.compile("CONTIENE (\\d+) ARCHIVOS");
            Matcher m = regex.matcher(result);
            if (m.find()) {
                Integer numPrograms = Integer.parseInt(m.group(1));
                return numPrograms;
            } else {
                return -1;
            }
        } catch (InterruptedException | IOException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
        return -1;
    }

    @Override
    public Program getProgram(int id) {
        logger.fine("Searching program with id = " + id + "...");
        // Key sequence to search the record
        final int[] keysSearch = { KeyEvent.VK_7, KeyEvent.VK_S, KeyEvent.VK_ENTER, KeyEvent.VK_0, KeyEvent.VK_0, KeyEvent.VK_0, KeyEvent.VK_ENTER };
        // Key sequence to return to the main menu
        final int[] keysReturn = { KeyEvent.VK_S, KeyEvent.VK_ENTER, KeyEvent.VK_N, KeyEvent.VK_ENTER, KeyEvent.VK_N, KeyEvent.VK_ENTER };
        // Map the id digits to keys (max 3 digits)
        keysSearch[3] += (id / 100) % 10;
        keysSearch[4] += (id / 10) % 10;
        keysSearch[5] += id % 10;
        try {
            // Execute search sequence
            keySequence(keysSearch);
            // Read the screen
            String result = readScreen();
            // Execute return sequence
            keySequence(keysReturn);
            result = result.substring(0, result.indexOf('\n'));
            // Cleanup
            File capturesDir = new File(CAPTURES_DIR);
            FileUtils.cleanDirectory(capturesDir);
            return Program.parseProgram1(result);
        } catch (InterruptedException | IOException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
        return null;
    }

    @Override
    public Program getProgram(String name) {
        logger.fine("Searching program with name = \"" + name + "\"...");
        // Key sequence to search the record
        final int[] keysSearch = new int[4 + name.length()];
        keysSearch[0] = KeyEvent.VK_7; keysSearch[1] = KeyEvent.VK_N; keysSearch[2] = KeyEvent.VK_ENTER; keysSearch[keysSearch.length - 1] = KeyEvent.VK_ENTER;
        // Key sequence to return to the main menu
        final int[] keysReturn = { KeyEvent.VK_S, KeyEvent.VK_ENTER, KeyEvent.VK_N, KeyEvent.VK_ENTER, KeyEvent.VK_N, KeyEvent.VK_ENTER };
        // Map the name letters to keys
        int i = 3;
        for (char c : name.toLowerCase().toCharArray()) {
            if (c >= 'a' && c <= 'z')
                keysSearch[i++] = KeyEvent.VK_A + c - 'a';
            else if (c == ' ')
                keysSearch[i++] = KeyEvent.VK_SPACE;
        }
        try {
            // Execute search sequence
            keySequence(keysSearch);
            // Read the screen
            String result = readScreen();
            // Execute return sequence
            keySequence(keysReturn);
            result = result.substring(0, result.indexOf('\n'));
            // Cleanup
            File capturesDir = new File(CAPTURES_DIR);
            FileUtils.cleanDirectory(capturesDir);
            return Program.parseProgram1(result);
        } catch (InterruptedException | IOException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<Program> getPrograms() {
        logger.fine("Searching all programs...");
        // Key sequence to return to the main menu
        final int[] keysNext = { KeyEvent.VK_SPACE };
        try {
            // Go to the listing page
            goToProgramList(0);
            List<Program> programs = new ArrayList<>();
            boolean lastPage = false;
            while (!lastPage) {
                // Read the screen
                String result = readScreen();
                lastPage = result.contains("M E N U");
                boolean valid = result.contains("PULSA SPACE");
                if (valid) {
                    String[] results = result.split("\n");
                    for (int i = 1; i < results.length - 1; i++) {
                        Program p = Program.parseProgram2(results[i]);
                        if (p != null)
                            programs.add(p);
                    }
                    keySequence(keysNext);
                } else {
                    Thread.sleep(50);
                }
            }
            File capturesDir = new File(CAPTURES_DIR);
            FileUtils.cleanDirectory(capturesDir);
            return programs;
        } catch (InterruptedException | IOException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
        return null;
    }
}
