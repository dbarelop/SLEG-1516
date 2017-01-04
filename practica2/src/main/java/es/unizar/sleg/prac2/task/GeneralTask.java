package es.unizar.sleg.prac2.task;

import java.util.Date;

public class GeneralTask {
    private Integer id;
    private Date date;
    private String description;

    public GeneralTask(Integer id, Date date, String description) {
        this.id = id;
        this.date = date;
        this.description = description;
    }

    public GeneralTask(Date date, String description) {
        this.date = date;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
