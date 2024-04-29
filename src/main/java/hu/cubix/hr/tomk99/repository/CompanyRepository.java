package hu.cubix.hr.tomk99.repository;

import hu.cubix.hr.tomk99.model.AverageSalaryByPosition;
import hu.cubix.hr.tomk99.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    @Query("select distinct c from Company c join c.employees e where e.salary > :minSalary")
    List<Company> findByEmployeeWithGreaterThanMinSalary(int minSalary);

    @Query("select c from Company c where size(c.employees) > :maxHeadcount")
    List<Company> findByAboveMaxHeadcount(int maxHeadcount);

    @Query("select e.position.name as position, avg(e.salary) as averageSalary "
            + "from Company c "
            + "inner join c.employees e "
            + "where c.id = :companyId "
            + "group by e.position.name "
            + "order by averageSalary desc")
    List<AverageSalaryByPosition> findAverageSalariesByPosition(long companyId);
}
