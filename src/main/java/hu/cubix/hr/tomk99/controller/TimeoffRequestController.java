package hu.cubix.hr.tomk99.controller;

import hu.cubix.hr.tomk99.dto.TimeoffRequestDto;
import hu.cubix.hr.tomk99.mapper.TimeoffRequestMapper;
import hu.cubix.hr.tomk99.model.RequestStatus;
import hu.cubix.hr.tomk99.service.TimeoffRequestService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<TimeoffRequestDto> findAll() {
        return timeoffRequestMapper.requestsToDtos(timeoffRequestService.getAll());
    }
    @PostMapping
    public TimeoffRequestDto createRequest(@RequestBody TimeoffRequestDto timeoffRequestDto) {
        return timeoffRequestMapper.requestToDto(timeoffRequestService.save(timeoffRequestMapper.dtoToRequest(timeoffRequestDto)));
    }
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable long id) {
        timeoffRequestService.deleteById(id);
    }

    @GetMapping("/findBySpecs")
    public List<TimeoffRequestDto> findBySpecs(
            @RequestParam(required = false) RequestStatus requestStatus,
            @RequestParam(required = false) String namePrefix,
            @RequestParam(required = false) LocalDateTime createTimeFrom,
            @RequestParam(required = false) LocalDateTime createTimeUntil,
            @RequestParam(required = false) LocalDate requestTimeFrom,
            @RequestParam(required = false) LocalDate requestTimeUntil
            ) {
        return timeoffRequestMapper.requestsToDtos(timeoffRequestService.findBySpecs(requestStatus,namePrefix,createTimeFrom,createTimeUntil,requestTimeFrom,requestTimeUntil));
    }
}
