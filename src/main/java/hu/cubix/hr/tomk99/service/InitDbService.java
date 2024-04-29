package hu.cubix.hr.tomk99.service;

import hu.cubix.hr.tomk99.model.*;
import hu.cubix.hr.tomk99.repository.CompanyRepository;
import hu.cubix.hr.tomk99.repository.EmployeeRepository;
import hu.cubix.hr.tomk99.repository.PositionDetailsByCompanyRepository;
import hu.cubix.hr.tomk99.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    private PositionDetailsByCompanyRepository positionDetailsByCompanyRepository;

    @Transactional
    public void initDb() {
        clearDb();
        insertTestData();
    }

    private void clearDb() {
        employeeRepository.deleteAll();
        companyRepository.deleteAll();
        positionRepository.deleteAll();
        positionDetailsByCompanyRepository.deleteAll();
    }

    private void insertTestData() {
        Position unknownPosition = positionRepository.save(new Position("unknown", Qualification.COLLEGE));

        Company company = new Company(null, 987654, "InitCompany", "Initial St. 11", new ArrayList<>());
        Employee employee = new Employee(null, "Init Name", unknownPosition, 17000, LocalDateTime.now());
        employeeRepository.save(employee);
        company.addEmployee(employee);
        companyRepository.save(company);

        PositionDetailsByCompany positionDetailsByCompany = new PositionDetailsByCompany();
        positionDetailsByCompany.setCompany(company);
        positionDetailsByCompany.setMinSalary(15000);
        positionDetailsByCompany.setPosition(unknownPosition);
        positionDetailsByCompanyRepository.save(positionDetailsByCompany);
    }
}
