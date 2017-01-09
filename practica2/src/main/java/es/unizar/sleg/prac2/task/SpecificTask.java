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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        SpecificTask that = (SpecificTask) o;

        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
