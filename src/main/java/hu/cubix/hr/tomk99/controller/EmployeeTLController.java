package hu.cubix.hr.tomk99.controller;

import hu.cubix.hr.tomk99.model.Employee;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
public class EmployeeTLController {

    private List<Employee> employees = new ArrayList<>();
    {
        employees.add(new Employee(1L,"Kálmán Mixáth",10000, LocalDateTime.of(2021,3,5,8,0)));
        employees.add(new Employee(2L,"Elek Teszt",20000, LocalDateTime.of(2013,11,4,8,0)));
    }
    @GetMapping
    public String getAll(Map<String, Object> model) {
        model.put("employees",employees);
        model.put("newEmployee",new Employee());
        return "employees";
    }

    @PostMapping
    public String createNew(Employee newEmployee) {
        newEmployee.setEntryTime(LocalDateTime.now());
        employees.add(newEmployee);
        return "redirect:/";
    }
}
