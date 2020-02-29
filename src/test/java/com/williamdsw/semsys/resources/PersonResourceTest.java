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
public class PersonResourceTest extends GlobalResourceConfigureTest
{
	// FIELDS
	
	private final String FIND_ALL_PERSONS_URL = "/v1/admin/persons/";
	private final String FIND_BY_ID = "/v1/protected/persons/{id}/";
	private final String DELETE_BY_ID = "/v1/admin/persons/{id}/";
	
	// TESTS
	
	// --> FIND_ALL_PERSONS_URL = "/v1/admin/persons/"
	
	@Test
	public void findAllPersonsWhenUserHasRoleAdminShouldReturnStatusCode200 ()
	{
		ResponseEntity<String> entity = restTemplate.exchange (FIND_ALL_PERSONS_URL, HttpMethod.GET, this.getAdminEntity (), String.class);
		Assertions.assertEquals (entity.getStatusCodeValue (), HttpStatus.OK.value ());
	}
	
	@Test
	public void findAllPersonsWhenUserHasRoleEmployeeShouldReturnStatusCode403 ()
	{
		ResponseEntity<String> entity = restTemplate.exchange (FIND_ALL_PERSONS_URL, HttpMethod.GET, this.getStudentEntity (), String.class);
		Assertions.assertEquals (entity.getStatusCodeValue (), HttpStatus.FORBIDDEN.value ());
	}
	
	@Test
	public void findAllPersonsWhenUserHasRoleStudentShouldReturnStatusCode403 ()
	{
		ResponseEntity<String> entity = restTemplate.exchange (FIND_ALL_PERSONS_URL, HttpMethod.GET, this.getStudentEntity (), String.class);
		Assertions.assertEquals (entity.getStatusCodeValue (), HttpStatus.FORBIDDEN.value ());
	}
	
	@Test
	public void findAllPersonsWhenTokenIsInvalidShouldReturnStatusCode403 ()
	{
		ResponseEntity<String> entity = restTemplate.exchange (FIND_ALL_PERSONS_URL, HttpMethod.GET, this.getWrongEntity (), String.class);
		Assertions.assertEquals (entity.getStatusCodeValue (), HttpStatus.FORBIDDEN.value ());
	}
	
	// --> FIND_BY_ID = "/v1/protected/persons/{id}/"
	
	@Test
	public void findPersonByIdWhenUserHasRoleAdminAndPersonExistsShouldReturnStatusCode200 ()
	{
		String[] headers = new String[] { "id" };
		String[] values = new String[] { "1" };
		URI location = HeaderUtils.buildUriWithPathParams (FIND_BY_ID, headers, values);
		
		ResponseEntity<String> entity = restTemplate.exchange (location, HttpMethod.GET, this.getAdminEntity (), String.class);
		Assertions.assertEquals (entity.getStatusCodeValue (), HttpStatus.OK.value ());
	}
	
	@Test
	public void findPersonByIdWhenUserHasRoleEmployeeAndPersonExistsShouldReturnStatusCode200 ()
	{
		String[] headers = new String[] { "id" };
		String[] values = new String[] { "1" };
		URI location = HeaderUtils.buildUriWithPathParams (FIND_BY_ID, headers, values);
		
		ResponseEntity<String> entity = restTemplate.exchange (location, HttpMethod.GET, this.getEmployeeEntity (), String.class);
		Assertions.assertEquals (entity.getStatusCodeValue (), HttpStatus.OK.value ());
	}
	
	@Test
	public void findStudentByIdWhenUserHasRoleStudentAndStudentExistsShouldReturnStatusCode200 ()
	{
		String[] headers = new String[] { "id" };
		String[] values = new String[] { "2" };
		URI location = HeaderUtils.buildUriWithPathParams (FIND_BY_ID, headers, values);
		
		ResponseEntity<String> entity = restTemplate.exchange (location, HttpMethod.GET, this.getStudentEntity (), String.class);
		Assertions.assertEquals (entity.getStatusCodeValue (), HttpStatus.OK.value ());
	}
	
	@Test
	public void findPersonByIdWhenTokenIsInvalidAndPersonExistsShouldReturnStatusCode403 ()
	{
		String[] headers = new String[] { "id" };
		String[] values = new String[] { "1" };
		URI location = HeaderUtils.buildUriWithPathParams (FIND_BY_ID, headers, values);
		
		ResponseEntity<String> entity = restTemplate.exchange (location, HttpMethod.GET, this.getWrongEntity (), String.class);
		Assertions.assertEquals (entity.getStatusCodeValue (), HttpStatus.FORBIDDEN.value ());
	}
	
	@Test
	public void findPersonByIdWhenUserHasRoleAdminAndPersonDoesNotExistsShouldReturnStatusCode404 ()
	{
		String[] headers = new String[] { "id" };
		String[] values = new String[] { "-1" };
		URI location = HeaderUtils.buildUriWithPathParams (FIND_BY_ID, headers, values);
		
		ResponseEntity<String> entity = restTemplate.exchange (location, HttpMethod.GET, this.getAdminEntity (), String.class);
		Assertions.assertEquals (entity.getStatusCodeValue (), HttpStatus.NOT_FOUND.value ());
	}
	
	@Test
	public void findPersonByIdWhenUserHasRoleEmployeeAndPersonDoesNotExistsShouldReturnStatusCode404 ()
	{
		String[] headers = new String[] { "id" };
		String[] values = new String[] { "-1" };
		URI location = HeaderUtils.buildUriWithPathParams (FIND_BY_ID, headers, values);
		
		ResponseEntity<String> entity = restTemplate.exchange (location, HttpMethod.GET, this.getEmployeeEntity (), String.class);
		Assertions.assertEquals (entity.getStatusCodeValue (), HttpStatus.NOT_FOUND.value ());
	}
	
	@Test
	public void findStudentByIdWhenUserHasRoleStudentAndStudentDoesNotExistsOrStudentIsDifferentShouldReturnStatusCode403 ()
	{
		String[] headers = new String[] { "id" };
		String[] values = new String[] { "-1" };
		URI location = HeaderUtils.buildUriWithPathParams (FIND_BY_ID, headers, values);
		
		ResponseEntity<String> entity = restTemplate.exchange (location, HttpMethod.GET, this.getStudentEntity (), String.class);
		Assertions.assertEquals (entity.getStatusCodeValue (), HttpStatus.FORBIDDEN.value ());
	}
	
	// --> DELETE_BY_ID = "/v1/admin/persons/{id}/"
	
	@Test
	public void deletePersonByIdWhenUserHasRoleAdminAndPersonExistsShouldReturnStatus204 ()
	{
		String[] headers = new String[] { "id" };
		String[] values = new String[] { "8" };
		URI location = HeaderUtils.buildUriWithPathParams (DELETE_BY_ID, headers, values);
		
		ResponseEntity<String> entity = restTemplate.exchange (location, HttpMethod.DELETE, this.getAdminEntity (), String.class);
		Assertions.assertEquals (entity.getStatusCodeValue(), HttpStatus.NO_CONTENT.value ());
	}
	
	@Test
	public void deletePersonByIdWhenUserHasRoleEmployeeAndPersonExistsShouldReturnStatus403 ()
	{
		String[] headers = new String[] { "id" };
		String[] values = new String[] { "8" };
		URI location = HeaderUtils.buildUriWithPathParams (DELETE_BY_ID, headers, values);
		
		ResponseEntity<String> entity = restTemplate.exchange (location, HttpMethod.DELETE, this.getEmployeeEntity (), String.class);
		Assertions.assertEquals (entity.getStatusCodeValue(), HttpStatus.FORBIDDEN.value ());
	}
	
	@Test
	public void deletePersonByIdWhenUserHasRoleStudentAndPersonExistsShouldReturnStatus403 ()
	{
		String[] headers = new String[] { "id" };
		String[] values = new String[] { "8" };
		URI location = HeaderUtils.buildUriWithPathParams (DELETE_BY_ID, headers, values);
		
		ResponseEntity<String> entity = restTemplate.exchange (location, HttpMethod.DELETE, this.getStudentEntity (), String.class);
		Assertions.assertEquals (entity.getStatusCodeValue(), HttpStatus.FORBIDDEN.value ());
	}
	
	@Test
	public void deletePersonByIdWhenUserHasRoleAdminAndPersonDoesNotExistsShouldReturnStatus404 ()
	{
		String[] headers = new String[] { "id" };
		String[] values = new String[] { "-1" };
		URI location = HeaderUtils.buildUriWithPathParams (DELETE_BY_ID, headers, values);
		
		ResponseEntity<String> entity = restTemplate.exchange (location, HttpMethod.DELETE, this.getAdminEntity (), String.class);
		Assertions.assertEquals (entity.getStatusCodeValue(), HttpStatus.NOT_FOUND.value ());
	}
}