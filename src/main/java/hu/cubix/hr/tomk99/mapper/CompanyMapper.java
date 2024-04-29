package hu.cubix.hr.tomk99.mapper;

import hu.cubix.hr.tomk99.dto.CompanyDto;
import hu.cubix.hr.tomk99.dto.EmployeeDto;
import hu.cubix.hr.tomk99.model.Company;
import hu.cubix.hr.tomk99.model.Employee;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

    List<CompanyDto> companiesToDtos(List<Company> companies);
    @IterableMapping(qualifiedByName = "summary")
    List<CompanyDto> companiesToSummaryDtos(List<Company> companies);
    CompanyDto companyToDto(Company company);

    @Mapping(target = "employees", ignore = true)
    @Named("summary")
    CompanyDto companyToSummaryDto(Company company);

    Company dtoToCompany(CompanyDto companyDto);
    List<EmployeeDto> employeesToDtos(List<Employee> employees);

    @Mapping(target = "id",source = "employeeId")
    @Mapping(target = "job",source = "position.name")
    @Mapping(target = "company", ignore = true)
    EmployeeDto employeeToDto(Employee employee);

    @InheritInverseConfiguration
    Employee dtoToEmployee(EmployeeDto employeeDto);

    List<Employee> dtosToEmployees(List<EmployeeDto> newEmployees);
}
