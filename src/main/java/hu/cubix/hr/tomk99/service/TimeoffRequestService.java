package hu.cubix.hr.tomk99.service;

import hu.cubix.hr.tomk99.model.*;
import hu.cubix.hr.tomk99.repository.TimeoffRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TimeoffRequestService {

    @Autowired
    TimeoffRequestRepository timeoffRequestRepository;

    public Page<TimeoffRequest> getAll(RequestStatus requestStatus, String namePrefix, LocalDateTime createTimeFrom, LocalDateTime createTimeUntil, LocalDate requestTimeFrom, LocalDate requestTimeUntil, Pageable pageable) {
        return timeoffRequestRepository.findAllWithCriteriaBuilder(requestStatus, namePrefix, createTimeFrom, createTimeUntil, requestTimeFrom, requestTimeUntil, pageable);
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

    public List<TimeoffRequest> findBySpecs(RequestStatus requestStatus, String namePrefix, LocalDateTime createTimeFrom, LocalDateTime createTimeUntil, LocalDate requestTimeFrom, LocalDate requestTimeUntil) {
        return timeoffRequestRepository.findAllByRequestStatusAndApplicantNameStartingWith(requestStatus,namePrefix);
    }
}
