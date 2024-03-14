package hu.cubix.hr.tomk99.service;

import hu.cubix.hr.tomk99.model.Employee;
import org.springframework.stereotype.Service;

@Service
public interface EmployeeService {
    int getPayRaisePercent(Employee employee);
}
