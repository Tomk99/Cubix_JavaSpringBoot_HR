package hu.cubix.hr.tomk99.service;

import hu.cubix.hr.tomk99.model.Employee;
import hu.cubix.hr.tomk99.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
    public Employee update(Employee employee) {
        if (findById(employee.getEmployeeId()).isEmpty()) {
            return null;
        }
        return save(employee);
    }
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

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

    public abstract int getPayRaisePercent(Employee employee);
}
