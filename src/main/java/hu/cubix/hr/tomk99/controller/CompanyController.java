package hu.cubix.hr.tomk99.controller;

import hu.cubix.hr.tomk99.dto.CompanyDto;
import hu.cubix.hr.tomk99.dto.EmployeeDto;
import hu.cubix.hr.tomk99.mapper.CompanyMapper;
import hu.cubix.hr.tomk99.model.AverageSalaryByPosition;
import hu.cubix.hr.tomk99.model.Company;
import hu.cubix.hr.tomk99.service.CompanyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    @Autowired
    CompanyService companyService;
    @Autowired
    CompanyMapper companyMapper;

    @GetMapping
    public List<CompanyDto> getAll(@RequestParam(required = false) Optional<Boolean> full) {
        List<Company> companies = companyService.findAll();
        return mapCompanies(full, companies);
    }

    @GetMapping("/{id}")
    public CompanyDto getById(@PathVariable long id, @RequestParam(required = false) boolean full) {
        Company company = companyService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!full) return companyMapper.companyToSummaryDto(company);
        return companyMapper.companyToDto(company);
    }

    @PostMapping
    public CompanyDto create(@RequestBody CompanyDto companyDto) {
        return companyMapper.companyToDto(companyService.save(companyMapper.dtoToCompany(companyDto)));
    }

    @PutMapping("/{id}")
    public CompanyDto modify(@PathVariable long id, @RequestBody CompanyDto companyDto) {
        CompanyDto idModifiedCompanyDto = new CompanyDto(id, companyDto.getRegistrationNumber(), companyDto.getName(), companyDto.getAddress(), companyDto.getEmployees());
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
    public CompanyDto addNewEmployee(@PathVariable long id, @RequestBody @Valid EmployeeDto employeeDto) {
        Company company = companyService.addNewEmployee(id, companyMapper.dtoToEmployee(employeeDto));
        return companyMapper.companyToDto(company);
    }

    @DeleteMapping("/{id}/employees/{employeeId}")
    public CompanyDto deleteEmployeeFromCompany(@PathVariable long id, @PathVariable long employeeId) {
        Company company = companyService.deleteEmployeeFromCompany(id, employeeId);
        return companyMapper.companyToDto(company);
    }

    @PutMapping("/{id}/employees")
    public CompanyDto replaceEmployees(@PathVariable long id, @RequestBody @Valid List<EmployeeDto> newEmployees) {
        return companyMapper.companyToDto(companyService.updateEmployees(id,companyMapper.dtosToEmployees(newEmployees)));
    }

    @GetMapping(params = "minSalary")
    public List<CompanyDto> findByEmployeeWithGreaterThanMinSalary(@RequestParam(required = false) int minSalary, @RequestParam Optional<Boolean> full) {
        List<Company> companies = companyService.findByEmployeeWithGreaterThanMinSalary(minSalary);
        return mapCompanies(full, companies);
    }

    @GetMapping(params = "maxHeadcount")
    public List<CompanyDto> findByAboveMaxHeadcount(@RequestParam(required = false) int maxHeadcount, @RequestParam Optional<Boolean> full) {
        List<Company> companies = companyService.findByAboveMaxHeadcount(maxHeadcount);
        return mapCompanies(full, companies);
    }

    @GetMapping("/{id}/orderBySalaries")
    public List<AverageSalaryByPosition> findAverageSalariesByPosition(@PathVariable long id) {
        return companyService.findAverageSalariesByPosition(id);
    }

    private static CompanyDto createCompanyWithoutEmployees(CompanyDto companyDto) {
        return new CompanyDto(companyDto.getId(), companyDto.getRegistrationNumber(), companyDto.getName(), companyDto.getAddress(), null);
    }

    private List<CompanyDto> mapCompanies(Optional<Boolean> full, List<Company> companies) {
        if (full.orElse(false)) return companyMapper.companiesToDtos(companies);
        else return companyMapper.companiesToSummaryDtos(companies);
    }
}
