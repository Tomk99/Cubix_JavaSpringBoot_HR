package hu.cubix.hr.tomk99.service;

import hu.cubix.hr.tomk99.config.HrConfigProperties;
import hu.cubix.hr.tomk99.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("!smart")
public class DefaultEmployeeService extends EmployeeService {

    @Autowired
    private HrConfigProperties config;

    @Override
    public int getPayRaisePercent(Employee employee) {
        return config.getSalary().getDef().getPercent();
    }
}
