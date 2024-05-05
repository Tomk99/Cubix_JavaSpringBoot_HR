package hu.cubix.hr.tomk99.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Position {

    @Id
    @GeneratedValue
    private int id;
    private String name;
    private Qualification qualification;

    public Position(String name, Qualification qualification) {
        this.name = name;
        this.qualification = qualification;
    }

    public Position(int id, String name, Qualification qualification) {
        this.id = id;
        this.name = name;
        this.qualification = qualification;
    }

    public Position() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Qualification getQualification() {
        return qualification;
    }

    public void setQualification(Qualification qualification) {
        this.qualification = qualification;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return id == position.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
