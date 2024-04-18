package hu.cubix.hr.tomk99.mapper;

import hu.cubix.hr.tomk99.dto.CompanyDto;
import hu.cubix.hr.tomk99.dto.EmployeeDto;
import hu.cubix.hr.tomk99.model.Company;
import hu.cubix.hr.tomk99.model.Employee;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

    public List<CompanyDto> companiesToDtos(List<Company> companies);
    @IterableMapping(qualifiedByName = "summary")
    public List<CompanyDto> companiesToSummaryDtos(List<Company> companies);
    public CompanyDto companyToDto(Company company);

    @Mapping(target = "employees", ignore = true)
    @Named("summary")
    public CompanyDto companyToSummaryDto(Company company);

    public Company dtoToCompany(CompanyDto companyDto);
    public List<EmployeeDto> employeesToDtos(List<Employee> employees);

    @Mapping(target = "id",source = "employeeId")
    public EmployeeDto employeeToDto(Employee employee);

    @InheritInverseConfiguration
    public Employee dtoToEmployee(EmployeeDto employeeDto);

    public List<Employee> dtosToEmployees(List<EmployeeDto> newEmployees);
}
