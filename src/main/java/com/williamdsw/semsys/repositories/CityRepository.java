package com.williamdsw.semsys.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.williamdsw.semsys.domain.City;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> 
{
	@Transactional (readOnly = true)
	public List<City> findByStateIdOrderByNameAsc (Integer stateId);
}