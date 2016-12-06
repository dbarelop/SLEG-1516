package es.unizar.sleg.service;

import es.unizar.sleg.domain.Program;
import es.unizar.sleg.repository.ProgramRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProgramManagerImpl implements ProgramManager {

    @Autowired
    private ProgramRepository programRepository;

    @Override
    public List<Program> getPrograms() {
        return programRepository.getPrograms();
    }
}
