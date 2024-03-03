package domain;

import java.io.Serializable;
import java.util.Objects;
//import org.junit.jupiter.api.Test;
public class Doctor implements Identifiable<String>, Serializable{ // can have comparable<>
    String id;
    String name;
    String specialization;
    int score;

    public Doctor(String id, String name, String specialization, int score) {
        this.id = id;
        this.name = name;
        this.specialization = specialization;
        this.score = score;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", specialization='" + specialization + '\'' +
                ", score=" + score +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Doctor doctor = (Doctor) o;
        return score == doctor.score && Objects.equals(id, doctor.id) && Objects.equals(name, doctor.name) && Objects.equals(specialization, doctor.specialization);
    }

}

