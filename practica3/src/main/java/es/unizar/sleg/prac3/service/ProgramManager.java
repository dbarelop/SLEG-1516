package es.unizar.sleg.prac3.service;

import es.unizar.sleg.prac3.domain.Program;

import java.util.List;

public interface ProgramManager {

    int getNumPrograms();

    Program getProgram(int id);

    Program getProgram(String name);

    List<Program> getPrograms();

    List<Program> getPrograms(String tape);
}
