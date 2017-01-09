package es.unizar.sleg.prac2.task;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GeneralTask that = (GeneralTask) o;

        DateFormat df = new SimpleDateFormat("ddMM");
        if (date != null ? !df.format(date).equals(df.format(that.date)) : that.date != null) return false;
        return description != null ? description.equals(that.description) : that.description == null;
    }

    @Override
    public int hashCode() {
        int result = date != null ? date.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
