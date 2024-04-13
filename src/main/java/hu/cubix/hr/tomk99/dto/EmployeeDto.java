package hu.cubix.hr.tomk99.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDateTime;

public record EmployeeDto(long id, @NotEmpty String name, @NotEmpty String job, @PositiveOrZero int salary, @Past LocalDateTime entryTime) {
    public EmployeeDto() {
        this(0,null,null,0,null);
    }
}
