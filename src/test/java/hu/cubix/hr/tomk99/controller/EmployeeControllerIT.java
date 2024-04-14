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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeControllerIT {

    private static final String BASE_URI = "/api/employees";

    @Autowired
    WebTestClient webTestClient;

    EmployeeDto basicEmployeeDto = new EmployeeDto(999, "John Doe", "Cashier", 30000, LocalDateTime.of(2020, 3, 3, 8, 0));

    @Test
    void testThatCreatedEmployeeIsListed() {
        List<EmployeeDto> employeeDtoListBefore = getAll();
        createEmployee(employeeDtoListBefore, basicEmployeeDto);
        List<EmployeeDto> employeeDtoListAfter = getAll();

        assertThat(employeeDtoListAfter.subList(0,employeeDtoListBefore.size())).containsExactlyElementsOf(employeeDtoListBefore);
        assertThat(employeeDtoListBefore.size()+1).isEqualTo(employeeDtoListAfter.size());
    }

    @Test
    void testThatEmployeeIsUpdated() {
        List<EmployeeDto> employeeDtoListBeforeUpdate = getAll();
        EmployeeDto newEmployee = new EmployeeDto(1, "Jane Doe", "Cashier", 987654321, LocalDateTime.of(2020, 3, 3, 9, 0));
        updateEmployee(newEmployee);
        List<EmployeeDto> employeeDtoListAfterUpdate = getAll();
        assertThat(employeeDtoListBeforeUpdate.size()).isEqualTo(employeeDtoListAfterUpdate.size());
        assertThat(employeeDtoListAfterUpdate.get((int) newEmployee.id()-1).salary()).isEqualTo(987654321);
    }


    /****************************Additional Methods******************************/
    private void createEmployee(List<EmployeeDto> employeeDtoList, EmployeeDto newEmployeeDto) {
        long newId = 999;
        if (employeeDtoList.size() > 0) {
            newId = employeeDtoList.get(employeeDtoList.size()-1).id() + 1;
        }
        webTestClient.post().uri(BASE_URI).bodyValue(new EmployeeDto(newId,newEmployeeDto.name(),newEmployeeDto.job(),newEmployeeDto.salary(),newEmployeeDto.entryTime())).exchange().expectStatus().isOk();
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
        employeeDtos.sort(Comparator.comparing(EmployeeDto::id));
        return employeeDtos;
    }

    private void updateEmployee(EmployeeDto newEmployee) {
        webTestClient.put().uri(BASE_URI).bodyValue(newEmployee).exchange().expectStatus().isOk();
    }
}
