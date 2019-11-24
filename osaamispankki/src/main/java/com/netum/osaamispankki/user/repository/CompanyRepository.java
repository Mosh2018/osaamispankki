package com.netum.osaamispankki.user.repository;

import com.netum.osaamispankki.user.domain.Company;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends CrudRepository<Company, Long> {
    Company findByBusinessId(String x);

    Company findByCompanyCode(String companyCode);
    Company findByCreatedBy(Long id);
    Boolean existsByBusinessId(String businessId);
    Boolean existsByCompanyName(String name);
    Company findByCompanyName(String name);
    List<Company> findAllByCompanyNameContaining(String name);
}
