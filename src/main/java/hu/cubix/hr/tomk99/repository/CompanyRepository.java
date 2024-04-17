package hu.cubix.hr.tomk99.repository;

import hu.cubix.hr.tomk99.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
