package hu.cubix.hr.tomk99.repository;

import hu.cubix.hr.tomk99.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findBySalaryGreaterThanEqual(int minSalary);
}
