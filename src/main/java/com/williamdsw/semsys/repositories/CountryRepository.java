package com.williamdsw.semsys.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.williamdsw.semsys.domain.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> 
{
	@Transactional (readOnly = true)
	public List<Country> findAllByOrderByName ();
}