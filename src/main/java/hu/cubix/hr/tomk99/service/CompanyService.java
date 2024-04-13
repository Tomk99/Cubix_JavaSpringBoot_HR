package hu.cubix.hr.tomk99.service;

import hu.cubix.hr.tomk99.model.Company;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CompanyService {

    private Map<Long, Company> companies = new HashMap<>();

    public List<Company> findAll() {
        return companies.values().stream().toList();
    }
    public Company findById(long id) {
        return companies.get(id);
    }

    public Company create(Company company) {
        if (findById(company.getId()) != null) {
            return null;
        }
        return save(company);
    }
    public Company update(Company company) {
        if (findById(company.getId()) == null) {
            return null;
        }
        return save(company);
    }
    public Company save(Company company) {
        companies.put(company.getId(),company);
        return company;
    }
    public void delete(long id) {
        companies.remove(id);
    }
}
