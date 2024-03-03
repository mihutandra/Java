package domain;

import java.io.Serializable;
import java.util.Objects;

public class Appointment implements Identifiable<Integer>, Serializable {

    int id;
    private String description;

    private String date;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }


    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public Appointment(int id, String description, String date) {
        this.id = id;
        this.description = description;
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Appointment that = (Appointment) o;
        return Objects.equals(id, that.id) && Objects.equals(description, that.description) && Objects.equals(date, that.date);
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
