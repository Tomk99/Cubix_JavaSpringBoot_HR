package hu.cubix.hr.tomk99.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import hu.cubix.hr.tomk99.config.HrConfigProperties;
import hu.cubix.hr.tomk99.model.Employee;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.auth0.jwt.JWTCreator.*;

@Service
public class JwtService {
    private static String ISSUER;
    private static Algorithm ALGORITHM; // = Algorithm.HMAC256("mysecret");

    private static final String AUTH = "auth";
    private static final String FULL_NAME = "fullName";
    private static final String ID = "id";
    private static final String USERNAME = "username";
    private static final String APPLICANTS = "applicants";
    private static final String MANAGER = "manager";

    @Autowired
    private HrConfigProperties config;
    @PostConstruct
    public void init() {
        HrConfigProperties.JwtData jwtData = config.getJwtData();
        ISSUER = config.getJwtData().getIssuer();
        try {
            Method method = Algorithm.class.getMethod(jwtData.getAlg(), String.class);
            ALGORITHM = (Algorithm) method.invoke(null, jwtData.getSecret());
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
    public String createJwt(UserDetails userDetails) {

        Employee employee = ((HrUser) userDetails).getEmployee();

        Builder jwtBuilder = JWT.create()
                .withSubject(userDetails.getUsername())
                .withArrayClaim(AUTH, userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toArray(String[]::new))
                .withClaim(FULL_NAME, employee.getName())
                .withClaim(ID, employee.getEmployeeId());
        Employee manager = employee.getManager();
        if (manager != null) {
            jwtBuilder.withClaim(MANAGER, createMapFromEmployee(manager));
        }
        List<Employee> applicants = employee.getApplicants();
        if (applicants != null && !applicants.isEmpty()) {
            jwtBuilder.withClaim(APPLICANTS, applicants.stream().map(this::createMapFromEmployee).toList());
        }

        return jwtBuilder
                .withExpiresAt(new Date(System.currentTimeMillis() + config.getJwtData().getDuration().toMillis()))
                .withIssuer(ISSUER)
                .sign(ALGORITHM);
    }

    private Map<String, Object> createMapFromEmployee(Employee employee) {
        return Map.of(
                ID,employee.getEmployeeId(),
                USERNAME,employee.getUsername()
                );
    }

    public UserDetails parseJwt(String jwtToken) {
        DecodedJWT decodedJwt = JWT.require(ALGORITHM)
                .withIssuer(ISSUER)
                .build()
                .verify(jwtToken);
        Employee employee = new Employee();
        employee.setEmployeeId(decodedJwt.getClaim(ID).asLong());
        employee.setUsername(decodedJwt.getSubject());
        employee.setName(decodedJwt.getClaim(FULL_NAME).asString());

        Claim managerClaim = decodedJwt.getClaim(MANAGER);
        Map<String, Object> managerData = managerClaim.asMap();
        employee.setManager(parseEmployeeFromMap(managerData));

        Claim applicantsClaim = decodedJwt.getClaim(APPLICANTS);
        List<HashMap> applicants = applicantsClaim.asList(HashMap.class);
        if (applicants != null) {
            employee.setApplicants(applicants.stream().map(this::parseEmployeeFromMap).toList());
        }

        return new HrUser(decodedJwt.getSubject(),"dummy",decodedJwt.getClaim("auth").asList(String.class).stream().map(SimpleGrantedAuthority::new).toList(), employee);
    }

    private Employee parseEmployeeFromMap(Map<String, Object> employeeMap) {
        if (employeeMap != null) {
            Employee employee = new Employee();
            employee.setEmployeeId(((Integer)employeeMap.get(ID)).longValue());
            employee.setUsername(employeeMap.get(USERNAME).toString());
            return employee;
        }
        return null;
    }
}
