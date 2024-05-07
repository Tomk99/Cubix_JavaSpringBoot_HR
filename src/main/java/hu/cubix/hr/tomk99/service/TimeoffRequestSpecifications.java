package hu.cubix.hr.tomk99.service;

import hu.cubix.hr.tomk99.model.RequestStatus;
import hu.cubix.hr.tomk99.model.TimeoffRequest;
import hu.cubix.hr.tomk99.model.TimeoffRequest_;
import org.springframework.data.jpa.domain.Specification;

public class TimeoffRequestSpecifications {
    public static Specification<TimeoffRequest> statusIsEqual(RequestStatus requestStatus) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(TimeoffRequest_.requestStatus),requestStatus);
    }
}
