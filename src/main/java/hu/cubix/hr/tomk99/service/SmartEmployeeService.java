package hu.cubix.hr.tomk99.service;

import hu.cubix.hr.tomk99.config.ConfigurationProperties;
import hu.cubix.hr.tomk99.model.Employee;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Profile("smart")
public class SmartEmployeeService implements EmployeeService{

    private final ConfigurationProperties config;

    public SmartEmployeeService(ConfigurationProperties config) {
        this.config = config;
    }

    @Override
    public int getPayRaisePercent(Employee employee) {
        int timeSinceEntry = LocalDateTime.now().getYear() - employee.getEntryTime().getYear();
        ConfigurationProperties.Salary configSalary = config.getSalary();
        if (timeSinceEntry > configSalary.getaTier().getYearLimit()) return configSalary.getaTier().getRaisePercent();
        else if (timeSinceEntry > configSalary.getbTier().getYearLimit()) return configSalary.getbTier().getRaisePercent();
        else if (timeSinceEntry > configSalary.getcTier().getYearLimit()) return configSalary.getcTier().getRaisePercent();
        else return 0;
    }
}
