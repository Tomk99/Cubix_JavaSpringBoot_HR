package hu.cubix.hr.tomk99.service;

import hu.cubix.hr.tomk99.model.Employee;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("!smart")
public class DefaultEmployeeService implements EmployeeService{
    @Override
    public int getPayRaisePercent(Employee employee) {
        return 5;
    }
}
