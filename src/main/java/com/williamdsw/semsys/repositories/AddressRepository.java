package com.williamdsw.semsys.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.williamdsw.semsys.domain.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {}