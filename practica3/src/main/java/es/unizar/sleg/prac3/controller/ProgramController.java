package es.unizar.sleg.prac3.controller;

import es.unizar.sleg.prac3.service.ProgramManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ProgramController {

    @Autowired
    private ProgramManager programManager;

}
