package es.unizar.sleg.prac3.controller;

import es.unizar.sleg.prac3.domain.Program;
import es.unizar.sleg.prac3.service.ProgramManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class ProgramController {

    private static final Logger logger = Logger.getLogger(ProgramController.class.getName());

    @Autowired
    private ProgramManager programManager;

    private void sleep() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    @GetMapping("/programs/count")
    public ResponseEntity<Integer> getProgramCount() {
        sleep();        // Give time to switch windows when developing in one machine
        int numPrograms = programManager.getNumPrograms();
        return new ResponseEntity<>(numPrograms, HttpStatus.OK);
    }

    @GetMapping("/programs")
    public ResponseEntity<List<Program>> getPrograms() {
        sleep();        // Give time to switch windows when developing in one machine
        List<Program> programs = programManager.getPrograms();
        return new ResponseEntity<>(programs, HttpStatus.OK);
    }

    @GetMapping("/program/{id}")
    public ResponseEntity<Program> getProgram(@PathVariable("id") Integer id) {
        sleep();        // Give time to switch windows when developing in one machine
        Program p = programManager.getProgram(id);
        return new ResponseEntity<>(p, HttpStatus.OK);
    }

    @GetMapping("/program/name/{name}")
    public ResponseEntity<Program> getProgram(@PathVariable("name") String name) {
        sleep();        // Give time to switch windows when developing in one machine
        Program p = programManager.getProgram(name);
        return new ResponseEntity<>(p, HttpStatus.OK);
    }
}
