package hu.cubix.hr.tomk99.controller;

import hu.cubix.hr.tomk99.dto.CompanyDto;
import hu.cubix.hr.tomk99.dto.EmployeeDto;
import hu.cubix.hr.tomk99.mapper.CompanyMapper;
import hu.cubix.hr.tomk99.model.Company;
import hu.cubix.hr.tomk99.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
        CompanyDto companyDto = companyMapper.companyToDto(companyService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
        if (!full) companyDto = createCompanyWithoutEmployees(companyDto);
        return companyDto;
    }

    @PostMapping
    public CompanyDto createNew(@RequestBody CompanyDto companyDto) {
        Company company = companyMapper.dtoToCompany(companyDto);
        Company savedCompany = companyService.create(company);

        if (savedCompany == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        return companyMapper.companyToDto(savedCompany);
    }

    @PutMapping("/{id}")
    public CompanyDto modify(@PathVariable long id, @RequestBody CompanyDto companyDto) {
        CompanyDto idModifiedCompanyDto = new CompanyDto(id, companyDto.registrationNumber(), companyDto.name(), companyDto.address(), companyDto.employees());
        Company company = companyMapper.dtoToCompany(idModifiedCompanyDto);
        Company updatedCompany = companyService.update(company);

        if (updatedCompany == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        return companyMapper.companyToDto(updatedCompany);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable long id) {
        companyService.delete(id);
    }

    @PostMapping("/{id}/employees")
    public CompanyDto addNewEmployee(@PathVariable long id, @RequestBody EmployeeDto employeeDto) {
        Company company = companyService.addNewEmployee(id, companyMapper.dtoToEmployee(employeeDto));
        if (company == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return companyMapper.companyToDto(company);
    }

    @DeleteMapping("/{id}/employees/{employeeId}")
    public void deleteEmployeeFromCompany(@PathVariable long id, @PathVariable long employeeId) {
        if (companyService.findById(id).isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        boolean foundIdMatch = companyService.deleteEmployeeFromCompany(id, employeeId);
        if (!foundIdMatch) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}/employees")
    public CompanyDto replaceEmployees(@PathVariable long id, @RequestBody List<EmployeeDto> newEmployees) {
        if (companyService.findById(id).isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return companyMapper.companyToDto(companyService.updateEmployees(id,companyMapper.dtosToEmployees(newEmployees)));
    }

    private static CompanyDto createCompanyWithoutEmployees(CompanyDto companyDto) {
        return new CompanyDto(companyDto.id(), companyDto.registrationNumber(), companyDto.name(), companyDto.address(), null);
    }
}
