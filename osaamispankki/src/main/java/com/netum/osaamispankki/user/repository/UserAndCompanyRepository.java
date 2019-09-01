package com.netum.osaamispankki.user.repository;

import com.netum.osaamispankki.user.domain.UserAndCompany;
import org.springframework.data.repository.CrudRepository;

public interface UserAndCompanyRepository extends CrudRepository<UserAndCompany, Long> {
    UserAndCompany findByCompanyId(Long id);
}
