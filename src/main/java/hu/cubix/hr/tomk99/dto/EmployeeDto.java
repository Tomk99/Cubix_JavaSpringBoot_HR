package hu.cubix.hr.tomk99.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDateTime;

public class EmployeeDto {
    private Long id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String job;
    @PositiveOrZero
    private int salary;
    @Past
    private LocalDateTime entryTime;

    public EmployeeDto(Long id, String name, String job, int salary, LocalDateTime entryTime) {
        this.id = id;
        this.name = name;
        this.job = job;
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
