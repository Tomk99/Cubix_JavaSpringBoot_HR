package hu.cubix.hr.tomk99.service;

import hu.cubix.hr.tomk99.config.ConfigurationProperties;
import hu.cubix.hr.tomk99.model.Employee;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Profile("smart")
public class SmartEmployeeService implements EmployeeService{

    private final ConfigurationProperties config;

    public SmartEmployeeService(ConfigurationProperties config) {
        this.config = config;
    }

    @Override
    public int getPayRaisePercent(Employee employee) {
        LocalDateTime localDateTimeSinceEntry = LocalDateTime.now().minusYears(employee.getEntryTime().getYear()).minusDays(employee.getEntryTime().getDayOfYear());
        double yearSinceEntry = localDateTimeSinceEntry.getYear();
        double monthSinceEntry = localDateTimeSinceEntry.getMonthValue()/12.0;
        double timeSinceEntry = yearSinceEntry+monthSinceEntry;
        //System.out.println(timeSinceEntry);
        List<ConfigurationProperties.SalaryTier> salaryConfig = config.getSalary();
        int payRaisePercent = 0;

        for (int i = salaryConfig.size()-1; i >= 0; i--) {
            if (timeSinceEntry >= salaryConfig.get(i).getYearLimit()) {
                payRaisePercent = salaryConfig.get(i).getRaisePercent();
            }
        }
        return payRaisePercent;
    }
}
