package com.netum.osaamispankki.user.repository;

import com.netum.osaamispankki.user.domain.Profile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends CrudRepository<Profile, Long> {

    boolean existsByEmail(String email);
    boolean existsByPhoneNo(String phone);
}
