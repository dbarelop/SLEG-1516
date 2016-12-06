package es.unizar.sleg.prac3.domain;

public class Program {

    private Integer id;
    private String name;
    private String type;
    private String tape;
    private Integer register;

    public Program() {
    }

    public Program(Integer id, String name, String type, String tape, Integer register) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.tape = tape;
        this.register = register;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
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
}
