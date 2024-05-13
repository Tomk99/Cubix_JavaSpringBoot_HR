package hu.cubix.hr.tomk99.service;

import hu.cubix.hr.tomk99.model.*;
import hu.cubix.hr.tomk99.repository.EmployeeRepository;
import hu.cubix.hr.tomk99.repository.TimeoffRequestRepository;
import hu.cubix.hr.tomk99.security.HrUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TimeoffRequestService {

    @Autowired
    TimeoffRequestRepository timeoffRequestRepository;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    EmployeeRepository employeeRepository;

    public Page<TimeoffRequest> getAll(RequestStatus requestStatus, String namePrefix, LocalDateTime createTimeFrom, LocalDateTime createTimeUntil, LocalDate requestTimeFrom, LocalDate requestTimeUntil, Pageable pageable) {
        return timeoffRequestRepository.findAllWithCriteriaBuilder(requestStatus, namePrefix, createTimeFrom, createTimeUntil, requestTimeFrom, requestTimeUntil, pageable);
    }
    @Transactional
    public TimeoffRequest save(TimeoffRequest timeoffRequest, long applicantId) {
        Employee applicant = employeeRepository.findById(applicantId).orElseThrow(IllegalArgumentException::new);
        timeoffRequest.setApplicant(applicant);
        timeoffRequest.setManager(applicant.getManager());
        timeoffRequest.setRequestCreateTime(LocalDateTime.now());
        return timeoffRequestRepository.save(timeoffRequest);
    }
    @Transactional
    public TimeoffRequest update(long requestId, TimeoffRequest timeoffRequest) {
        TimeoffRequest request = timeoffRequestRepository.findById(requestId).orElseThrow(IllegalArgumentException::new);
        request.setStartDate(timeoffRequest.getStartDate());
        request.setEndDate(timeoffRequest.getEndDate());
        request.setRequestCreateTime(LocalDateTime.now());
        return request;
    }

    @Transactional
    public TimeoffRequest acceptRequest(long requestId) {
        TimeoffRequest request = timeoffRequestRepository.findById(requestId).orElseThrow(IllegalArgumentException::new);
        if (!getCurrentHrUser().getEmployee().getEmployeeId().equals(request.getManager().getEmployeeId())) throw new AccessDeniedException("Your permission is not enough to accept this request");
        if (!request.getRequestStatus().equals(RequestStatus.REQUESTED)) throw new IllegalArgumentException("Request is already assessed");
        request.setRequestStatus(RequestStatus.ACCEPTED);
        return request;
    }

    @Transactional
    public TimeoffRequest rejectRequest(long requestId) {
        TimeoffRequest request = timeoffRequestRepository.findById(requestId).orElseThrow(IllegalArgumentException::new);
        if (!getCurrentHrUser().getEmployee().getEmployeeId().equals(request.getManager().getEmployeeId())) throw new AccessDeniedException("Your permission is not enough to accept this request");
        if (!request.getRequestStatus().equals(RequestStatus.REQUESTED)) throw new IllegalArgumentException("Request is already assessed");
        request.setRequestStatus(RequestStatus.REJECTED);
        return request;
    }

    @Transactional
    public void deleteById(long id) {
        TimeoffRequest timeoffRequest = timeoffRequestRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        if (timeoffRequest.getApplicant() == null) throw new NoSuchElementException("This request has no applicant yet");
        if (!timeoffRequest.getApplicant().getEmployeeId().equals(getCurrentHrUser().getEmployee().getEmployeeId())) throw new AccessDeniedException("Not allowed to delete request but your own");
        RequestStatus requestStatus = timeoffRequest.getRequestStatus();
        if (requestStatus != RequestStatus.REQUESTED) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        timeoffRequestRepository.deleteById(id);
    }

    public HrUser getCurrentHrUser() {
        return (HrUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
