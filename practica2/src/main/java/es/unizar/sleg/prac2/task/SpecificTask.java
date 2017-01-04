package es.unizar.sleg.prac2.task;

import java.util.Date;

public class SpecificTask extends GeneralTask {
    private String name;

    public SpecificTask(Integer id, Date date, String description, String name) {
        super(id, date, description);
        this.name = name;
    }

    public SpecificTask(Date date, String description, String name) {
        super(date, description);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
