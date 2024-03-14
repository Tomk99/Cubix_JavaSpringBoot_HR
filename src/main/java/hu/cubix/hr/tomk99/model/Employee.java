package hu.cubix.hr.tomk99.model;

import java.time.LocalDateTime;

public class Employee {
    private Long id;
    private int salary;
    private LocalDateTime entryTime;

    public Employee(Long id, int salary, LocalDateTime entryTime) {
        this.id = id;
        this.salary = salary;
        this.entryTime = entryTime;
    }

    public int getSalary() {
        return salary;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

}
