package com.development.ordering.service.admin;

import com.development.ordering.model.Company;
import com.development.ordering.model.Menu;
import com.development.ordering.repository.CompanyRepository;
import com.development.ordering.repository.MenuRepository;
import com.development.ordering.repository.WeekDaysRepository;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class CompaniesService {
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private WeekDaysRepository weekDaysRepository;

    @Autowired
    private MenuRepository menuRepository;

    public List<Company> getAllCompanies() {
        List<Company> companies = new ArrayList<>(companyRepository.findAll());
        return companies;
    }

    public Company getCompany(Long id) throws ResourceNotFoundException {
        try{
            Company company = companyRepository.findCompaniesById(id);
            return company;
        }
        catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException("Company not found for this id :: " + id);
        }
    }

    public Company addOrUpdateCompany(Company company) {
        return companyRepository.save(company);
    }

    public boolean deleteCompany(long id) throws ResourceNotFoundException {
        try{
            companyRepository.deleteById(id);
            return true;
        }catch (Exception e){
            throw e;
        }
    }
}
