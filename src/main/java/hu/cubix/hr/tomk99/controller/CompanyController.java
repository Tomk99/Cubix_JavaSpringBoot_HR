package hu.cubix.hr.tomk99.controller;

import hu.cubix.hr.tomk99.dto.CompanyDto;
import hu.cubix.hr.tomk99.dto.EmployeeDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    public Map<Long, CompanyDto> companies = new HashMap<>();

    {
        companies.put(1L, new CompanyDto(1L,
                2323,
                "MZ/X",
                "Budapest, Fűzfa út 44.",
                List.of(new EmployeeDto(1L,"asd",123, LocalDateTime.of(2022,2,2,8,0)))
                ));
        companies.put(2L, new CompanyDto(2L,
                2323,
                "MZ/X",
                "Budapest, Fűzfa út 44.",
                List.of(new EmployeeDto(1L,"asd",123, LocalDateTime.of(2022,2,2,8,0)))
        ));
    }

    @GetMapping
    public List<CompanyDto> getAll(@RequestParam(required = false) boolean full) {
        if (full) return companies.values().stream().toList();
        else return companies.values().stream().map(v -> new CompanyDto(v.getId(),v.getRegistrationNumber(),v.getName(),v.getAddress())).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyDto> getById(@PathVariable long id, @RequestParam(required = false) boolean full) {
        CompanyDto companyDto = companies.get(id);
        if (!full) companyDto = new CompanyDto(companyDto.getId(), companyDto.getRegistrationNumber(), companyDto.getName(), companyDto.getAddress());
        if (companyDto != null) return ResponseEntity.ok(companyDto);
        else return ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}")
    public ResponseEntity<CompanyDto> createNew(@RequestBody CompanyDto companyDto) {
        if (companies.containsKey(companyDto.getId())) return ResponseEntity.notFound().build();
        companies.put(companyDto.getId(),companyDto);
        return ResponseEntity.ok(companyDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompanyDto> modify(@RequestBody CompanyDto companyDto, @PathVariable long id) {
        companyDto.setId(id);
        if (!companies.containsKey(id)) return ResponseEntity.notFound().build();
        companies.put(id, companyDto);
        return ResponseEntity.ok(companyDto);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable long id) {
        companies.remove(id);
    }
}
