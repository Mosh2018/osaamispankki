package com.netum.osaamispankki.user.repository;

import com.netum.osaamispankki.user.domain.TokenConfirmation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenConfirmationRepository extends CrudRepository<TokenConfirmation, Long> {
    TokenConfirmation findByToken(String token);
}
