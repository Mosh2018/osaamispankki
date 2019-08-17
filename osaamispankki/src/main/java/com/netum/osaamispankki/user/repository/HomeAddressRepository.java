package com.netum.osaamispankki.user.repository;

import com.netum.osaamispankki.user.domain.HomeAddress;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HomeAddressRepository extends CrudRepository<HomeAddress, Long> {
}
