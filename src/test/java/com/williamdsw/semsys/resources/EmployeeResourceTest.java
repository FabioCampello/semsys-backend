package com.williamdsw.semsys.resources;

import java.net.URI;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.internal.util.collections.Sets;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.williamdsw.semsys.domain.dto.EmployeeDTO;
import com.williamdsw.semsys.domain.dto.EmployeeNewDTO;
import com.williamdsw.semsys.resources.utils.HeaderUtils;

@ExtendWith (SpringExtension.class)
@SpringBootTest (webEnvironment = WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
@ActiveProfiles (profiles = "test")
public class EmployeeResourceTest extends GlobalResourceConfigureTest
{
	// FIELDS
	
	private final String FIND_ALL_EMPLOYEES_URL = "/v1/protected/employees/";
	private final String FIND_EMPLOYEES_BY_NAME_PAGE_URL = "/v1/protected/employees/page";
	private final String FIND_EMPLOYEE_BY_EMAIL_URL = "/v1/admin/employees/email";
	private final String FIND_EMPLOYEE_BY_SSN_URL = "/v1/admin/employees/ssn";
	private final String INSERT_URL = "/v1/admin/employees/";
	private final String UPDATE_URL = "/v1/protected/employees/{id}/";
	
	// TESTS
	
	// --> FIND_ALL_EMPLOYEES_URL = "/v1/protected/employees/"
	
	@Test
	public void findAllEmployeesWhenUserHasRoleEmployeeShouldReturnStatusCode200 ()
	{
		ResponseEntity<String> response = restTemplate.exchange (FIND_ALL_EMPLOYEES_URL, HttpMethod.GET, this.getEmployeeEntity (), String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.OK.value ());
		System.out.println (response.getBody ());
	}
	
	@Test
	public void findAllEmployeesWhenUserHasRoleStudentShouldReturnStatusCode200 ()
	{
		ResponseEntity<String> response = restTemplate.exchange (FIND_ALL_EMPLOYEES_URL, HttpMethod.GET, this.getStudentEntity (), String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.OK.value ());
		System.out.println (response.getBody ());
	}
	
	@Test
	public void findAllEmployeesWhenUserIsInvalidShouldReturnStatusCode403 ()
	{
		ResponseEntity<String> response = restTemplate.exchange (FIND_ALL_EMPLOYEES_URL, HttpMethod.GET, this.getWrongEntity (), String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.FORBIDDEN.value ());
		System.out.println (response.getBody ());
	}

	// --> FIND_EMPLOYEES_BY_NAME_PAGE_URL = "/v1/admin/employees/page"
	
	@Test
	public void findEmployeesByNameWhenUserHasRoleEmployeeShouldReturnStatusCode200 ()
	{
		String[] names = { "name", "page", "size", "direction", "orderBy"};
		String[] values = { "An", "0", "10", "DESC", "name"};
		URI location = HeaderUtils.buildUriWithQueryParams (FIND_EMPLOYEES_BY_NAME_PAGE_URL, names, values);
		
		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.GET, this.getEmployeeEntity (), String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.OK.value ());
		System.out.println (response.getBody ());
	}
	
	@Test
	public void findEmployeesByNameWhenUserHasRoleStudentShouldReturnStatusCode403 ()
	{
		String[] names = { "name", "page", "size", "direction", "orderBy"};
		String[] values = { "An", "0", "10", "DESC", "name"};
		URI location = HeaderUtils.buildUriWithQueryParams (FIND_EMPLOYEES_BY_NAME_PAGE_URL, names, values);
		
		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.GET, this.getStudentEntity (), String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.FORBIDDEN.value ());
		System.out.println (response.getBody ());
	}
	
	@Test
	public void findEmployeesByNameWhenUserIsInvalidShouldReturnStatusCode403 ()
	{
		String[] names = { "name", "page", "size", "direction", "orderBy"};
		String[] values = { "An", "0", "10", "DESC", "name"};
		URI location = HeaderUtils.buildUriWithQueryParams (FIND_EMPLOYEES_BY_NAME_PAGE_URL, names, values);
		
		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.GET, this.getStudentEntity (), String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.FORBIDDEN.value ());
		System.out.println (response.getBody ());
	}

	// --> FIND_EMPLOYEE_BY_EMAIL_URL = "/v1/admin/employees/email"
	
	@Test
	public void findEmployeeByEmailWhenUserHasRoleAdminAndEmployeeExistsAndShouldReturnStatusCode200 ()
	{
		String[] names = { "value" };
		String[] values = { "iommi@email.com"};
		URI location = HeaderUtils.buildUriWithQueryParams (FIND_EMPLOYEE_BY_EMAIL_URL, names, values);
		
		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.GET, this.getAdminEntity (), String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.OK.value ());
		System.out.println (response.getBody ());
	}
	
	@Test
	public void findEmployeeByEmailWhenUserHasRoleEmployeeAndEmployeeExistsAndShouldReturnStatusCode403 ()
	{
		String[] names = { "value" };
		String[] values = { "iommi@email.com"};
		URI location = HeaderUtils.buildUriWithQueryParams (FIND_EMPLOYEE_BY_EMAIL_URL, names, values);
		
		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.GET, this.getEmployeeEntity (), String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.FORBIDDEN.value ());
		System.out.println (response.getBody ());
	}

	@Test
	public void findEmployeeByEmailWhenUserHasRoleStudentAndEmployeeExistsAndShouldReturnStatusCode403 ()
	{
		String[] names = { "value" };
		String[] values = { "iommi@email.com"};
		URI location = HeaderUtils.buildUriWithQueryParams (FIND_EMPLOYEE_BY_EMAIL_URL, names, values);
		
		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.GET, this.getStudentEntity (), String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.FORBIDDEN.value ());
		System.out.println (response.getBody ());
	}

	@Test
	public void findEmployeeByEmailWhenUserIsInvalidAndEmployeeExistsAndShouldReturnStatusCode403 ()
	{
		String[] names = { "value" };
		String[] values = { "iommi@email.com"};
		URI location = HeaderUtils.buildUriWithQueryParams (FIND_EMPLOYEE_BY_EMAIL_URL, names, values);
		
		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.GET, this.getWrongEntity (), String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.FORBIDDEN.value ());
		System.out.println (response.getBody ());
	}
	
	@Test
	public void findEmployeeByEmailWhenUserHasRoleAdminAndEmployeeDoesNotExistsShouldReturnStatusCode404 ()
	{
		String[] names = { "value" };
		String[] values = { "bowie@email.com"};
		URI location = HeaderUtils.buildUriWithQueryParams (FIND_EMPLOYEE_BY_EMAIL_URL, names, values);
		
		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.GET, this.getAdminEntity (), String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.NOT_FOUND.value ());
		System.out.println (response.getBody ());
	}

	// --> FIND_EMPLOYEE_BY_SSN_URL = "/v1/admin/employees/ssn"
	
	@Test
	public void findEmployeeBySsnWhenUserHasRoleAdminAndEmployeeExistsAndShouldReturnStatusCode200 ()
	{
		String[] names = { "value" };
		String[] values = { "554-90-1122"};
		URI location = HeaderUtils.buildUriWithQueryParams (FIND_EMPLOYEE_BY_SSN_URL, names, values);
		
		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.GET, this.getAdminEntity (), String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.OK.value ());
		System.out.println (response.getBody ());
	}
	
	@Test
	public void findEmployeeBySsnWhenUserHasRoleEmployeeAndEmployeeExistsAndShouldReturnStatusCode403 ()
	{
		String[] names = { "value" };
		String[] values = { "554-90-1122"};
		URI location = HeaderUtils.buildUriWithQueryParams (FIND_EMPLOYEE_BY_SSN_URL, names, values);
		
		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.GET, this.getEmployeeEntity (), String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.FORBIDDEN.value ());
		System.out.println (response.getBody ());
	}

	@Test
	public void findEmployeeBySsnWhenUserHasRoleStudentAndEmployeeExistsAndShouldReturnStatusCode403 ()
	{
		String[] names = { "value" };
		String[] values = { "554-90-1122"};
		URI location = HeaderUtils.buildUriWithQueryParams (FIND_EMPLOYEE_BY_SSN_URL, names, values);
		
		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.GET, this.getStudentEntity (), String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.FORBIDDEN.value ());
		System.out.println (response.getBody ());
	}

	@Test
	public void findEmployeeBySsnWhenUserIsInvalidAndEmployeeExistsAndShouldReturnStatusCode403 ()
	{
		String[] names = { "value" };
		String[] values = { "554-90-1122"};
		URI location = HeaderUtils.buildUriWithQueryParams (FIND_EMPLOYEE_BY_SSN_URL, names, values);
		
		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.GET, this.getWrongEntity (), String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.FORBIDDEN.value ());
		System.out.println (response.getBody ());
	}
	
	@Test
	public void findEmployeeBySsnWhenUserHasRoleAdminAndEmployeeDoesNotExistsShouldReturnStatusCode404 ()
	{
		String[] names = { "value" };
		String[] values = { "111-22-3333"};
		URI location = HeaderUtils.buildUriWithQueryParams (FIND_EMPLOYEE_BY_SSN_URL, names, values);
		
		ResponseEntity<String> entity = restTemplate.exchange (location, HttpMethod.GET, this.getAdminEntity (), String.class);
		Assertions.assertEquals (entity.getStatusCodeValue (), HttpStatus.NOT_FOUND.value ());
		System.out.println (entity.getBody ());
	}

	// --> INSERT_URL = "/v1/admin/employees/"
	
	@Test
	public void createEmployeeShouldPersistDataShouldReturnStatusCode201 ()
	{
		// Data
		EmployeeNewDTO dto = new EmployeeNewDTO ();
		dto.setName ("Mike Patton");
		dto.setEmail ("mike@email.com");
		dto.setSocialSecurityNumber ("595-48-6522");
		dto.setPassword ("123");
		dto.setPhoneNumbers (Sets.newSet ("239-200-8722", "239-204-9723"));
		dto.setStreet ("1769 Stanley Avenue");
		dto.setNumber ("887");
		dto.setComplement ("Last block");
		dto.setZipCode ("10011");
		dto.setCityId (9);
		
		HttpEntity<EmployeeNewDTO> entity = new HttpEntity<> (dto, this.getAdminEntity().getHeaders ()); 
		ResponseEntity<String> response = restTemplate.exchange (INSERT_URL, HttpMethod.POST, entity, String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.CREATED.value ());
		System.out.println (response.getHeaders ().getLocation ());
	}
	
	@Test
	public void createEmployeeWhenDataIsNullShouldReturnStatusCode422 ()
	{
		// Data
		EmployeeNewDTO dto = new EmployeeNewDTO ();		
		HttpEntity<EmployeeNewDTO> entity = new HttpEntity<> (dto, this.getAdminEntity().getHeaders ()); 
		ResponseEntity<String> response = restTemplate.exchange (INSERT_URL, HttpMethod.POST, entity, String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.UNPROCESSABLE_ENTITY.value ());
		System.out.println (response.getBody ());
	}

	// --> UPDATE_URL = "/v1/admin/employees/{id}/"
	
	@Test
	public void updateEmployeeShouldPersistDataShouldReturnStatusCode204 ()
	{
		String[] headers = { "id" };
		String[] values = { "1" };
		URI location = HeaderUtils.buildUriWithPathParams (UPDATE_URL, headers, values);
		
		// Data
		EmployeeDTO dto = new EmployeeDTO ();
		dto.setName ("Tony Iommi");
		dto.setEmail ("iommi_2@email.com");
		HttpEntity<EmployeeDTO> entity = new HttpEntity<> (dto, this.getAdminEntity ().getHeaders ());
		
		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.PUT, entity, String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.NO_CONTENT.value ());
		System.out.println (response);
	}
	
	@Test
	public void updateEmployeeWhenEmployeeIsDifferentFromUserShouldReturnStatusCode403 ()
	{
		String[] headers = { "id" };
		String[] values = { "5" };
		URI location = HeaderUtils.buildUriWithPathParams (UPDATE_URL, headers, values);
		
		// Data
		EmployeeDTO dto = new EmployeeDTO ();
		dto.setName ("Geezer Butler");
		dto.setEmail ("black_sabbath@email.com");
		HttpEntity<EmployeeDTO> entity = new HttpEntity<> (dto, this.getAdminEntity ().getHeaders ());
		
		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.PUT, entity, String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.FORBIDDEN.value ());
		System.out.println (response);
	}
	
	@Test
	public void updateEmployeeWhenDataIsNullShouldReturnStatusCode422 ()
	{
		String[] headers = { "id" };
		String[] values = { "5" };
		URI location = HeaderUtils.buildUriWithPathParams (UPDATE_URL, headers, values);
		
		// Data
		EmployeeDTO dto = new EmployeeDTO ();
		HttpEntity<EmployeeDTO> entity = new HttpEntity<> (dto, this.getAdminEntity().getHeaders ());
		
		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.PUT, entity, String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.UNPROCESSABLE_ENTITY.value ());
		System.out.println (response.getBody ());
	}
}