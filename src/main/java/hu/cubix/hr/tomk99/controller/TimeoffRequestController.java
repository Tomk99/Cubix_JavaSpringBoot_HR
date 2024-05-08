package hu.cubix.hr.tomk99.controller;

import hu.cubix.hr.tomk99.dto.TimeoffRequestDto;
import hu.cubix.hr.tomk99.mapper.TimeoffRequestMapper;
import hu.cubix.hr.tomk99.model.RequestStatus;
import hu.cubix.hr.tomk99.model.TimeoffRequest;
import hu.cubix.hr.tomk99.service.TimeoffRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/requests")
public class TimeoffRequestController {

    @Autowired
    TimeoffRequestService timeoffRequestService;
    @Autowired
    TimeoffRequestMapper timeoffRequestMapper;

    @GetMapping
    public List<TimeoffRequestDto> findAll(@RequestParam(required = false) RequestStatus requestStatus,
                                           @RequestParam(required = false) String namePrefix,
                                           @RequestParam(required = false) LocalDateTime createTimeFrom,
                                           @RequestParam(required = false) LocalDateTime createTimeUntil,
                                           @RequestParam(required = false) LocalDate requestTimeFrom,
                                           @RequestParam(required = false) LocalDate requestTimeUntil,
                                           @SortDefault("id") Pageable pageable) {
        Page<TimeoffRequest> requestPage = timeoffRequestService.getAll(requestStatus, namePrefix, createTimeFrom, createTimeUntil, requestTimeFrom, requestTimeUntil, pageable);
        return timeoffRequestMapper.requestsToDtos(requestPage.getContent());
    }
    @PostMapping
    public TimeoffRequestDto createRequest(@RequestBody TimeoffRequestDto timeoffRequestDto) {
        return timeoffRequestMapper.requestToDto(timeoffRequestService.save(timeoffRequestMapper.dtoToRequest(timeoffRequestDto)));
    }
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable long id) {
        timeoffRequestService.deleteById(id);
    }
}
