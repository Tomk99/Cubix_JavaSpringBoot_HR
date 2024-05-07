package hu.cubix.hr.tomk99.repository;

import hu.cubix.hr.tomk99.model.RequestStatus;
import hu.cubix.hr.tomk99.model.TimeoffRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface TimeoffRequestRepository extends JpaRepository<TimeoffRequest, Long>, JpaSpecificationExecutor<TimeoffRequest> {
    List<TimeoffRequest> findAllByRequestStatusAndApplicantNameStartingWith(RequestStatus requestStatus, String namePrefix);
}
