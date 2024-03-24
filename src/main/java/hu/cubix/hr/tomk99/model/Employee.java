package hu.cubix.hr.tomk99.model;

import java.time.LocalDateTime;

public class Employee {
    private Long id;
    private String name;
    private int salary;
    private LocalDateTime entryTime;

    public Employee(Long id, String name, int salary, LocalDateTime entryTime) {
        this.id = id;
        this.name = name;
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
