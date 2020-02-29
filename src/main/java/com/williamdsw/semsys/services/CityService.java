package com.williamdsw.semsys.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.williamdsw.semsys.domain.City;
import com.williamdsw.semsys.repositories.CityRepository;

@Service
public class CityService 
{
	// FIELDS
	
	@Autowired private CityRepository cityRepository;
	@Autowired private StateService stateService;
	
	// HELPER FUNCTIONS
	
	public List<City> findByState (Integer stateId)
	{
		stateService.findById (stateId);
		return cityRepository.findByStateIdOrderByNameAsc (stateId);
	}
}