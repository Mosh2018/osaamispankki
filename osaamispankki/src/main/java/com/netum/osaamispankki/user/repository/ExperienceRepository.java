package com.netum.osaamispankki.user.repository;

import com.netum.osaamispankki.user.domain.Experience;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExperienceRepository extends CrudRepository<Experience, Long> {

    Optional<List<Experience>> findAllByUserId(Long userId);
}
