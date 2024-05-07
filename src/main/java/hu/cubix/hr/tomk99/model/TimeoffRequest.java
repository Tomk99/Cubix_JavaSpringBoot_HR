package hu.cubix.hr.tomk99.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class TimeoffRequest {
    @Id
    @GeneratedValue
    private long id;
    @ManyToOne
    private Employee applicant;
    @ManyToOne
    private Employee manager;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDateTime requestCreateTime;
    private RequestStatus requestStatus;

    public TimeoffRequest() {

    }

    public TimeoffRequest(long id, Employee applicant, Employee manager, LocalDate startDate, LocalDate endDate, LocalDateTime requestCreateTime, RequestStatus requestStatus) {
        this.id = id;
        this.applicant = applicant;
        this.manager = manager;
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

    public Employee getApplicant() {
        return applicant;
    }

    public void setApplicant(Employee applicant) {
        this.applicant = applicant;
    }

    public Employee getManager() {
        return manager;
    }

    public void setManager(Employee manager) {
        this.manager = manager;
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
        TimeoffRequest that = (TimeoffRequest) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
