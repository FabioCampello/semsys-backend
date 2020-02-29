package com.williamdsw.semsys.resources;

import java.net.URI;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.williamdsw.semsys.resources.utils.HeaderUtils;

@ExtendWith (SpringExtension.class)
@SpringBootTest (webEnvironment = WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
@ActiveProfiles (profiles = "test")
public class CountryResourceTest extends GlobalResourceConfigureTest
{
	// FIELDS
	
	private final String FIND_ALL_COUNTRIES_URL = "/v1/public/countries/";
	private final String FIND_ALL_STATES_URL = "/v1/public/countries/{countryId}/states";
	private final String FIND_ALL_CITIES_URL = "/v1/public/countries/{countryId}/states/{stateId}/cities";
	
	// TESTS
	
	// --> FIND_ALL_COUNTRIES_URL = "/v1/public/countries/"
	
	@Test
	public void findAllCountriesShouldReturnStatusCode200 ()
	{
		ResponseEntity<String> response = restTemplate.exchange (FIND_ALL_COUNTRIES_URL, HttpMethod.GET, null, String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.OK.value ());
		System.out.println (response.getBody ());
	}
	
	// --> FIND_ALL_STATES_URL = "/v1/public/countries/{countryId}/states"
	
	@Test
	public void findAllStatesByCountryShouldReturnStatusCode200 ()
	{
		String[] headers = { "countryId"};
		String[] values = { "1"};
		URI location = HeaderUtils.buildUriWithPathParams (FIND_ALL_STATES_URL, headers, values);
		
		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.GET, null, String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.OK.value ());
		System.out.println (response.getBody ());
	}
	
	@Test
	public void findAllStatesByCountryIdWhereCountryIsInvalidShouldReturnStatusCode404 ()
	{
		String[] headers = { "countryId"};
		String[] values = { "-1"};
		URI location = HeaderUtils.buildUriWithPathParams (FIND_ALL_STATES_URL, headers, values);
		
		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.GET, null, String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.NOT_FOUND.value ());
		System.out.println (response.getBody ());
	}
	
	// --> FIND_ALL_CITIES_URL = "/v1/public/countries/{countryId}/states/{stateId}/cities";
	
	@Test
	public void findAllCitiesByStateAndByCountryShouldReturnStatusCode200 ()
	{
		String[] headers = { "countryId", "stateId" };
		String[] values = { "1", "1"};
		URI location = HeaderUtils.buildUriWithPathParams (FIND_ALL_CITIES_URL, headers, values);
		
		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.GET, null, String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.OK.value ());
		System.out.println (response.getBody ());
	}
	
	@Test
	public void findAllCitiesByStateAndByCountryWhereCountryIsInvalidShouldReturnStatusCode404 ()
	{
		String[] headers = { "countryId", "stateId" };
		String[] values = { "-1", "1"};
		URI location = HeaderUtils.buildUriWithPathParams (FIND_ALL_CITIES_URL, headers, values);
		
		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.GET, null, String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.NOT_FOUND.value ());
		System.out.println (response.getBody ());
	}
	
	@Test
	public void findAllCitiesByStateAndByCountryWhereStateIsInvalidShouldReturnStatusCode404 ()
	{
		String[] headers = { "countryId", "stateId" };
		String[] values = { "1", "-1"};
		URI location = HeaderUtils.buildUriWithPathParams (FIND_ALL_CITIES_URL, headers, values);
		
		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.GET, null, String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.NOT_FOUND.value ());
		System.out.println (response.getBody ());
	}
}