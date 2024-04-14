package hu.cubix.hr.tomk99.service;

import hu.cubix.hr.tomk99.model.Company;
import hu.cubix.hr.tomk99.model.Employee;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CompanyService {

    private Map<Long, Company> companies = new HashMap<>();

    public List<Company> findAll() {
        return companies.values().stream().toList();
    }
    public Company findById(long id) {
        return companies.get(id);
    }

    public Company create(Company company) {
        if (findById(company.getId()) != null) {
            return null;
        }
        return save(company);
    }
    public Company update(Company company) {
        if (findById(company.getId()) == null) {
            return null;
        }
        return save(company);
    }
    public Company save(Company company) {
        companies.put(company.getId(),company);
        return company;
    }
    public void delete(long id) {
        companies.remove(id);
    }

    public Company addNewEmployee(long id, Employee employee) {
        Company company = findById(id);
        if (company == null || company.getEmployees().stream().anyMatch(e -> e.getId().equals(employee.getId()))) {
            return null;
        } else {
            company.getEmployees().add(employee);
            return company;
        }
    }

    public Company updateEmployees(long id, List<Employee> employees) {
        Company company = findById(id);
            company.setEmployees(employees);
            return company;
    }
    public boolean deleteEmployeeFromCompany(long companyId, long employeeId) {
        Company companyById = findById(companyId);
        return companyById.getEmployees().removeIf(e -> e.getId().equals(employeeId));
    }
}
