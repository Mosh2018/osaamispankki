package com.netum.osaamispankki.user.repository;

import com.netum.osaamispankki.user.domain.CompanyConformation;
import org.springframework.data.repository.CrudRepository;

public interface CompanyConformationRepository extends CrudRepository<CompanyConformation, Long> {
    CompanyConformation findByCompanyId(Long id);
}
