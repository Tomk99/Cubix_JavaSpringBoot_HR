package hu.cubix.hr.tomk99;

import hu.cubix.hr.tomk99.service.InitDbService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HrApplication implements CommandLineRunner {

    InitDbService initDbService;

    public HrApplication(InitDbService initDbService) {
        this.initDbService = initDbService;
    }

    public static void main(String[] args) {
        SpringApplication.run(HrApplication.class, args);
    }

    @Override
    public void run(String... args) {
        initDbService.initDb();
    }
}
