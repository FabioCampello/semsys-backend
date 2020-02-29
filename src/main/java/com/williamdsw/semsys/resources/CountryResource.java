package com.williamdsw.semsys.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.williamdsw.semsys.domain.City;
import com.williamdsw.semsys.domain.Country;
import com.williamdsw.semsys.domain.State;
import com.williamdsw.semsys.domain.dto.CityDTO;
import com.williamdsw.semsys.domain.dto.CountryDTO;
import com.williamdsw.semsys.domain.dto.StateDTO;
import com.williamdsw.semsys.services.CityService;
import com.williamdsw.semsys.services.CountryService;
import com.williamdsw.semsys.services.StateService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping (path = "/v1/public/countries")
public class CountryResource 
{
	// FIELDS
	
	@Autowired private CountryService countryService;
	@Autowired private StateService stateService;
	@Autowired private CityService cityService;
	
	// ENDPOINTS
	
	@ApiOperation (value = "Find all countries", response = CountryDTO[].class)
	@GetMapping
	public ResponseEntity<List<CountryDTO>> findAll ()
	{
		List<Country> countries = countryService.findAll ();
		List<CountryDTO> listDto = countries.stream ().map (country -> new CountryDTO (country)).collect (Collectors.toList ()); 
		return ResponseEntity.ok ().body (listDto);
	}
	
	@ApiOperation (value = "Find all states by given country", response = StateDTO[].class)
	@GetMapping (path = "/{countryId}/states")
	public ResponseEntity<List<StateDTO>> findStates (@PathVariable Integer countryId)
	{
		List<State> states = stateService.findByCountry (countryId);
		List<StateDTO> listDto = states.stream ().map (state -> new StateDTO (state)).collect (Collectors.toList ());
		return ResponseEntity.ok ().body (listDto);
	}
	
	@ApiOperation (value = "Find all cities by given state and country", response = CityDTO[].class)
	@GetMapping (path = "/{countryId}/states/{stateId}/cities")
	public ResponseEntity<List<CityDTO>> findCities (@PathVariable Integer countryId, @PathVariable Integer stateId)
	{
		countryService.findById (countryId);
		List<City> cities = cityService.findByState (stateId);
		List<CityDTO> listDto = cities.stream ().map (city -> new CityDTO (city)).collect (Collectors.toList ());
		return ResponseEntity.ok ().body (listDto);
	}
}