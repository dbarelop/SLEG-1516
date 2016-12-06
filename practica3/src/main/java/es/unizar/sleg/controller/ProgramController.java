package es.unizar.sleg.controller;

import es.unizar.sleg.service.ProgramManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ProgramController {

    @Autowired
    private ProgramManager programManager;

}
