package com.netum.osaamispankki.user.repository;

import com.netum.osaamispankki.user.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository <User, Long> {
    User findByUsername(String username);
    Optional<User> findById(Long id);
    boolean existsByUsername(String username);
}
