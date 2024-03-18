package hu.cubix.hr.tomk99.config;

import org.springframework.stereotype.Component;

import java.util.List;

@org.springframework.boot.context.properties.ConfigurationProperties(prefix = "hr")
@Component
public class ConfigurationProperties {
    public List<SalaryTier> salary;

    public List<SalaryTier> getSalary() {
        return salary;
    }

    public void setSalary(List<SalaryTier> salary) {
        this.salary = salary;
    }

    public static class SalaryTier {
        private String tier;
        private double yearLimit;
        private int raisePercent;

        public String getTier() {
            return tier;
        }

        public void setTier(String tier) {
            this.tier = tier;
        }

        public double getYearLimit() {
            return yearLimit;
        }

        public void setYearLimit(double yearLimit) {
            this.yearLimit = yearLimit;
        }

        public int getRaisePercent() {
            return raisePercent;
        }

        public void setRaisePercent(int raisePercent) {
            this.raisePercent = raisePercent;
        }
    }
}
