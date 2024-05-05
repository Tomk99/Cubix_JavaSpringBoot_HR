package hu.cubix.hr.tomk99.repository;

import hu.cubix.hr.tomk99.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {
    Page<Employee> findBySalaryGreaterThanEqual(int minSalary, Pageable pageable);
    List<Employee> findByPositionName(String jobTitle);
    List<Employee> findByNameStartingWithIgnoreCase(String namePrefix);

    List<Employee> findByEntryTimeBetween(LocalDateTime startDate, LocalDateTime endDate);

    @Modifying
    @Query("update Employee e set e.salary = :minSalary where e.position.name = :positionName and e.company.id = :companyId and e.salary < :minSalary")
    void updateSalaries(long companyId, String positionName, int minSalary);
}
