package com.williamdsw.semsys.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.williamdsw.semsys.domain.State;
import com.williamdsw.semsys.repositories.StateRepository;
import com.williamdsw.semsys.services.exceptions.ObjectNotFoundException;

@Service
public class StateService 
{
	// FIELDS
	
	@Autowired private StateRepository stateRepository;
	@Autowired private CountryService countryService;
	
	// HELPER FUNCTIONS
	
	public State findById (Integer stateId)
	{
		Optional<State> state = stateRepository.findById (stateId);
		return state.orElseThrow (() -> new ObjectNotFoundException (String.format ("State not found for id : %s", stateId)));
	}
	
	public List<State> findByCountry (Integer countryId)
	{
		countryService.findById (countryId);
		return stateRepository.findByCountryIdOrderByNameAsc (countryId);
	}
}