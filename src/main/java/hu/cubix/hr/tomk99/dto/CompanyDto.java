package hu.cubix.hr.tomk99.dto;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public record CompanyDto(long id, int registrationNumber, String name, String address, List<EmployeeDto> employeeDtos) {
    public CompanyDto() {
        this(0,0,null,null,null);
    }
}
