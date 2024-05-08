package hu.cubix.hr.tomk99.repository;

import hu.cubix.hr.tomk99.model.RequestStatus;
import hu.cubix.hr.tomk99.model.TimeoffRequest;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public interface TimeoffRequestRepository extends JpaRepository<TimeoffRequest, Long>, JpaSpecificationExecutor<TimeoffRequest> {
    List<TimeoffRequest> findAllByRequestStatusAndApplicantNameStartingWith(RequestStatus requestStatus, String namePrefix);

    default Page<TimeoffRequest> findAllWithCriteriaBuilder(RequestStatus requestStatus, String namePrefix, LocalDateTime createTimeFrom, LocalDateTime createTimeUntil, LocalDate requestTimeFrom, LocalDate requestTimeUntil, Pageable pageable) {
        return findAll(buildSpecification(requestStatus, namePrefix, createTimeFrom, createTimeUntil, requestTimeFrom, requestTimeUntil), pageable);
    }

    static Specification<TimeoffRequest> buildSpecification(RequestStatus requestStatus, String namePrefix, LocalDateTime createTimeFrom, LocalDateTime createTimeUntil, LocalDate requestTimeFrom, LocalDate requestTimeUntil) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (requestStatus != null) {
                predicates.add(criteriaBuilder.equal(root.get("requestStatus"), requestStatus));
            }
            if (namePrefix != null) {
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.like(root.get("applicant").get("name"), namePrefix + "%"),
                        criteriaBuilder.like(root.get("manager").get("name"), namePrefix + "%")
                ));
            }
            if (createTimeFrom != null) {
                predicates.add(criteriaBuilder.greaterThan(root.get("requestCreateTime"), createTimeFrom));
            }
            if (createTimeUntil != null) {
                predicates.add(criteriaBuilder.lessThan(root.get("requestCreateTime"), createTimeUntil));
            }
            if (requestTimeFrom != null) {
                predicates.add(criteriaBuilder.greaterThan(root.get("endDate"), requestTimeFrom));
            }
            if (requestTimeUntil != null) {
                predicates.add(criteriaBuilder.lessThan(root.get("startDate"), requestTimeUntil));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
