package hu.cubix.hr.tomk99.controller;

import hu.cubix.hr.tomk99.model.Employee;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
public class EmployeeTLController {

    private List<Employee> employees = new ArrayList<>();
    {
        employees.add(new Employee(1L,"Kálmán Mixáth","Writer",10000, LocalDateTime.of(2021,3,5,8,0)));
        employees.add(new Employee(2L,"Elek Teszt","Java Backend Developer",20000, LocalDateTime.of(2013,11,4,8,0)));
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

    @GetMapping("/deleteEmployee/{id}")
    public String deleteEmployee(@PathVariable long id) {
        employees.removeIf(e -> e.getId() == id);
        return "redirect:/";
    }

    @GetMapping("/modifyEmployee/{id}")
    public String getEmployeeById(@PathVariable long id, Map<String, Object> model) {
        model.put("employee",employees.stream().filter(e -> e.getId() == id).findFirst().orElseThrow());
        return "modify";
    }

    @PostMapping("/modifyEmployee")
    public String modifyEmployee(Employee employee) {
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getId().equals(employee.getId())) {
                employees.set(i,employee);
                break;
            }
        }
        return "redirect:/";
    }

}
