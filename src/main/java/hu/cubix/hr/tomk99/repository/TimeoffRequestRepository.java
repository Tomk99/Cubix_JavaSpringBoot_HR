package hu.cubix.hr.tomk99.repository;

import hu.cubix.hr.tomk99.model.TimeoffRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TimeoffRequestRepository extends JpaRepository<TimeoffRequest, Long>, JpaSpecificationExecutor<TimeoffRequest> {
}