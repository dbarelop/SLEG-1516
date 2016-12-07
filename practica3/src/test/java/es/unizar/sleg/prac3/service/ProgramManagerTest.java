package es.unizar.sleg.prac3.service;

import es.unizar.sleg.prac3.domain.Program;
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

    @Test
    public void programSearchTest() {
        final int PROGRAM_ID = 1;
        Program p1 = programManager.getProgram(PROGRAM_ID);
        final Program p2 = new Program(1, "MUGSY", "CONVERSACIONAL", "A");
        double sum = 0.0;
        double dist = StringUtils.getJaroWinklerDistance(p1.getId().toString(), p2.getId().toString());
        sum += dist;
        logger.fine(dist + "/1.0" + "\t\"" + p1.getId().toString());
        dist = StringUtils.getJaroWinklerDistance(p1.getName(), p2.getName());
        sum += dist;
        logger.fine(dist + "/1.0" + "\t\"" + p1.getName());
        dist = StringUtils.getJaroWinklerDistance(p1.getType(), p2.getType());
        sum += dist;
        logger.fine(dist + "/1.0" + "\t\"" + p1.getType());
        dist = StringUtils.getJaroWinklerDistance(p1.getTape(), p2.getTape());
        sum += dist;
        logger.fine(dist + "/1.0" + "\t\"" + p1.getTape());
        double err = 1 - sum / 4;
        logger.fine("--------------------");
        logger.info(sum + "/4.0" + " (err = " + err + ")");
        collector.checkThat(err, lessThan(0.05));
    }
}
