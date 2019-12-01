package com.netum.osaamispankki.user.repository;

import com.netum.osaamispankki.user.domain.ActivationCode;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivationCodeRepository extends CrudRepository<ActivationCode, Long> {

    ActivationCode findByActivationCode(String code);
    List<ActivationCode> findAllByUsed(boolean used);
    List<ActivationCode> findAll();
}
