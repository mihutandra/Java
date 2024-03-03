package domain;

import java.io.Serializable;

public class Patient implements Identifiable<Integer> , Serializable {
    Integer id;
    private String name;
    private String disease;

    public Patient(Integer id, String name, String disease) {
        this.id = id;
        this.name = name;
        this.disease = disease;
    }

    //GETTERS
    public String getName()
    {
        return this.name;
    }

    public String getDisease(){return this.disease;}

    //SETTERS

    @Override
    public Integer getId(){return id;}

    @Override
    public void setId(Integer id) {
        this.id = id;
    }


    public void setName(String name1) {this.name = name1;}

    public void setDisease(String disease) {this.disease = disease;}

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", disease='" + disease + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o)
    {
        if (!(o instanceof Patient))
            return false;
        Patient d = (Patient)o;
        if (d.id==this.id && d.name.equals(this.name))
            return true;
        return false;
    }

}
