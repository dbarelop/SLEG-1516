package es.unizar.sleg.repository;

import es.unizar.sleg.domain.Program;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract1;
import org.springframework.stereotype.Repository;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
public class ProgramRepositoryImpl implements ProgramRepository {

    private static final Logger logger = Logger.getLogger(ProgramRepositoryImpl.class.getName());

    private Robot robot;
    private ITesseract ocr;

    public ProgramRepositoryImpl() {
        try {
            startDatabase();
            robot = new Robot();
            ocr = new Tesseract1();
            ocr.setLanguage("spa");
        } catch (IOException | AWTException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    private void startDatabase() throws IOException {
        Process dosbox = Runtime.getRuntime().exec("dosbox msdos-database/gwbasic.bat -noconsole");
    }

    private void readScreen() {
        final int x1 = 0, x2 = 300, y1 = 0, y2 = 200;
        BufferedImage capture = robot.createScreenCapture(new Rectangle(x1, y1, x2 - x1, y2 - y1));

    }

    @Override
    public List<Program> getPrograms() {
        return null;
    }
}
