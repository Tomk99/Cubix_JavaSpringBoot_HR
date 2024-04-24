package hu.cubix.hr.tomk99;

import hu.cubix.hr.tomk99.model.Employee;
import hu.cubix.hr.tomk99.service.InitDbService;
import hu.cubix.hr.tomk99.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;

@SpringBootApplication
public class HrApplication implements CommandLineRunner {

    InitDbService initDbService;

    public HrApplication(SalaryService salaryService, InitDbService initDbService) {
        this.initDbService = initDbService;
    }

    public static void main(String[] args) {
        SpringApplication.run(HrApplication.class, args);
    }

    @Override
    public void run(String... args) {
        //initDbService.clearDB();
        //initDbService.insertTestData();
    }
}
