package hu.cubix.hr.tomk99.controller;

import hu.cubix.hr.tomk99.dto.EmployeeDto;
import hu.cubix.hr.tomk99.mapper.EmployeeMapper;
import hu.cubix.hr.tomk99.model.Employee;
import hu.cubix.hr.tomk99.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;
    @Autowired
    EmployeeMapper employeeMapper;
    @GetMapping
    public List<EmployeeDto> getAll() {
        return employeeMapper.employeesToDtos(employeeService.findAll());
    }

    @GetMapping("/{id}")
    public EmployeeDto getById(@PathVariable long id) {
        EmployeeDto employeeDto = employeeMapper.employeeToDto(employeeService.findById(id));
        if (employeeDto != null) return employeeDto;
        else throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public EmployeeDto createNew(@RequestBody @Valid EmployeeDto employeeDto) {
        Employee employee = employeeMapper.dtoToEmployee(employeeDto);
        Employee savedEmployee = employeeService.create(employee);

        if (savedEmployee == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        return employeeMapper.employeeToDto(savedEmployee);
    }

    @PutMapping("/{id}")
    public EmployeeDto modify(@PathVariable long id, @RequestBody @Valid EmployeeDto employeeDto) {
        employeeDto.setId(id);
        Employee updatedEmployee = employeeService.update(employeeMapper.dtoToEmployee(employeeDto));

        if (updatedEmployee == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        return employeeMapper.employeeToDto(updatedEmployee);
    }
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable long id) {
        employeeService.delete(id);
    }

    @GetMapping("/filterByMinSalary")
    public List<EmployeeDto> getAllEmployeesWithSalaryHigherThanN(@RequestParam int minSalary) {
        return employeeMapper.employeesToDtos(employeeService.filterByMinSalary(minSalary));
    }

    @PostMapping("/payraise")
    public int getPayRaisePercent(@RequestBody @Valid Employee employee) {
        return employeeService.getPayRaisePercent(employee);
    }

}
