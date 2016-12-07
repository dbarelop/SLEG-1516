package es.unizar.sleg.prac3.service;

import es.unizar.sleg.prac3.domain.Program;
import es.unizar.sleg.prac3.domain.ProgramType;
import org.apache.commons.lang3.StringUtils;
import org.junit.*;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.hamcrest.Matchers.equalTo;
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

    private long t0, t1;

    @Before
    public void before() {
        t0 = System.currentTimeMillis();
    }

    @After
    public void after() {
        t1 = System.currentTimeMillis();
        logger.info("Test completed in " + (t1-t0) + " ms");
    }

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

    @Test
    public void ocrProgramList1() {
        try {
            final String[] expected = {
                    "Nº NOMBRE TIPO CINTA REGISTRO",
                    "19 OLIMPICON ARCADE B 19",
                    "20 MATCH POINT S. DEPORTIVO B-E 20",
                    "21 THE BIRDS AND THE BEES ARCADE B 21",
                    "22 EL ESCALADOR LOCO ARCADE B 22",
                    "23 ZZOOM ARCADE B 23",
                    "24 ZIP ZAP ARCADE B 24",
                    "25 COPYCAT 3 UTILIDAD B 25",
                    "26 COSMIC CRUISER ARCADE B 26",
                    "27 BLACK HOLE ARCADE B 27",
                    "28 CHUCKIE EGG ARCADE B 28",
                    "29 THE KEY UTILIDAD B 29",
                    "30 COOKIE ARCADE B 30",
                    "31 BLUE THUNDER ARCADE B 31",
                    "32 SPACE SHUTTLE SIMULADOR B 32",
                    "33 STATION ZEBRA ARCADE B 33",
                    "34 ANDROIDE II ARCADE B 34",
                    "35 INFRARED UTILIDAD B 35",
                    "36 ZX DISASSEMBLER UTILIDAD B 36",
                    "PULSA SPACE PARA CONTINUAR U OTRA TECLA PARA ACABAR"
            };
            ((ProgramManagerImpl) programManager).goToProgramList(1);
            String result = ((ProgramManagerImpl) programManager).readScreen();
            // Return to main menu
            ((ProgramManagerImpl) programManager).pressEnter();
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

    @Test
    public void ocrProgramList2() {
        try {
            final String[] expected = {
                    "Nº NOMBRE TIPO CINTA REGISTRO",
                    "1 MUGSY CONVERSACIONAL A 1",
                    "2 PAINTBOX UTILIDAD A 1",
                    "3 HUNCHBACK ARCADE A 3",
                    "4 ZAXXAN ARCADE A 4",
                    "5 TOWER OF EVIL ARCADE A 6",
                    "6 REVERSI JUEGO DE MESA A 6",
                    "7 HORACE GOES SKIING ARCADE A 7",
                    "8 HORACE AND THE SPIDERS ARCADE A 8",
                    "9 HUNGRY HORACE ARCADE A 9",
                    "10 MISSILE ARCADE A 10",
                    "11 PSYTRON ARCADE A 11",
                    "12 SPACE RAIDERS ARCADE A 12",
                    "13 PLANETOIDS ARCADE A 13",
                    "14 INTERCEPTOR COBALT SIMULADOR A 14",
                    "15 COMBAT ZONE ARCADE A 15",
                    "16 TRANZ AM ARCADE A 16",
                    "17 AUTOSTOPISTA GALAZTICO ARCADE A 17",
                    "18 HIGH NOON ARCADE A 18",
                    "PULSA SPACE PARA CONTINUAR U OTRA TECLA PARA ACABAR"
            };
            ((ProgramManagerImpl) programManager).goToProgramList(0);
            String result = ((ProgramManagerImpl) programManager).readScreen();
            // Return to main menu
            ((ProgramManagerImpl) programManager).pressEnter();
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

    private void comparePrograms(final Program REFERENCE, Program p) {
        double err = 1.0;
        double sum = 0.0;
        logger.fine("-----------------------------");
        if (p == null) {
            logger.fine("-----------------------------");
            logger.info(String.format("%d - %.2f/%.2f (err = %.2f%%)", REFERENCE.getId(), sum, (REFERENCE.getRegister() == null ? 4.0 : 5.0), err * 100));
        } else {
            double dist = 0.0;
            if (p.getId() != null)
                dist = StringUtils.getJaroWinklerDistance(p.getId().toString(), REFERENCE.getId().toString());
            sum += dist;
            logger.fine(String.format("%.2f/1.00\t\"%s\"", dist, p.getId().toString()));
            dist = 0.0;
            if (p.getName() != null)
                dist = StringUtils.getJaroWinklerDistance(p.getName(), REFERENCE.getName());
            sum += dist;
            logger.fine(String.format("%.2f/1.00\t\"%s\"", dist, p.getName()));
            dist = 0.0;
            if (p.getType() != null)
                dist = StringUtils.getJaroWinklerDistance(p.getType().toString(), REFERENCE.getType().toString());
            sum += dist;
            logger.fine(String.format("%.2f/1.00\t\"%s\"", dist, p.getType()));
            dist = 0.0;
            if (p.getTape() != null)
                dist = StringUtils.getJaroWinklerDistance(p.getTape(), REFERENCE.getTape());
            sum += dist;
            logger.fine(String.format("%.2f/1.00\t\"%s\"", dist, p.getTape()));
            dist = 0.0;
            if (p.getRegister() != null)
                dist = StringUtils.getJaroWinklerDistance(p.getRegister().toString(), REFERENCE.getRegister().toString());
            sum += dist;
            logger.fine(String.format("%.2f/1.00\t\"%s\"", dist, p.getRegister()));
            err -= sum / (REFERENCE.getRegister() == null ? 4.0 : 5.0);
            logger.fine("-----------------------------");
            logger.info(String.format("%d - %.2f/%.2f (err = %.2f%%)", REFERENCE.getId(), sum, (REFERENCE.getRegister() == null ? 4.0 : 5.0), err * 100));
        }
        collector.checkThat(err, lessThan(MAX_ERR));
    }

    @Test
    public void searchAllProgramsTest() {
        final int DATABASE_SIZE = 763;
        final List<Program> REF = new ArrayList<Program>() {{
            add(new Program(1, "MUGSY", ProgramType.CONVERSACIONAL, "A", 1));
            add(new Program(2, "PAINTBOX", ProgramType.UTILIDAD, "A", 2));
            add(new Program(3, "HUNCHBACK", ProgramType.ARCADE, "A", 3));
            add(new Program(4, "ZAXXAN", ProgramType.ARCADE, "A", 4));
            add(new Program(5, "TOWER OF EVIL", ProgramType.ARCADE, "A", 5));
            add(new Program(6, "REVERSI", ProgramType.JUEGO_DE_MESA, "A", 6));
            add(new Program(7, "HORACE GOES SKIING", ProgramType.ARCADE, "A", 7));
            add(new Program(8, "HORACE AND THE SPIDERS", ProgramType.ARCADE, "A", 8));
            add(new Program(9, "HUNGRY HORACE", ProgramType.ARCADE, "A", 9));
            add(new Program(10, "MISSILE", ProgramType.ARCADE, "A", 10));
            add(new Program(11, "PSYTRON", ProgramType.ARCADE, "A", 11));
            add(new Program(12, "SPACE RAIDERS", ProgramType.ARCADE, "A", 12));
            add(new Program(13, "PLANETOIDS", ProgramType.ARCADE, "A", 13));
            add(new Program(14, "INTERCEPTOR COBALT", ProgramType.SIMULADOR, "A", 14));
            add(new Program(15, "COMBAT ZONE", ProgramType.ARCADE, "A", 15));
            add(new Program(16, "TRANZ AM", ProgramType.ARCADE, "A", 16));
            add(new Program(17, "AUTOSTOPISTA GALACTICO", ProgramType.ARCADE, "A", 17));
            add(new Program(18, "HIGH NOON", ProgramType.ARCADE, "A", 18));
        }};
        List<Program> programs = programManager.getPrograms();
        collector.checkThat(programs.size(), equalTo(DATABASE_SIZE));
        /*for (int i = 0; i < REF.size(); i++) {
            comparePrograms(REF.get(i), programs.get(i));
        }*/
    }

    @Test
    public void searchProgram001Test() {
        final Program REF = new Program(1, "MUGSY", ProgramType.CONVERSACIONAL, "A");
        Program p1 = programManager.getProgram(REF.getId());
        comparePrograms(REF, p1);
        Program p2 = programManager.getProgram(REF.getName());
        comparePrograms(REF, p2);
    }

    @Test
    public void searchProgram002Test() {
        final Program REF = new Program(2, "PAINTBOX", ProgramType.UTILIDAD, "A");
        Program p1 = programManager.getProgram(REF.getId());
        comparePrograms(REF, p1);
        Program p2 = programManager.getProgram(REF.getName());
        comparePrograms(REF, p2);
    }

    @Test
    public void searchProgram006Test() {
        final Program REF = new Program(6, "REVERSI", ProgramType.JUEGO_DE_MESA, "A");
        Program p1 = programManager.getProgram(REF.getId());
        comparePrograms(REF, p1);
        Program p2 = programManager.getProgram(REF.getName());
        comparePrograms(REF, p2);
    }

    @Test
    public void searchProgram008Test() {
        final Program REF = new Program(8, "HORACE AND THE SPIDERS", ProgramType.ARCADE, "A");
        Program p1 = programManager.getProgram(REF.getId());
        comparePrograms(REF, p1);
        Program p2 = programManager.getProgram(REF.getName());
        comparePrograms(REF, p2);
    }

    @Test
    public void searchProgram020Test() {
        final Program REF = new Program(20, "MATCH POINT", ProgramType.S_DEPORTIVO, "B-E");
        Program p1 = programManager.getProgram(REF.getId());
        comparePrograms(REF, p1);
        Program p2 = programManager.getProgram(REF.getName());
        comparePrograms(REF, p2);
    }
}
