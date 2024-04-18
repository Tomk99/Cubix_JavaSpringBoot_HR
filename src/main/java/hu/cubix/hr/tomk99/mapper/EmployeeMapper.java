package hu.cubix.hr.tomk99.mapper;

import hu.cubix.hr.tomk99.dto.EmployeeDto;
import hu.cubix.hr.tomk99.model.Employee;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    public List<EmployeeDto> employeesToDtos(List<Employee> employees);
    @Mapping(target = "id",source = "employeeId")
    public EmployeeDto employeeToDto(Employee employee);

    @InheritInverseConfiguration
    public Employee dtoToEmployee(EmployeeDto employeeDto);
}
