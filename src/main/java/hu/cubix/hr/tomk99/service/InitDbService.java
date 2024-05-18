package hu.cubix.hr.tomk99.service;

import hu.cubix.hr.tomk99.model.*;
import hu.cubix.hr.tomk99.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class InitDbService {

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private PositionRepository positionRepository;
    @Autowired
    private TimeoffRequestRepository timeoffRequestRepository;
    @Autowired
    private PositionDetailsByCompanyRepository positionDetailsByCompanyRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public void initDb() {
        clearDb();
        insertTestData();
        System.out.println("\033[0;35m"+"Database Initialized!"+"\033[0m");
    }

    private void clearDb() {
        timeoffRequestRepository.deleteAll();
        employeeRepository.deleteAll();
        companyRepository.deleteAll();
        positionDetailsByCompanyRepository.deleteAll();
        positionRepository.deleteAll();
    }

    private void insertTestData() {
        Position position = positionRepository.save(new Position("Tester", Qualification.COLLEGE));
        Position position2 = positionRepository.save(new Position("Test Leader", Qualification.UNIVERSITY));

        Company company = new Company(null, 987654, "InitCompany", "Initial St. 11", new ArrayList<>());
        Employee employee = new Employee(null, "John Doe", position, 17000, LocalDateTime.now());
        employee.setUsername("user1");
        employee.setPassword(passwordEncoder.encode("pass"));
        Employee employee2 = new Employee(null, "John Smith", position2, 40000, LocalDateTime.now());
        employee2.setUsername("user2");
        employee2.setPassword(passwordEncoder.encode("pass"));
        employee.setManager(employee2);
        employeeRepository.save(employee);
        employeeRepository.save(employee2);
        company.addEmployee(employee);
        company.addEmployee(employee2);
        companyRepository.save(company);

        PositionDetailsByCompany positionDetailsByCompany = new PositionDetailsByCompany();
        positionDetailsByCompany.setCompany(company);
        positionDetailsByCompany.setMinSalary(15000);
        positionDetailsByCompany.setPosition(position);
        positionDetailsByCompanyRepository.save(positionDetailsByCompany);
    }
}
