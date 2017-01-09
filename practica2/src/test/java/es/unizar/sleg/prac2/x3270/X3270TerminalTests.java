package es.unizar.sleg.prac2.x3270;

import es.unizar.sleg.prac2.task.GeneralTask;
import es.unizar.sleg.prac2.task.SpecificTask;
import org.junit.*;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class X3270TerminalTests {

    private final String USERNAME = "";
    private final String PASSWORD = "";

    private X3270Terminal terminal = new X3270Terminal();

    public X3270TerminalTests() throws IOException, InterruptedException {
    }

    @Before
    public void login() throws IOException, InterruptedException {
        terminal.connect();
        terminal.login(USERNAME, PASSWORD);
        terminal.startLegacyApplication();
    }

    @After
    public void logout() throws IOException, InterruptedException {
        terminal.disconnect();
        terminal.close();
    }

    @Test
    public void createOneGeneralTaskTest() throws IOException, InterruptedException {
        GeneralTask task1 = new GeneralTask(new Date(1483225200000L), "generalTask1");
        terminal.createGeneralTask(task1);
        List<GeneralTask> tasks = terminal.getGeneralTasks();
        Assert.assertTrue(tasks.contains(task1));
    }

    @Test
    public void createThreeGeneralTasksTest() throws IOException, InterruptedException {
        GeneralTask task1 = new GeneralTask(new Date(1483225200000L), "generalTask1");
        GeneralTask task2 = new GeneralTask(new Date(1480633200000L), "generalTask2");
        GeneralTask task3 = new GeneralTask(new Date(1478127600000L), "generalTask3");
        terminal.createGeneralTask(task1);
        terminal.createGeneralTask(task2);
        terminal.createGeneralTask(task3);
        List<GeneralTask> tasks = terminal.getGeneralTasks();
        Assert.assertTrue(tasks.contains(task1));
        Assert.assertTrue(tasks.contains(task2));
        Assert.assertTrue(tasks.contains(task3));
    }

    @Test
    public void createOneSpecificTaskTest() throws IOException, InterruptedException {
        SpecificTask task1 = new SpecificTask(new Date(1483225200000L), "specificTask1", "name1");
        terminal.createSpecificTask(task1);
        List<SpecificTask> tasks = terminal.getSpecificTasks();
        Assert.assertTrue(tasks.contains(task1));
    }

    @Test
    @Ignore
    public void createThreeSpecificTasksTest() throws IOException, InterruptedException {
        SpecificTask task1 = new SpecificTask(new Date(1483225200000L), "specificTask1", "name1");
        SpecificTask task2 = new SpecificTask(new Date(1480633200000L), "specificTask2", "name2");
        SpecificTask task3 = new SpecificTask(new Date(1478127600000L), "specificTask3", "name3");
        terminal.createSpecificTask(task1);
        terminal.createSpecificTask(task2);
        terminal.createSpecificTask(task3);
        List<SpecificTask> tasks = terminal.getSpecificTasks();
        Assert.assertTrue(tasks.contains(task1));
        Assert.assertTrue(tasks.contains(task2));
        Assert.assertTrue(tasks.contains(task3));
    }
}
