package hu.cubix.hr.tomk99.controller;

import hu.cubix.hr.tomk99.dto.TimeoffRequestDto;
import hu.cubix.hr.tomk99.mapper.TimeoffRequestMapper;
import hu.cubix.hr.tomk99.service.TimeoffRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}
