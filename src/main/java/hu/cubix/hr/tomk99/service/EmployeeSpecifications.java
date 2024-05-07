package hu.cubix.hr.tomk99.service;

import hu.cubix.hr.tomk99.model.Company_;
import hu.cubix.hr.tomk99.model.Employee;
import hu.cubix.hr.tomk99.model.Employee_;
import hu.cubix.hr.tomk99.model.Position_;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class EmployeeSpecifications {

    public static Specification<Employee> hasId(long id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Employee_.employeeId),id);
    }

    public static Specification<Employee> containsName(String namePrefix) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get(Employee_.name)),namePrefix.toLowerCase()+"%");
    }

    public static Specification<Employee> exactMatchPositionName(String positionName) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Employee_.position).get(Position_.name),positionName);
    }
    public static Specification<Employee> hasSalaryWithinRange(int salary) {
        int min = (int) (salary / 1.05);
        int max = (int) (salary / 0.95);
        return (root, query, criteriaBuilder) -> criteriaBuilder.between(root.get(Employee_.salary),min,max);
    }
    public static Specification<Employee> entryTimeWithinRange(LocalDateTime entryTime) {
        LocalDate entryDate = entryTime.toLocalDate();
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(criteriaBuilder.function("date",LocalDate.class,root.get(Employee_.entryTime)),entryDate);
    }
    public static Specification<Employee> companyNameStartsWith(String companyNamePrefix) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get(Employee_.company).get(Company_.name)),companyNamePrefix.toLowerCase()+"%");
    }
}
