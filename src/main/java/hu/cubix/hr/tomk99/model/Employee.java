package hu.cubix.hr.tomk99.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
public class Employee {
    @Id
    @GeneratedValue
    private Long employeeId;
    private String name;
    private int salary;
    private LocalDateTime entryTime;
    private String username;
    private String password;
    @ManyToOne
    private Company company;
    @ManyToOne(cascade = CascadeType.ALL)
    private Position position;
    @OneToMany(mappedBy = "applicant")
    private List<TimeoffRequest> sentRequests;

    @OneToMany(mappedBy = "manager")
    private List<TimeoffRequest> receivedRequest;

    @ManyToOne
    private Employee manager;
    @OneToMany(mappedBy = "manager")
    private List<Employee> applicants;

    public Employee(Long employeeId, String name, Position position, int salary, LocalDateTime entryTime) {
        this.employeeId = employeeId;
        this.name = name;
        this.position = position;
        this.salary = salary;
        this.entryTime = entryTime;
    }

    public Employee() {

    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long id) {
        this.employeeId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(LocalDateTime entryTime) {
        this.entryTime = entryTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public List<TimeoffRequest> getSentRequests() {
        return sentRequests;
    }

    public void setSentRequests(List<TimeoffRequest> requests) {
        this.sentRequests = requests;
    }

    public List<TimeoffRequest> getReceivedRequest() {
        return receivedRequest;
    }

    public void setReceivedRequest(List<TimeoffRequest> receivedRequest) {
        this.receivedRequest = receivedRequest;
    }

    public Employee getManager() {
        return manager;
    }

    public void setManager(Employee manager) {
        this.manager = manager;
    }

    public List<Employee> getApplicants() {
        return applicants;
    }

    public void setApplicants(List<Employee> applicants) {
        this.applicants = applicants;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(employeeId, employee.employeeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId);
    }
}
