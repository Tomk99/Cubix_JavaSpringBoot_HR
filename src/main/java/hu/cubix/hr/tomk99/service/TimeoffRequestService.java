package hu.cubix.hr.tomk99.service;

import hu.cubix.hr.tomk99.model.*;
import hu.cubix.hr.tomk99.repository.TimeoffRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

import static hu.cubix.hr.tomk99.service.TimeoffRequestSpecifications.statusIsEqual;

@Service
public class TimeoffRequestService {

    @Autowired
    TimeoffRequestRepository timeoffRequestRepository;

    public List<TimeoffRequest> getAll() {
        return timeoffRequestRepository.findAll();
    }

    @Transactional
    public TimeoffRequest save(TimeoffRequest timeoffRequest) {
        timeoffRequest.setRequestCreateTime(LocalDateTime.now());
        return timeoffRequestRepository.save(timeoffRequest);
    }

    @Transactional
    public void deleteById(long id) {
        RequestStatus requestStatus = timeoffRequestRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)).getRequestStatus();
        if (requestStatus == RequestStatus.REQUESTED) deleteById(id);
        else throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    public List<TimeoffRequest> findRequestsByExample(TimeoffRequest timeoffRequest) {
        RequestStatus requestStatus = timeoffRequest.getRequestStatus();

        Specification<TimeoffRequest> specs = Specification.where(null);

        if (requestStatus != null) specs = specs.and(statusIsEqual(requestStatus));

        return timeoffRequestRepository.findAll(specs);
    }
}
