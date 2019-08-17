package com.netum.osaamispankki.user.repository;

import com.netum.osaamispankki.user.domain.Education;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EducationRepository extends CrudRepository<Education, Long> {

    Optional<List<Education>> findAllByUserId(Long id);

}
