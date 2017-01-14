package es.unizar.sleg.prac3.domain;

import java.util.Arrays;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Program {
    private static final Logger logger = Logger.getLogger(Program.class.getName());

    private Integer id;
    private String name;
    private ProgramType type;
    private String tape;
    private Integer register;

    public Program() {
    }

    public Program(Integer id, String name, ProgramType type, String tape, Integer register) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.tape = tape;
        this.register = register;
    }

    public Program(Integer id, String name, ProgramType type, String tape) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.tape = tape;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProgramType getType() {
        return type;
    }

    public void setType(ProgramType type) {
        this.type = type;
    }

    public String getTape() {
        return tape;
    }

    public void setTape(String tape) {
        this.tape = tape;
    }

    public Integer getRegister() {
        return register;
    }

    public void setRegister(Integer register) {
        this.register = register;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Program program = (Program) o;

        if (!id.equals(program.id)) return false;
        if (!name.equals(program.name)) return false;
        if (type != null ? !type.equals(program.type) : program.type != null) return false;
        if (tape != null ? !tape.equals(program.tape) : program.tape != null) return false;
        return register != null ? register.equals(program.register) : program.register == null;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (tape != null ? tape.hashCode() : 0);
        result = 31 * result + (register != null ? register.hashCode() : 0);
        return result;
    }

    public static Program parseProgram1(String str) {
        String programTypes = Arrays.stream(ProgramType.values()).map(ProgramType::toString).collect(Collectors.joining("|"));
        Pattern regex = Pattern.compile("^(\\d+) *- *(.+) (" + programTypes + ") CINTA:(.+)$");
        Matcher m = regex.matcher(str);
        if (m.find()) {
            try {
                Integer id = Integer.parseInt(m.group(1));
                String name = m.group(2);
                ProgramType type = ProgramType.parse(m.group(3));
                String tape = m.group(4);
                return new Program(id, name, type, tape);
            } catch (IllegalStateException e) {
                logger.severe("Error parsing Program from \"" + str + "\"");
            }
        }
        return null;
    }

    public static Program parseProgram2(String str) {
        String programTypes = Arrays.stream(ProgramType.values()).map(ProgramType::toString).collect(Collectors.joining("|"));
        Pattern regex = Pattern.compile("^(\\d+) (.+) (" + programTypes + ") (.+) (\\d+)$");
        Matcher m = regex.matcher(str);
        if (m.find()) {
            try {
                Integer id = Integer.parseInt(m.group(1));
                String name = m.group(2);
                ProgramType type = ProgramType.parse(m.group(3));
                String tape = m.group(4);
                Integer register = Integer.parseInt(m.group(5));
                return new Program(id, name, type, tape, register);
            } catch (IllegalStateException e) {
                logger.severe("Error parsing Program from \"" + str + "\"");
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return id + " - " + name + "(" + type + ")" + " - " + tape + " " + register;
    }
}
