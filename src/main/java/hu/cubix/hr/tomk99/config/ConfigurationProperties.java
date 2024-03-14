package hu.cubix.hr.tomk99.config;

import org.springframework.stereotype.Component;
@org.springframework.boot.context.properties.ConfigurationProperties(prefix = "hr")
@Component
public class ConfigurationProperties {
    private Salary salary;

    public Salary getSalary() {
        return salary;
    }

    public void setSalary(Salary salary) {
        this.salary = salary;
    }

    public static class Salary {

        public ATier getaTier() {
            return aTier;
        }

        public void setaTier(ATier aTier) {
            this.aTier = aTier;
        }

        public BTier getbTier() {
            return bTier;
        }

        public void setbTier(BTier bTier) {
            this.bTier = bTier;
        }

        public CTier getcTier() {
            return cTier;
        }

        public void setcTier(CTier cTier) {
            this.cTier = cTier;
        }

        private ATier aTier;
        private BTier bTier;
        private CTier cTier;

        public static class ATier {
            private int yearLimit;
            private int raisePercent;

            public int getYearLimit() {
                return yearLimit;
            }

            public void setYearLimit(int yearLimit) {
                this.yearLimit = yearLimit;
            }

            public int getRaisePercent() {
                return raisePercent;
            }

            public void setRaisePercent(int raisePercent) {
                this.raisePercent = raisePercent;
            }
        }
        public static class BTier {
            private int yearLimit;
            private int raisePercent;

            public int getYearLimit() {
                return yearLimit;
            }

            public void setYearLimit(int yearLimit) {
                this.yearLimit = yearLimit;
            }

            public int getRaisePercent() {
                return raisePercent;
            }

            public void setRaisePercent(int raisePercent) {
                this.raisePercent = raisePercent;
            }
        }
        public static class CTier {
            private int yearLimit;
            private int raisePercent;

            public int getYearLimit() {
                return yearLimit;
            }

            public void setYearLimit(int yearLimit) {
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
}
