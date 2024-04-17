package hu.cubix.hr.tomk99;

import hu.cubix.hr.tomk99.model.Employee;
import hu.cubix.hr.tomk99.service.SalaryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;

@SpringBootApplication
public class HrApplication implements CommandLineRunner {

    private final SalaryService salaryService;

    public HrApplication(SalaryService salaryService) {
        this.salaryService = salaryService;
    }

    public static void main(String[] args) {
        SpringApplication.run(HrApplication.class, args);
    }

    @Override
    public void run(String... args) {
    }
}
