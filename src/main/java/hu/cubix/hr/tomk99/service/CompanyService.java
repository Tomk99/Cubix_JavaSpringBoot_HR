package hu.cubix.hr.tomk99.service;

import hu.cubix.hr.tomk99.model.AverageSalaryByPosition;
import hu.cubix.hr.tomk99.model.Company;
import hu.cubix.hr.tomk99.model.Employee;
import hu.cubix.hr.tomk99.repository.CompanyRepository;
import hu.cubix.hr.tomk99.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Company> findAll() {
        return companyRepository.findAll();
    }
    public Optional<Company> findById(long id) {
        return companyRepository.findById(id);
    }
    @Transactional
    public Company save(Company company) {
        return companyRepository.save(company);
    }
    @Transactional
    public Company update(Company company) {
        if (findById(company.getId()).isEmpty()) {
            return null;
        }
        return save(company);
    }
    @Transactional
    public void delete(long id) {
        companyRepository.deleteById(id);
    }

    @Transactional
    public Company addNewEmployee(long id, Employee employee) {
        Company company = findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        company.addEmployee(employeeRepository.save(employee));
        return companyRepository.findByIdWithEmployees(id).orElse(null);
    }
    @Transactional
    public Company updateEmployees(long id, List<Employee> employees) {
        Company company = findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        company.getEmployees().forEach(e -> e.setCompany(null));
        company.getEmployees().clear();

        employees.forEach(e -> company.addEmployee(employeeRepository.save(e)));
        return company;
    }
    @Transactional
    public Company deleteEmployeeFromCompany(long companyId, long employeeId) {
        Company company = findById(companyId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        employee.setCompany(null);
        company.getEmployees().remove(employee);
        employeeRepository.save(employee);
        return company;
    }

    public List<Company> findByEmployeeWithGreaterThanMinSalary(int minSalary) {
        return companyRepository.findByEmployeeWithGreaterThanMinSalary(minSalary);
    }

    public List<Company> findByAboveMaxHeadcount(int maxHeadcount) {
        return companyRepository.findByAboveMaxHeadcount(maxHeadcount);
    }

    public List<AverageSalaryByPosition> findAverageSalariesByPosition(long id) {
        return companyRepository.findAverageSalariesByPosition(id);
    }
}
