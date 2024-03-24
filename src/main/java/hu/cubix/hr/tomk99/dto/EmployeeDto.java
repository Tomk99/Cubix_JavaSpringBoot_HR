package hu.cubix.hr.tomk99.dto;

import java.time.LocalDateTime;

public class EmployeeDto {
    private Long id;
    private String name;
    private int salary;
    private LocalDateTime entryTime;

    public EmployeeDto(Long id, String name, int salary, LocalDateTime entryTime) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.entryTime = entryTime;
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
