package hu.cubix.hr.tomk99.repository;

import hu.cubix.hr.tomk99.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findBySalaryGreaterThanEqual(int minSalary);
    List<Employee> findByJob(String job);
    List<Employee> findByNameStartingWithIgnoreCase(String namePrefix);

    List<Employee> findByEntryTimeBetween(LocalDateTime startDate, LocalDateTime endDate);
}
