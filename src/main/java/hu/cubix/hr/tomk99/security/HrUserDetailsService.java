package hu.cubix.hr.tomk99.security;

import hu.cubix.hr.tomk99.model.Employee;
import hu.cubix.hr.tomk99.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HrUserDetailsService implements UserDetailsService {

    @Autowired
    EmployeeRepository employeeRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee employee = employeeRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        return new HrUser(username, employee.getPassword(), List.of(new SimpleGrantedAuthority("USER")),employee);
    }
}
