package hu.cubix.hr.tomk99.service;

import hu.cubix.hr.tomk99.config.ConfigurationProperties;
import hu.cubix.hr.tomk99.model.Employee;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.TreeMap;

@Service
@Profile("smart")
public class SmartEmployeeService implements EmployeeService{

    private final ConfigurationProperties config;

    public SmartEmployeeService(ConfigurationProperties config) {
        this.config = config;
    }

    @Override
    public int getPayRaisePercent(Employee employee) {
        double timeSinceEntry = ChronoUnit.DAYS.between(employee.getEntryTime(), LocalDateTime.now()) / 365.0;
        ConfigurationProperties.SalaryTier.Smart smartConfig = config.getSalary().getSmart();
        TreeMap<Double, Integer> raiseIntervals = smartConfig.getLimits();

        Map.Entry<Double, Integer> entry = raiseIntervals.floorEntry(timeSinceEntry);
        return entry == null ? 0 : entry.getValue();
    }
}
