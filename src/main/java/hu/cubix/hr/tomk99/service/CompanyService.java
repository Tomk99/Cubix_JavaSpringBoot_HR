package hu.cubix.hr.tomk99.service;

import hu.cubix.hr.tomk99.model.Company;
import hu.cubix.hr.tomk99.model.Employee;
import hu.cubix.hr.tomk99.repository.CompanyRepository;
import hu.cubix.hr.tomk99.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
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

    public Company create(Company company) {
        if (findById(company.getId()).isPresent()) {
            return null;
        }
        return save(company);
    }
    public Company update(Company company) {
        if (findById(company.getId()).isEmpty()) {
            return null;
        }
        return save(company);
    }
    public Company save(Company company) {
        return companyRepository.save(company);
    }
    public void delete(long id) {
        companyRepository.deleteById(id);
    }

    public Company addNewEmployee(long id, Employee employee) {
        Company company = findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        employee.setCompany(companyRepository.getReferenceById(id));
        employeeRepository.save(employee);
        return company;
    }

    public Company updateEmployees(long id, List<Employee> employees) {
        Company company = findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            company.setEmployees(employees);
            return company;
    }
    public boolean deleteEmployeeFromCompany(long companyId, long employeeId) {
        Company companyById = findById(companyId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return companyById.getEmployees().removeIf(e -> e.getId().equals(employeeId));
    }
}
