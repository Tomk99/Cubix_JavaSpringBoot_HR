package hu.cubix.hr.tomk99.service;

import hu.cubix.hr.tomk99.model.RequestStatus;
import hu.cubix.hr.tomk99.model.TimeoffRequest;
import hu.cubix.hr.tomk99.repository.TimeoffRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TimeoffRequestService {

    @Autowired
    TimeoffRequestRepository timeoffRequestRepository;

    public List<TimeoffRequest> getAll() {
        return timeoffRequestRepository.findAll();
    }

    @Transactional
    public TimeoffRequest save(TimeoffRequest timeoffRequest) {
        return timeoffRequestRepository.save(timeoffRequest);
    }

    @Transactional
    public void deleteById(long id) {
        RequestStatus requestStatus = timeoffRequestRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)).getRequestStatus();
        if (requestStatus == RequestStatus.REQUESTED) deleteById(id);
        else throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
}
