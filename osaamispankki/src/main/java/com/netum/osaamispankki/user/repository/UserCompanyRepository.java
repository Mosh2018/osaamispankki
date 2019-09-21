package com.netum.osaamispankki.user.repository;

import com.netum.osaamispankki.user.domain.User;
import com.netum.osaamispankki.user.domain.UserCompany;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

public interface UserCompanyRepository extends CrudRepository<UserCompany, Long> {

    Set<UserCompany> getAllByUser(User user);
    Set<UserCompany> getAllByCompanyCode(String companyCode);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "DELETE FROM user_company WHERE id = ?1", nativeQuery = true)
    void deleteById(Long id);

}
