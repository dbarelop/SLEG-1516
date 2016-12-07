package es.unizar.sleg.prac3.service;

import es.unizar.sleg.prac3.domain.Program;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract1;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class ProgramManagerImpl implements ProgramManager {

    private static final Logger logger = Logger.getLogger(ProgramManager.class.getName());
    private static final String CAPTURES_DIR = "capture";

    private Robot robot;
    private ITesseract ocr;

    public ProgramManagerImpl() throws InterruptedException, IOException {
        try {
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

    public String readScreen() throws InterruptedException, IOException {
        File capture = captureScreen();
        if (capture != null) {
            try {
                String ocrResult = ocr.doOCR(capture);
                logger.fine("Text recognized with Tesseract: \n" + ocrResult);

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
        Thread.sleep(20);
        robot.keyRelease(KeyEvent.VK_CONTROL); robot.keyRelease(KeyEvent.VK_F5);
        File capturesDir = new File(CAPTURES_DIR);
        Thread.sleep(200);
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
            robot.keyPress(k); robot.keyRelease(k); Thread.sleep(20); robot.keyRelease(KeyEvent.VK_SHIFT); Thread.sleep(100);
        }
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
            return Program.parseProgram(result);
        } catch (InterruptedException | IOException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
        return null;
    }

    @Override
    public Program getProgram(String name) {
        return null;
    }

    @Override
    public List<Program> getPrograms() {
        return null;
    }
}
