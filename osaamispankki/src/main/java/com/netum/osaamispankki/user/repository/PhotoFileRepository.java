package com.netum.osaamispankki.user.repository;

import com.netum.osaamispankki.user.domain.PhotoFile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PhotoFileRepository extends CrudRepository<PhotoFile, String> {

    PhotoFile findByUserId(Long userId);
    boolean existsByUserId(Long userId);

    @Transactional
    void deleteByUserId(Long userId);

}
