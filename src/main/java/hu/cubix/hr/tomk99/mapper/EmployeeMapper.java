package hu.cubix.hr.tomk99.mapper;

import hu.cubix.hr.tomk99.dto.EmployeeDto;
import hu.cubix.hr.tomk99.model.Employee;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    List<EmployeeDto> employeesToDtos(List<Employee> employees);
    @Mapping(target = "id",source = "employeeId")
    @Mapping(target = "job",source = "position.name")
    @Mapping(target = "company.employees", ignore = true)
    EmployeeDto employeeToDto(Employee employee);

    @InheritInverseConfiguration
    Employee dtoToEmployee(EmployeeDto employeeDto);
}
