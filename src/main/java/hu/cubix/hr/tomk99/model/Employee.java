package hu.cubix.hr.tomk99.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class Employee {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String job;
    private int salary;
    private LocalDateTime entryTime;

    public Employee(String name, String job, int salary, LocalDateTime entryTime) {
        this.name = name;
        this.job = job;
        this.salary = salary;
        this.entryTime = entryTime;
    }

    public Employee() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(LocalDateTime entryTime) {
        this.entryTime = entryTime;
    }
}
