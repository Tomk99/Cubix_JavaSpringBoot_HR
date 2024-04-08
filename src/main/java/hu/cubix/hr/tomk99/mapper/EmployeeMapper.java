package hu.cubix.hr.tomk99.mapper;

import hu.cubix.hr.tomk99.dto.EmployeeDto;
import hu.cubix.hr.tomk99.model.Employee;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    public List<EmployeeDto> employeesToDtos(List<Employee> employees);
    public EmployeeDto employeeToDto(Employee employee);

    public Employee dtoToEmployee(EmployeeDto employeeDto);
}
