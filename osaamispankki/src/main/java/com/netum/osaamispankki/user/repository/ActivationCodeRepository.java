package com.netum.osaamispankki.user.repository;

import com.netum.osaamispankki.user.domain.ActivationCode;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ActivationCodeRepository extends CrudRepository<ActivationCode, Long> {

    Iterable<ActivationCode> findAllByUsed(boolean used);
    ActivationCode findByName(String name);
}
