package hu.cubix.hr.tomk99.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
public class Position {

    @Id
    @GeneratedValue
    private int id;
    private String name;
    private Qualification qualification;
    @OneToMany(mappedBy = "position")
    private List<Employee> employees;

    public Position(String name, Qualification qualification) {
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

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
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
