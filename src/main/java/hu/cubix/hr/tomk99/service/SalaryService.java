package hu.cubix.hr.tomk99.service;

import hu.cubix.hr.tomk99.model.Employee;
import org.springframework.stereotype.Service;

@Service
public class SalaryService {

    private final EmployeeService employeeService;

    public SalaryService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public int getSalaryAfterRaise(Employee employee) {
        return (int) (employee.getSalary()*((100+employeeService.getPayRaisePercent(employee))/100.0));
    }
}
