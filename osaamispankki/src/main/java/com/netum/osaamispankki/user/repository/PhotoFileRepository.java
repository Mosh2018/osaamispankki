package com.netum.osaamispankki.user.repository;

import com.netum.osaamispankki.user.domain.PhotoFile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoFileRepository extends CrudRepository<PhotoFile, String> {

}
