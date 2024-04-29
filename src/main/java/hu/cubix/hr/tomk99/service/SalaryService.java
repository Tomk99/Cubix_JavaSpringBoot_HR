package hu.cubix.hr.tomk99.service;

import hu.cubix.hr.tomk99.model.Employee;
import hu.cubix.hr.tomk99.repository.EmployeeRepository;
import hu.cubix.hr.tomk99.repository.PositionDetailsByCompanyRepository;
import hu.cubix.hr.tomk99.repository.PositionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SalaryService {

    private final EmployeeService employeeService;
    private final PositionRepository positionRepository;
    private final PositionDetailsByCompanyRepository positionDetailsByCompanyRepository;
    private final EmployeeRepository employeeRepository;


    public SalaryService(EmployeeService employeeService, PositionRepository positionRepository, PositionDetailsByCompanyRepository positionDetailsByCompanyRepository, EmployeeRepository employeeRepository) {
        this.employeeService = employeeService;
        this.positionRepository = positionRepository;
        this.positionDetailsByCompanyRepository = positionDetailsByCompanyRepository;
        this.employeeRepository = employeeRepository;
    }

    public void setNewSalary(Employee employee) {
        int newSalary = employee.getSalary() * (100 + employeeService.getPayRaisePercent(employee)) / 100;
        employee.setSalary(newSalary);
    }

    @Transactional
    public void raiseMinSalary(long companyId, String positionName, int minSalary) {
        positionDetailsByCompanyRepository.findByPositionNameAndCompanyId(positionName, companyId)
                .forEach(pd -> {
                    pd.setMinSalary(minSalary);
                    employeeRepository.updateSalaries(companyId, positionName, minSalary);
                });
    }
}
