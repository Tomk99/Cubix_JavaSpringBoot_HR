package hu.cubix.hr.tomk99.controller;

import hu.cubix.hr.tomk99.dto.CompanyDto;
import hu.cubix.hr.tomk99.dto.EmployeeDto;
import hu.cubix.hr.tomk99.mapper.CompanyMapper;
import hu.cubix.hr.tomk99.model.Company;
import hu.cubix.hr.tomk99.model.Employee;
import hu.cubix.hr.tomk99.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    public Map<Long, CompanyDto> companies = new HashMap<>();

    @Autowired
    CompanyService companyService;
    @Autowired
    CompanyMapper companyMapper;

    @GetMapping
    public List<CompanyDto> getAll(@RequestParam(required = false) boolean full) {
        if (full) return companyMapper.companiesToDtos(companyService.findAll());
        else return companyMapper.companiesToDtos(companyService.findAll()).stream().map(CompanyController::createCompanyWithoutEmployees).toList();
    }

    @GetMapping("/{id}")
    public CompanyDto getById(@PathVariable long id, @RequestParam(required = false) boolean full) {
        CompanyDto companyDto = companyMapper.companyToDto(companyService.findById(id));
        if (!full) companyDto = createCompanyWithoutEmployees(companyDto);
        if (companyDto != null) return companyDto;
        else throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public CompanyDto createNew(@RequestBody CompanyDto companyDto) {
        Company company = companyMapper.dtoToCompany(companyDto);
        Company savedCompany = companyService.create(company);

        if (savedCompany == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        return companyMapper.companyToDto(savedCompany);
    }

    @PutMapping
    public CompanyDto modify(@RequestBody CompanyDto companyDto) {
        Company company = companyMapper.dtoToCompany(companyDto);
        Company updatedCompany = companyService.update(company);

        if (updatedCompany == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        return companyMapper.companyToDto(updatedCompany);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable long id) {
        companyService.delete(id);
    }

    @PostMapping("/{id}/employees")
    public ResponseEntity<CompanyDto> addNewEmployee(@PathVariable long id, @RequestBody EmployeeDto employeeDto) {
        CompanyDto companyDto = companies.get(id);
        if (companyDto == null) {
            return ResponseEntity.notFound().build();
        }
        companyDto.employeeDtos().add(employeeDto);
        return ResponseEntity.ok(companyDto);
    }

    @DeleteMapping("/{id}/employees/{employeeId}")
    public ResponseEntity<CompanyDto> deleteEmployeeFromCompany(@PathVariable long id, @PathVariable long employeeId) {
        CompanyDto companyDto = companies.get(id);
        if (companyDto == null) {
            return ResponseEntity.notFound().build();
        }
        companyDto.employeeDtos().removeIf(e -> e.id() == employeeId);
        return ResponseEntity.ok(companies.get(id));
    }

    @PutMapping("/{id}/employees")
    public ResponseEntity<CompanyDto> replaceEmployees(@PathVariable long id, @RequestBody List<EmployeeDto> newEmployees) {
        CompanyDto companyDto = companies.get(id);
        if (companyDto == null) {
            return ResponseEntity.notFound().build();
        }
        //companyDto.setEmployees(newEmployees);
        return ResponseEntity.ok(companyDto);
    }

    private static CompanyDto createCompanyWithoutEmployees(CompanyDto companyDto) {
        return new CompanyDto(companyDto.id(), companyDto.registrationNumber(), companyDto.name(), companyDto.address(), null);
    }
}
