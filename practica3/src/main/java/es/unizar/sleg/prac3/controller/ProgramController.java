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

@RestController
public class ProgramController {

    @Autowired
    private ProgramManager programManager;

    @GetMapping("/programs/count")
    public ResponseEntity<Integer> getProgramCount() {
        int numPrograms = programManager.getNumPrograms();
        return new ResponseEntity<>(numPrograms, HttpStatus.OK);
    }

    @GetMapping("/programs")
    public ResponseEntity<List<Program>> getPrograms() {
        List<Program> programs = programManager.getPrograms();
        return new ResponseEntity<>(programs, HttpStatus.OK);
    }

    @GetMapping("/program/{id}")
    public ResponseEntity<Program> getProgram(@PathVariable("id") Integer id) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Program p = programManager.getProgram(id);
        return new ResponseEntity<>(p, HttpStatus.OK);
    }

    @GetMapping("/program/name/{name}")
    public ResponseEntity<Program> getProgram(@PathVariable("name") String name) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Program p = programManager.getProgram(name);
        return new ResponseEntity<>(p, HttpStatus.OK);
    }
}
