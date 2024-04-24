package hu.cubix.hr.tomk99.service;

import hu.cubix.hr.tomk99.model.Company;
import hu.cubix.hr.tomk99.model.Employee;
import hu.cubix.hr.tomk99.repository.CompanyRepository;
import hu.cubix.hr.tomk99.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class InitDbService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public void clearDB() {
        employeeRepository.deleteAll();
        companyRepository.deleteAll();
    }

    public void insertTestData() {
        Company company = new Company(0L, 987654, "InitCompany", "Initial St. 11", new ArrayList<>());
        Employee employee = new Employee(0L, "Init Name", "InitJob", 112211, LocalDateTime.of(2000, 2, 2, 2, 2, 2));
        companyRepository.save(company);
        employeeRepository.save(employee);
    }
}
