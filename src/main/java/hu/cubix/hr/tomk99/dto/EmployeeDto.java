package hu.cubix.hr.tomk99.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDateTime;
import java.util.Objects;

public class EmployeeDto {
    private long id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String job;
    @PositiveOrZero
    private int salary;
    @Past
    private LocalDateTime entryTime;
    @JsonIgnore
    private CompanyDto company;

    private TimeoffRequestDto request;
    public EmployeeDto() {
    }

    public EmployeeDto(long id, String name, String job, int salary, LocalDateTime entryTime) {
        this.id = id;
        this.name = name;
        this.job = job;
        this.salary = salary;
        this.entryTime = entryTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public CompanyDto getCompany() {
        return company;
    }

    public void setCompany(CompanyDto company) {
        this.company = company;
    }

    public TimeoffRequestDto getRequest() {
        return request;
    }

    public void setRequest(TimeoffRequestDto request) {
        this.request = request;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeDto that = (EmployeeDto) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
