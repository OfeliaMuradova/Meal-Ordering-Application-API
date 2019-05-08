package com.development.ordering.repository;

import com.development.ordering.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    public Company findCompaniesById(Long id);
}
