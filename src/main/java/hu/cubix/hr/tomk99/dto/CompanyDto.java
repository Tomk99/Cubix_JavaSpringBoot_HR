package hu.cubix.hr.tomk99.dto;

import java.util.List;

public record CompanyDto(long id, int registrationNumber, String name, String address, List<EmployeeDto> employees) {
    public CompanyDto() {
        this(0,0,null,null,null);
    }
}
