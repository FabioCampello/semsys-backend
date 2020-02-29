package com.williamdsw.semsys.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.williamdsw.semsys.domain.Country;
import com.williamdsw.semsys.repositories.CountryRepository;
import com.williamdsw.semsys.services.exceptions.ObjectNotFoundException;

@Service
public class CountryService 
{
	// FIELDS
	
	@Autowired private CountryRepository countryRepository;
	
	// HELPER FUNCTIONS
	
	public Country findById (Integer countryId)
	{
		Optional<Country> country = countryRepository.findById (countryId);
		return country.orElseThrow (() -> new ObjectNotFoundException (String.format ("Country not found for id : %s", countryId)));
	}
	
	public List<Country> findAll ()
	{
		return countryRepository.findAllByOrderByName ();
	}
}