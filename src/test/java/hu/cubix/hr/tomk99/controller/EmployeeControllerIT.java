package hu.cubix.hr.tomk99.controller;

import hu.cubix.hr.tomk99.dto.EmployeeDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.reactive.server.WebTestClient.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeControllerIT {

    private static final String BASE_URI = "/api/employees";

    @Autowired
    WebTestClient webTestClient;

    @Test
    void testThatNewValidEmployeeCanBeSaved() {
        List<EmployeeDto> employeeDtoListBefore = getAll();
        EmployeeDto newEmployeeDto = new EmployeeDto(0L, "Asd", "asd", 12233, LocalDateTime.of(2020, 3, 3, 8, 0, 0));
        saveEmployee(newEmployeeDto);
        List<EmployeeDto> employeeDtoListAfter = getAll();

        assertThat(employeeDtoListAfter.size()).isEqualTo(employeeDtoListBefore.size()+1);
        assertThat(employeeDtoListAfter.get(employeeDtoListAfter.size()-1)).usingRecursiveComparison().ignoringFields("id").isEqualTo(newEmployeeDto);
    }

    @Test
    void testSaveWithWrongData() {
        List<EmployeeDto> employeeDtoListBefore = getAll();
        EmployeeDto wrongEmployeeForm = new EmployeeDto(0L, "", "Cashier", 40000, LocalDateTime.of(2020, 3, 3, 8, 0));
        EmployeeDto wrongEmployeeForm2 = new EmployeeDto(0L, "John Doe", "", 40000, LocalDateTime.of(2020, 3, 3, 8, 0));
        saveEmployee(wrongEmployeeForm).expectStatus().isBadRequest();
        saveEmployee(wrongEmployeeForm2).expectStatus().isBadRequest();
        List<EmployeeDto> employeeDtoListAfter = getAll();
        assertThat(employeeDtoListAfter).hasSameSizeAs(employeeDtoListBefore);
    }

    @Test
    void testThatEmployeeIsUpdated() {
        EmployeeDto newEmployee = new EmployeeDto(0L,"Asd","asd",12345,LocalDateTime.of(2020,3,3,8,0,0));
        EmployeeDto savedEmployee = saveEmployee(newEmployee)
                .expectStatus()
                .isOk()
                .expectBody(EmployeeDto.class)
                .returnResult()
                .getResponseBody();
        List<EmployeeDto> employeeDtoListBefore = getAll();
        assert savedEmployee != null;
        savedEmployee.setName("modified");
        updateEmployee(savedEmployee).expectStatus().isOk();
        List<EmployeeDto> employeeDtoListAfter = getAll();

        assertThat(employeeDtoListAfter).hasSameSizeAs(employeeDtoListBefore);
        assertThat(employeeDtoListAfter.get(employeeDtoListAfter.size()-1)).usingRecursiveComparison().isEqualTo(savedEmployee);
    }

    @Test
    void testUpdateWithWrongData() {
        EmployeeDto newEmployee = new EmployeeDto(0L,"Asd","asd",12345,LocalDateTime.of(2020,3,3,8,0,0));
        EmployeeDto savedEmployee = saveEmployee(newEmployee)
                .expectStatus()
                .isOk()
                .expectBody(EmployeeDto.class)
                .returnResult()
                .getResponseBody();
        List<EmployeeDto> employeeDtoListBefore = getAll();
        EmployeeDto wrongEmployee = new EmployeeDto(0L, "", "asd", 40000, LocalDateTime.of(2020, 3, 3, 8, 0,0));
        assert savedEmployee != null;
        wrongEmployee.setId(savedEmployee.getId());
        updateEmployee(wrongEmployee).expectStatus().isBadRequest();
        List<EmployeeDto> employeeDtoListAfter = getAll();

        assertThat(employeeDtoListAfter).hasSameSizeAs(employeeDtoListBefore);
        assertThat(employeeDtoListAfter.get(employeeDtoListAfter.size()-1)).usingRecursiveComparison().isEqualTo(savedEmployee);
    }


    /****************************Additional Methods******************************/
    private ResponseSpec saveEmployee(EmployeeDto newEmployeeDto) {

        return webTestClient.post()
                .uri(BASE_URI)
                .bodyValue(newEmployeeDto)
                .exchange();
    }

    private List<EmployeeDto> getAll() {
        List<EmployeeDto> employeeDtos = webTestClient.get()
                .uri(BASE_URI)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(EmployeeDto.class)
                .returnResult()
                .getResponseBody();
        assert employeeDtos != null;
        employeeDtos.sort(Comparator.comparing(EmployeeDto::getId));
        return employeeDtos;
    }

    private ResponseSpec updateEmployee(EmployeeDto newEmployee) {
        return webTestClient.put()
                .uri(BASE_URI + "/" + newEmployee.getId())
                .bodyValue(newEmployee)
                .exchange();
    }
}
