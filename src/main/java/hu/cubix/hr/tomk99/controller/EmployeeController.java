package hu.cubix.hr.tomk99.controller;

import hu.cubix.hr.tomk99.dto.EmployeeDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final Map<Long, EmployeeDto> employees = new HashMap<>();

    {
        employees.put(1L, new EmployeeDto(1L,10000, LocalDateTime.of(2021,3,5,8,0)));
        employees.put(2L, new EmployeeDto(2L,20000, LocalDateTime.of(2013,11,4,8,0)));
    }
    @GetMapping
    public List<EmployeeDto> getAll() {
        return employees.values().stream().toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getById(@PathVariable long id) {
        EmployeeDto employeeDto = employees.get(id);
        if (employeeDto != null) return ResponseEntity.ok(employeeDto);
        else return ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}")
    public ResponseEntity<EmployeeDto> createNew(@RequestBody EmployeeDto employeeDto) {
        if (employees.containsKey(employeeDto.getId())) return ResponseEntity.notFound().build();
        employees.put(employeeDto.getId(), employeeDto);
        return ResponseEntity.ok(employeeDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDto> modify(@PathVariable long id, @RequestBody EmployeeDto employeeDto) {
        employeeDto.setId(id);
        if (!employees.containsKey(id)) {
            return ResponseEntity.notFound().build();
        } else {
            employees.put(id,employeeDto);
            return ResponseEntity.ok(employeeDto);
        }
    }
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable long id) {
        employees.remove(id);
    }

    @GetMapping("/filter")
    public List<EmployeeDto> getAllEmployeesWithSalaryHigherThanN(@RequestParam int minSalary) {
        return employees.values().stream().filter(employee -> employee.getSalary() > minSalary).toList();
    }
}
