package hu.cubix.hr.tomk99.dto;

import java.time.LocalDateTime;

public class EmployeeDto {
    private Long id;
    private int salary;
    private LocalDateTime entryTime;

    public EmployeeDto(Long id, int salary, LocalDateTime entryTime) {
        this.id = id;
        this.salary = salary;
        this.entryTime = entryTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
