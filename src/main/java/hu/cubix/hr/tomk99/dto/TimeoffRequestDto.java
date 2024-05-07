package hu.cubix.hr.tomk99.dto;

import hu.cubix.hr.tomk99.model.RequestStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class TimeoffRequestDto {
    private long id;
    private String applicantName;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDateTime requestCreateTime;
    private RequestStatus requestStatus;

    public TimeoffRequestDto(long id, String applicantName, LocalDate startDate, LocalDate endDate, RequestStatus requestStatus) {
        this.id = id;
        this.applicantName = applicantName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.requestCreateTime = LocalDateTime.now();
        this.requestStatus = requestStatus;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
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
