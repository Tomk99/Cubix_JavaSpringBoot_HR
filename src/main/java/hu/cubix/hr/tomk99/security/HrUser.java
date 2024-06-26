package hu.cubix.hr.tomk99.security;

import hu.cubix.hr.tomk99.model.Employee;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class HrUser extends User {

    private final Employee employee;
    public HrUser(String username, String password, Collection<? extends GrantedAuthority> authorities, Employee employee) {
        super(username, password, authorities);
        this.employee = employee;
    }
    public Employee getEmployee() {
        return employee;
    }
}
