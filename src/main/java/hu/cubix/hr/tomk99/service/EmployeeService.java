package hu.cubix.hr.tomk99.service;

import hu.cubix.hr.tomk99.model.Company;
import hu.cubix.hr.tomk99.model.Employee;
import hu.cubix.hr.tomk99.model.Position;
import hu.cubix.hr.tomk99.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static hu.cubix.hr.tomk99.service.EmployeeSpecifications.*;

@Service
public abstract class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }
    public Optional<Employee> findById(long id) {
        return employeeRepository.findById(id);
    }
    @Transactional
    public Employee update(Employee employee) {
        if (findById(employee.getEmployeeId()).isEmpty()) {
            return null;
        }
        return save(employee);
    }
    @Transactional
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }
    @Transactional
    public void delete(long id) {
        employeeRepository.deleteById(id);
    }

    public Page<Employee> filterByMinSalary(int minSalary, Pageable pageable) {
        return employeeRepository.findBySalaryGreaterThanEqual(minSalary, pageable);
    }

    public List<Employee> filterByJob(String jobTitle) {
        return employeeRepository.findByPositionName(jobTitle);
    }

    public List<Employee> filterByNamePrefix(String namePrefix) {
        return employeeRepository.findByNameStartingWithIgnoreCase(namePrefix);
    }

    public List<Employee> employeesBetweenStartEndEntryDate(LocalDateTime startDate, LocalDateTime endDate) {
        return employeeRepository.findByEntryTimeBetween(startDate, endDate);
    }

    public List<Employee> findEmployeesByExample(Employee employee) {
        long id = employee.getEmployeeId();
        String name = employee.getName();
        Position position = employee.getPosition();
        int salary = employee.getSalary();
        LocalDateTime entryTime = employee.getEntryTime();
        Company company = employee.getCompany();

        Specification<Employee> specs = Specification.where(null);

        if (id > 0) specs = specs.and(hasId(id));
        if (StringUtils.hasLength(name)) specs = specs.and(containsName(name));
        if (StringUtils.hasLength(name)) specs = specs.and(exactMatchPositionName(position.getName()));
        if (salary > 0) specs = specs.and(hasSalaryWithinRange(salary));
        if (entryTime != null) specs = specs.and(entryTimeWithinRange(entryTime));
        if (company != null) specs = specs.and(companyNameStartsWith(company.getName()));

        return employeeRepository.findAll(specs);
    }

    public abstract int getPayRaisePercent(Employee employee);
}
