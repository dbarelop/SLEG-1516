package es.unizar.sleg.prac3.service;

import es.unizar.sleg.prac3.domain.Program;
import es.unizar.sleg.prac3.domain.ProgramType;
import org.apache.commons.lang3.StringUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.hamcrest.Matchers.lessThan;

@RunWith(SpringRunner.class)
@SpringBootTest
// NOTE: execute tests with -Djava.awt.headless=false
public class ProgramManagerTest {

    private static final Logger logger = Logger.getLogger(ProgramManagerTest.class.getName());

    @Autowired
    private ProgramManager programManager;

    @Rule
    public ErrorCollector collector = new ErrorCollector();

    @Test
    public void ocrMainMenuTest() {
        try {
            final String[] expected = {
                    "M E N U",
                    "1 - INTRODUCIR DATOS",
                    "2 - GRABAR DATOS",
                    "3 - ORDENAR DATOS",
                    "4 - INFORMACION BASE DE DATOS",
                    "5 - IMPRIMIR DATOS SEGUN ORDEN",
                    "6 - LISTAR DATOS SEGUN ORDEN",
                    "7 - BUSCAR DATOS",
                    "8 - ACABAR"
            };
            String result = ((ProgramManagerImpl) programManager).readScreen();
            double sum = 0.0;
            int i = 0;
            for (String line : result.split("\n")) {
                double dist = StringUtils.getJaroWinklerDistance(line, expected[i++]);
                sum += dist;
                logger.fine(dist + "/1.0" + "\t\"" + line + "\"");
                //collector.checkThat(dist, equalTo(1.0));
            }
            double err = 1 - sum / expected.length;
            logger.fine("--------------------");
            logger.info(sum + "/" + (double) expected.length + " (err = " + err + ")");
            collector.checkThat(err, lessThan(0.05));
        } catch (InterruptedException | IOException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    private void comparePrograms(final Program REFERENCE) {
        Program p1 = programManager.getProgram(REFERENCE.getId());
        double sum = 0.0;
        double dist = StringUtils.getJaroWinklerDistance(p1.getId().toString(), REFERENCE.getId().toString());
        sum += dist;
        logger.fine(dist + "/1.0" + "\t\"" + p1.getId().toString());
        dist = StringUtils.getJaroWinklerDistance(p1.getName(), REFERENCE.getName());
        sum += dist;
        logger.fine(dist + "/1.0" + "\t\"" + p1.getName());
        dist = StringUtils.getJaroWinklerDistance(p1.getType().toString(), REFERENCE.getType().toString());
        sum += dist;
        logger.fine(dist + "/1.0" + "\t\"" + p1.getType());
        dist = StringUtils.getJaroWinklerDistance(p1.getTape(), REFERENCE.getTape());
        sum += dist;
        logger.fine(dist + "/1.0" + "\t\"" + p1.getTape());
        double err = 1 - sum / 4;
        logger.fine("--------------------");
        logger.info(sum + "/4.0" + " (err = " + err + ")");
        collector.checkThat(err, lessThan(0.05));
    }

    @Test
    public void searchProgram1Test() {
        final Program REF = new Program(1, "MUGSY", ProgramType.CONVERSACIONAL, "A");
        comparePrograms(REF);
    }

    @Test
    public void searchProgram2Test() {
        final Program REF = new Program(2, "PAINTBOX", ProgramType.UTILIDAD, "A");
        comparePrograms(REF);
    }

    @Test
    public void searchProgram6Test() {
        final Program REF = new Program(6, "REVERSI", ProgramType.JUEGO_DE_MESA, "A");
        comparePrograms(REF);
    }

    @Test
    public void searchProgram8Test() {
        final Program REF = new Program(8, "HORACE AND THE SPIDERS", ProgramType.ARCADE, "A");
        comparePrograms(REF);
    }
}
