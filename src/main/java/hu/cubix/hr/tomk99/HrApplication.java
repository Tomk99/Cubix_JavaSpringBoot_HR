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
        System.out.println(salaryService.getSalaryAfterRaise(new Employee(1L,100000,LocalDateTime.of(2010,4,1,8,0))));
        System.out.println(salaryService.getSalaryAfterRaise(new Employee(2L,100000,LocalDateTime.of(2021,9,3,8,0))));
        System.out.println(salaryService.getSalaryAfterRaise(new Employee(3L,100000,LocalDateTime.of(2018,11,2,8,0))));
    }
}
