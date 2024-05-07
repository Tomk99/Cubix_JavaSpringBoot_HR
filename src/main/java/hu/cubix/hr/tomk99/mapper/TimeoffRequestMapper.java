package hu.cubix.hr.tomk99.mapper;

import hu.cubix.hr.tomk99.dto.TimeoffRequestDto;
import hu.cubix.hr.tomk99.model.TimeoffRequest;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TimeoffRequestMapper{
    List<TimeoffRequestDto> requestsToDtos(List<TimeoffRequest> requests);

    List<TimeoffRequest> DtosToRequests(List<TimeoffRequestDto> requestDtos);

    @Mapping(target = "applicantId", source = "applicant.employeeId")
    @Mapping(target = "managerId", source = "manager.employeeId")
    TimeoffRequestDto requestToDto(TimeoffRequest request);

    @InheritInverseConfiguration
    TimeoffRequest dtoToRequest(TimeoffRequestDto requestDto);
}
