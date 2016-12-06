package es.unizar.sleg.prac3.service;

import es.unizar.sleg.prac3.domain.Program;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
// NOTE: execute tests with -Djava.awt.headless=false
public class ProgramManagerTest {

    @Autowired
    private ProgramManager programManager;

    @Test
    public void programSearchTest() {
        final int PROGRAM_ID = 1;
        Program p1 = programManager.getProgram(1);
        final Program p2 = new Program(1, "MUGSY", "CONVERSACIONAL", "A");
        Assert.assertEquals(p1, p2);
    }
}
