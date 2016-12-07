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
    private static final double MAX_ERR = 0.05;

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
                logger.fine(String.format("%.2f/1.00\t\"%s\"", dist, line));
                //collector.checkThat(dist, equalTo(1.0));
            }
            double err = 1 - sum / expected.length;
            logger.fine("-----------------------------");
            logger.info(String.format("%.2f/%.2f (err = %.2f%%)", sum, (double) expected.length, err * 100));
            collector.checkThat(err, lessThan(MAX_ERR));
        } catch (InterruptedException | IOException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    private void comparePrograms(final Program REFERENCE) {
        Program p1 = programManager.getProgram(REFERENCE.getId());
        double sum = 0.0;
        double dist = StringUtils.getJaroWinklerDistance(p1.getId().toString(), REFERENCE.getId().toString());
        sum += dist;
        logger.fine(String.format("%.2f/1.00\t\"%s\"", dist, p1.getId().toString()));
        dist = StringUtils.getJaroWinklerDistance(p1.getName(), REFERENCE.getName());
        sum += dist;
        logger.fine(String.format("%.2f/1.00\t\"%s\"", dist, p1.getName()));
        dist = StringUtils.getJaroWinklerDistance(p1.getType().toString(), REFERENCE.getType().toString());
        sum += dist;
        logger.fine(String.format("%.2f/1.00\t\"%s\"", dist, p1.getType()));
        dist = StringUtils.getJaroWinklerDistance(p1.getTape(), REFERENCE.getTape());
        sum += dist;
        logger.fine(String.format("%.2f/1.00\t\"%s\"", dist, p1.getTape()));
        double err = 1 - sum / 4;
        logger.fine("-----------------------------");
        logger.info(String.format("%.2f/4.00 (err = %.2f%%)", sum, err * 100));
        collector.checkThat(err, lessThan(MAX_ERR));
    }

    @Test
    public void searchProgram001Test() {
        final Program REF = new Program(1, "MUGSY", ProgramType.CONVERSACIONAL, "A");
        comparePrograms(REF);
    }

    @Test
    public void searchProgram002Test() {
        final Program REF = new Program(2, "PAINTBOX", ProgramType.UTILIDAD, "A");
        comparePrograms(REF);
    }

    @Test
    public void searchProgram006Test() {
        final Program REF = new Program(6, "REVERSI", ProgramType.JUEGO_DE_MESA, "A");
        comparePrograms(REF);
    }

    @Test
    public void searchProgram008Test() {
        final Program REF = new Program(8, "HORACE AND THE SPIDERS", ProgramType.ARCADE, "A");
        comparePrograms(REF);
    }

    @Test
    public void searchProgram020Test() {
        final Program REF = new Program(20, "MATCH POINT", ProgramType.S_DEPORTIVO, "B-E");
        comparePrograms(REF);
    }
}
