package hu.cubix.hr.tomk99.dto;

import hu.cubix.hr.tomk99.model.RequestStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class TimeoffRequestDto {
    private long id;
    private long applicantId;
    private long managerId;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDateTime requestCreateTime;
    private RequestStatus requestStatus;

    public TimeoffRequestDto(long id, long applicantId, long managerId, LocalDate startDate, LocalDate endDate, LocalDateTime requestCreateTime, RequestStatus requestStatus) {
        this.id = id;
        this.applicantId = applicantId;
        this.managerId = managerId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.requestCreateTime = requestCreateTime;
        this.requestStatus = requestStatus;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(long applicantId) {
        this.applicantId = applicantId;
    }

    public long getManagerId() {
        return managerId;
    }

    public void setManagerId(long managerId) {
        this.managerId = managerId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDateTime getRequestCreateTime() {
        return requestCreateTime;
    }

    public void setRequestCreateTime(LocalDateTime requestCreateTime) {
        this.requestCreateTime = requestCreateTime;
    }

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeoffRequestDto that = (TimeoffRequestDto) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
