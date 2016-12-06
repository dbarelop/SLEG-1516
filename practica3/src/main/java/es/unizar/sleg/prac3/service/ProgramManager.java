package es.unizar.sleg.prac3.service;

import es.unizar.sleg.prac3.domain.Program;

import java.util.List;

public interface ProgramManager {

    Program getProgram(int id);

    Program getProgram(String name);

    List<Program> getPrograms();
}
