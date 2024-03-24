package hu.cubix.hr.tomk99.config;

import org.springframework.stereotype.Component;

import java.util.TreeMap;

@org.springframework.boot.context.properties.ConfigurationProperties(prefix = "hr")
@Component
public class ConfigurationProperties {
    public SalaryTier salary;

    public SalaryTier getSalary() {
        return salary;
    }

    public void setSalary(SalaryTier salary) {
        this.salary = salary;
    }

    public static class SalaryTier {

        public Def def;
        public Smart smart;

        public Def getDef() {
            return def;
        }

        public void setDef(Def def) {
            this.def = def;
        }

        public Smart getSmart() {
            return smart;
        }

        public void setSmart(Smart smart) {
            this.smart = smart;
        }

        public static class Def {
            private int raisePercent;

            public int getRaisePercent() {
                return raisePercent;
            }

            public void setRaisePercent(int raisePercent) {
                this.raisePercent = raisePercent;
            }
        }
        public static class Smart {
            private TreeMap<Double, Integer> limits;

            public TreeMap<Double, Integer> getLimits() {
                return limits;
            }

            public void setLimits(TreeMap<Double, Integer> limits) {
                this.limits = limits;
            }
        }

    }
}
