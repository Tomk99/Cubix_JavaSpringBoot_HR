package hu.cubix.hr.tomk99.controller;

import hu.cubix.hr.tomk99.dto.CompanyDto;
import hu.cubix.hr.tomk99.dto.EmployeeDto;
import hu.cubix.hr.tomk99.repository.CompanyRepository;
import hu.cubix.hr.tomk99.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class CompanyControllerIT {

    private static final String BASE_URI = "/api/companies";

    @Autowired
    WebTestClient webTestClient;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    CompanyRepository companyRepository;

    @BeforeEach
    void initDb() {
        employeeRepository.deleteAllInBatch();
        companyRepository.deleteAllInBatch();
    }

    @Test
    void testAddNewEmployee() {
        CompanyDto companyDto = new CompanyDto(0L, 123123, "ABC", "ABC St. 3.", null);
        EmployeeDto employeeDto = new EmployeeDto(0L, "John Doe", "Developer", 40000, LocalDateTime.now());

        createCompany(companyDto).expectStatus().isOk();
        List<CompanyDto> responseBodyBefore = getAll();
        long companyId = Objects.requireNonNull(responseBodyBefore.stream().findFirst().orElse(null)).getId();

        addNewEmployee(employeeDto, companyId).expectStatus().isOk();
        List<CompanyDto> responseBodyAfter = getAll();
        assertThat(getEmployeeListSize(responseBodyAfter)).isEqualTo(getEmployeeListSize(responseBodyBefore)+1);
    }

    @Test
    void testAddNewEmployeeWithWrongData() {
        CompanyDto companyDto = new CompanyDto(0L, 123123, "ABC", "ABC St. 3.", null);
        EmployeeDto employeeDto = new EmployeeDto(0L, "John Doe", "Developer", 40000, LocalDateTime.now());
        EmployeeDto wrongEmployeeDto1 = new EmployeeDto(0L, "", "Developer", 40000, LocalDateTime.now());
        EmployeeDto wrongEmployeeDto2 = new EmployeeDto(0L, "John Doe", "", 40000, LocalDateTime.now());
        EmployeeDto wrongEmployeeDto3 = new EmployeeDto(0L, "John Doe", "Developer", -1, LocalDateTime.now());
        EmployeeDto wrongEmployeeDto4 = new EmployeeDto(0L, "John Doe", "Developer", 40000, LocalDateTime.of(99999,1,1,1,1,1));
        createCompany(companyDto).expectStatus().isOk();
        List<CompanyDto> responseBodyBefore = getAll();
        long companyId = Objects.requireNonNull(responseBodyBefore.stream().findFirst().orElse(null)).getId();

        addNewEmployee(employeeDto, companyId+1).expectStatus().isNotFound();
        addNewEmployee(wrongEmployeeDto1,companyId).expectStatus().isBadRequest();
        addNewEmployee(wrongEmployeeDto2,companyId).expectStatus().isBadRequest();
        addNewEmployee(wrongEmployeeDto3,companyId).expectStatus().isBadRequest();
        addNewEmployee(wrongEmployeeDto4,companyId).expectStatus().isBadRequest();
    }

    @Test
    void testReplaceEmployeesInACompany() {
        CompanyDto companyDto = new CompanyDto(0L, 123123, "ABC", "ABC St. 3.", null);
        List<EmployeeDto> employeeDtos = List.of(
                new EmployeeDto(0L, "John Doe", "Developer", 40000, LocalDateTime.now()),
                new EmployeeDto(0L, "Jane Doe", "Developer", 40000, LocalDateTime.now())
        );
        createCompany(companyDto).expectStatus().isOk();
        List<CompanyDto> responseBodyBefore = getAll();
        long companyId = Objects.requireNonNull(responseBodyBefore.stream().findFirst().orElse(null)).getId();

        replaceEmployeesInACompany(employeeDtos, companyId).expectStatus().isOk();
        List<CompanyDto> responseBodyAfter = getAll();
        assertThat(getEmployeeListSize(responseBodyAfter)).isEqualTo(employeeDtos.size());
    }

    @Test
    void testReplaceEmployeesInACompanyWithWrongData() {
        CompanyDto companyDto = new CompanyDto(0L, 123123, "ABC", "ABC St. 3.", null);
        List<EmployeeDto> employeeDtos = List.of(
                new EmployeeDto(0L, "John Doe", "Developer", 40000, LocalDateTime.now()),
                new EmployeeDto(0L, "Jane Doe", "Developer", 40000, LocalDateTime.now())
        );
        List<EmployeeDto> wrongEmployeeDtos1 = List.of(
                new EmployeeDto(0L, "", "Developer", 40000, LocalDateTime.now()),
                new EmployeeDto(0L, "Jane Doe", "Developer", 40000, LocalDateTime.now())
        );
        List<EmployeeDto> wrongEmployeeDtos2 = List.of(
                new EmployeeDto(0L, "John Doe", "Developer", 40000, LocalDateTime.now()),
                new EmployeeDto(0L, "Jane Doe", "", 40000, LocalDateTime.now())
        );
        createCompany(companyDto).expectStatus().isOk();
        List<CompanyDto> responseBodyBefore = getAll();
        long companyId = Objects.requireNonNull(responseBodyBefore.stream().findFirst().orElse(null)).getId();

        replaceEmployeesInACompany(employeeDtos, companyId+1).expectStatus().isNotFound();
        List<CompanyDto> responseBodyAfter = getAll();
        assertThat(getEmployeeListSize(responseBodyAfter)).isEqualTo(getEmployeeListSize(responseBodyBefore));

        replaceEmployeesInACompany(wrongEmployeeDtos1, companyId).expectStatus().isBadRequest();
        List<CompanyDto> responseBodyAfter1 = getAll();
        assertThat(getEmployeeListSize(responseBodyAfter1)).isEqualTo(getEmployeeListSize(responseBodyBefore));

        replaceEmployeesInACompany(wrongEmployeeDtos2, companyId).expectStatus().isBadRequest();
        List<CompanyDto> responseBodyAfter2 = getAll();
        assertThat(getEmployeeListSize(responseBodyAfter2)).isEqualTo(getEmployeeListSize(responseBodyBefore));
    }

    @Test
    void testDeleteEmployeeFromACompany() {
        CompanyDto companyDto = new CompanyDto(0L, 123123, "ABC", "ABC St. 3.", null);
        List<EmployeeDto> employeeDtos = List.of(
                new EmployeeDto(0L, "John Doe", "Developer", 40000, LocalDateTime.now()),
                new EmployeeDto(0L, "Jane Doe", "Developer", 40000, LocalDateTime.now())
        );
        createCompany(companyDto).expectStatus().isOk();
        List<CompanyDto> initResponseBody = getAll();
        long companyId = Objects.requireNonNull(initResponseBody.stream().findFirst().orElse(null)).getId();

        replaceEmployeesInACompany(employeeDtos, companyId).expectStatus().isOk();
        List<CompanyDto> responseBodyBefore = getAll();
        int employeeListSizeBefore = getEmployeeListSize(responseBodyBefore);

        webTestClient.delete().uri(BASE_URI+"/"+companyId+"/employees/"+employeeListSizeBefore).exchange();
        List<CompanyDto> responseBodyAfter = getAll();
        int employeeListSizeAfter = getEmployeeListSize(responseBodyAfter);
        assertThat(employeeListSizeAfter).isEqualTo(employeeListSizeBefore-1);

        EmployeeDto firstEmployee = Objects.requireNonNull(responseBodyBefore.get(0)).getEmployees().get(0);
        EmployeeDto nonDeletedEmployee = Objects.requireNonNull(responseBodyAfter.stream().findFirst().orElse(null)).getEmployees().stream().findFirst().orElse(null);
        assertThat(nonDeletedEmployee).isEqualTo(firstEmployee);
    }

    private static int getEmployeeListSize(List<CompanyDto> responseBodyBefore) {
        return Objects.requireNonNull(responseBodyBefore.stream().findFirst().orElse(null)).getEmployees().size();
    }

    private List<CompanyDto> getAll() {
        return webTestClient.get().uri(BASE_URI+"?full=true")
                .exchange()
                .expectBodyList(CompanyDto.class)
                .returnResult()
                .getResponseBody();
    }

    private WebTestClient.ResponseSpec createCompany(CompanyDto companyDto) {
        return webTestClient.post().uri(BASE_URI)
                .bodyValue(companyDto)
                .exchange();
    }

    private WebTestClient.ResponseSpec addNewEmployee(EmployeeDto newEmployeeDto, long companyId) {
        return webTestClient.post().uri(BASE_URI+"/"+ companyId +"/employees")
                .bodyValue(newEmployeeDto)
                .exchange();
    }

    private WebTestClient.ResponseSpec replaceEmployeesInACompany(List<EmployeeDto> employeeDtos, long companyId) {
        return webTestClient.put().uri(BASE_URI+"/"+ companyId +"/employees")
                .bodyValue(employeeDtos)
                .exchange();
    }
}
