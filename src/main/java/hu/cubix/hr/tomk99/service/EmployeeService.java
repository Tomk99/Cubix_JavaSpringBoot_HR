package hu.cubix.hr.tomk99.service;

import hu.cubix.hr.tomk99.dto.EmployeeDto;
import hu.cubix.hr.tomk99.model.Employee;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public abstract class EmployeeService {

    private Map<Long, Employee> employees = new HashMap<>();

    {
        employees.put(1L, new Employee(1L,"Elek Teszt","Java Backend Developer",10000, LocalDateTime.of(2021,3,5,8,0)));
        employees.put(2L, new Employee(2L,"Kálmán Mixáth","Frontend Developer",20000, LocalDateTime.of(2013,11,4,8,0)));
    }

    public List<Employee> findAll() {
        return employees.values().stream().toList();
    }
    public Employee findById(long id) {
        return employees.get(id);
    }

    public Employee create(Employee employee) {
        if (findById(employee.getId()) != null) {
            return null;
        }
        return save(employee);
    }
    public Employee update(Employee employee) {
        if (findById(employee.getId()) == null) {
            return null;
        }
        return save(employee);
    }
    public Employee save(Employee employee) {
        employees.put(employee.getId(),employee);
        return employee;
    }

    public void delete(long id) {
        employees.remove(id);
    }

    public List<Employee> filterByMinSalary(int minSalary) {
        return findAll().stream().filter(e -> e.getSalary() > minSalary).toList();
    }

    public abstract int getPayRaisePercent(Employee employee);
}
