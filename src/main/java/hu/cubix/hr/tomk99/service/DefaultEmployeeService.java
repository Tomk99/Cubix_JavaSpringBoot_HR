package hu.cubix.hr.tomk99.service;

import hu.cubix.hr.tomk99.config.ConfigurationProperties;
import hu.cubix.hr.tomk99.model.Employee;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("!smart")
public class DefaultEmployeeService extends EmployeeService {

    private final ConfigurationProperties config;

    public DefaultEmployeeService(ConfigurationProperties config) {
        this.config = config;
    }

    @Override
    public int getPayRaisePercent(Employee employee) {
        return config.getSalary().getDef().getRaisePercent();
    }
}
