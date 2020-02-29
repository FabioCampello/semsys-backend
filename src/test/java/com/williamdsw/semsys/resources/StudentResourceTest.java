package com.williamdsw.semsys.resources;

import java.net.URI;
import java.sql.Date;

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
import com.williamdsw.semsys.domain.dto.StudentNewDTO;
import com.williamdsw.semsys.resources.utils.HeaderUtils;

@ExtendWith (SpringExtension.class)
@SpringBootTest (webEnvironment = WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
@ActiveProfiles (profiles = "test")
public class StudentResourceTest extends GlobalResourceConfigureTest
{
	// FIELDS
	
	private final String FIND_ALL_STUDENTS_URL = "/v1/protected/students/";
	private final String FIND_STUDENTS_BY_NAME_PAGE_URL = "/v1/protected/students/page";
	private final String FIND_STUDENTS_BY_EMAIL_URL = "/v1/protected/students/email";
	private final String FIND_STUDENTS_BY_SSN_URL = "/v1/protected/students/ssn";
	private final String INSERT_URL = "/v1/public/students/";
	private final String UPDATE_URL = "/v1/protected/students/{id}/";
	
	// TESTS
	
	// --> FIND_ALL_STUDENTS_URL = "/v1/protected/students/"
	
	@Test
	public void findAllStudentsWhenUserHasRoleEmployeeShouldReturnStatusCode200 ()
	{
		ResponseEntity<String> response = restTemplate.exchange (FIND_ALL_STUDENTS_URL, HttpMethod.GET, this.getEmployeeEntity (), String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.OK.value ());
		System.out.println (response.getBody ());
	}
	
	@Test
	public void findAllStudentsWhenUserHasRoleStudentShouldReturnStatusCode403 ()
	{
		ResponseEntity<String> response = restTemplate.exchange (FIND_ALL_STUDENTS_URL, HttpMethod.GET, this.getStudentEntity (), String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.FORBIDDEN.value ());
		System.out.println (response.getBody ());
	}
	
	@Test
	public void findAllStudentsWhenUserIsInvalidShouldReturnStatusCode403 ()
	{
		ResponseEntity<String> response = restTemplate.exchange (FIND_ALL_STUDENTS_URL, HttpMethod.GET, this.getWrongEntity (), String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.FORBIDDEN.value ());
		System.out.println (response.getBody ());
	}
	
	// --> FIND_STUDENTS_BY_NAME_PAGE_URL = "/v1/protected/students/page"
	
	@Test
	public void findStudentsByNameWhenUserHasRoleEmployeeShouldReturnStatusCode200 ()
	{
		String[] names = { "name", "page", "size", "direction", "orderBy"};
		String[] values = { "Lee", "0", "24", "DESC", "name"};
		URI location = HeaderUtils.buildUriWithQueryParams (FIND_STUDENTS_BY_NAME_PAGE_URL, names, values);
		
		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.GET, this.getEmployeeEntity (), String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.OK.value ());
		System.out.println (response.getBody ());
	}
	
	@Test
	public void findStudentsByNameWhenUserHasRoleStudentShouldReturnStatusCode403 ()
	{
		String[] names = { "name", "page", "size", "direction", "orderBy"};
		String[] values = { "Lee", "0", "24", "DESC", "name"};
		URI location = HeaderUtils.buildUriWithQueryParams (FIND_STUDENTS_BY_NAME_PAGE_URL, names, values);
		
		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.GET, this.getStudentEntity (), String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.FORBIDDEN.value ());
		System.out.println (response.getBody ());
	}
	
	@Test
	public void findStudentsByNameWhenUserIsInvalidShouldReturnStatusCode403 ()
	{
		String[] names = { "name", "page", "size", "direction", "orderBy"};
		String[] values = { "Lee", "0", "24", "DESC", "name"};
		URI location = HeaderUtils.buildUriWithQueryParams (FIND_STUDENTS_BY_NAME_PAGE_URL, names, values);
		
		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.GET, this.getStudentEntity (), String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.FORBIDDEN.value ());
		System.out.println (response.getBody ());
	}

	// --> FIND_STUDENTS_BY_EMAIL_URL = "/v1/protected/students/email"
	
	@Test
	public void findStudentByEmailWhenUserHasRoleEmployeeAndStudentExistsAndShouldReturnStatusCode200 ()
	{
		String[] names = { "value" };
		String[] values = { "neil@email.com"};
		URI location = HeaderUtils.buildUriWithQueryParams (FIND_STUDENTS_BY_EMAIL_URL, names, values);
		
		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.GET, this.getEmployeeEntity (), String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.OK.value ());
		System.out.println (response.getBody ());
	}

	@Test
	public void findStudentByEmailWhenUserHasRoleStudentAndStudentExistsAndShouldReturnStatusCode403 ()
	{
		String[] names = { "value" };
		String[] values = { "neil@email.com"};
		URI location = HeaderUtils.buildUriWithQueryParams (FIND_STUDENTS_BY_EMAIL_URL, names, values);
		
		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.GET, this.getStudentEntity (), String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.FORBIDDEN.value ());
		System.out.println (response.getBody ());
	}

	@Test
	public void findStudentByEmailWhenUserIsInvalidAndStudentExistsAndShouldReturnStatusCode403 ()
	{
		String[] names = { "value" };
		String[] values = { "neil@email.com"};
		URI location = HeaderUtils.buildUriWithQueryParams (FIND_STUDENTS_BY_EMAIL_URL, names, values);
		
		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.GET, this.getWrongEntity (), String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.FORBIDDEN.value ());
		System.out.println (response.getBody ());
	}

	@Test
	public void findStudentByEmailWhenUserHasRoleEmployeeAndStudentDoesNotExistsShouldReturnStatusCode404 ()
	{
		String[] names = { "value" };
		String[] values = { "hetfield@email.com"};
		URI location = HeaderUtils.buildUriWithQueryParams (FIND_STUDENTS_BY_EMAIL_URL, names, values);
		
		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.GET, this.getEmployeeEntity (), String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.NOT_FOUND.value ());
		System.out.println (response.getBody ());
	}

	// --> FIND_STUDENTS_BY_SSN_URL = "/v1/admin/employees/ssn"
	
	@Test
	public void findStudentBySsnWhenUserHasRoleEmployeeAndStudentExistsAndShouldReturnStatusCode200 ()
	{
		String[] names = { "value" };
		String[] values = { "097-10-6026"};
		URI location = HeaderUtils.buildUriWithQueryParams (FIND_STUDENTS_BY_SSN_URL, names, values);
		
		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.GET, this.getEmployeeEntity (), String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.OK.value ());
		System.out.println (response.getBody ());
	}

	@Test
	public void findStudentBySsnWhenUserHasRoleStudentAndStudentExistsAndShouldReturnStatusCode403 ()
	{
		String[] names = { "value" };
		String[] values = { "097-10-6026"};
		URI location = HeaderUtils.buildUriWithQueryParams (FIND_STUDENTS_BY_SSN_URL, names, values);
		
		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.GET, this.getStudentEntity (), String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.FORBIDDEN.value ());
		System.out.println (response.getBody ());
	}

	@Test
	public void findStudentBySsnWhenUserIsInvalidAndStudentExistsAndShouldReturnStatusCode403 ()
	{
		String[] names = { "value" };
		String[] values = { "097-10-6026"};
		URI location = HeaderUtils.buildUriWithQueryParams (FIND_STUDENTS_BY_SSN_URL, names, values);
		
		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.GET, this.getWrongEntity (), String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.FORBIDDEN.value ());
		System.out.println (response.getBody ());
	}
	
	@Test
	public void findStudentBySsnWhenUserHasRoleEmployeeAndStudentDoesNotExistsShouldReturnStatusCode404 ()
	{
		String[] names = { "value" };
		String[] values = { "111-22-3333"};
		URI location = HeaderUtils.buildUriWithQueryParams (FIND_STUDENTS_BY_SSN_URL, names, values);
		
		ResponseEntity<String> entity = restTemplate.exchange (location, HttpMethod.GET, this.getEmployeeEntity (), String.class);
		Assertions.assertEquals (entity.getStatusCodeValue (), HttpStatus.NOT_FOUND.value ());
		System.out.println (entity.getBody ());
	}

	// --> INSERT_URL = "/v1/public/students/"
	
	@Test
	public void createStudentShouldPersistDataShouldReturnStatusCode201 ()
	{
		// Data
		StudentNewDTO dto = new StudentNewDTO ();
		dto.setName ("Dave Mustaine");
		dto.setEmail ("dave@email.com");
		dto.setSocialSecurityNumber ("389-04-7016");
		dto.setPassword ("123123");
		dto.setBirthdate (Date.valueOf ("2012-04-23"));
		dto.setPhoneNumbers (Sets.newSet ("239-200-8722", "239-204-9723"));
		dto.setStreet ("1769 Stanley Avenue");
		dto.setNumber ("887");
		dto.setComplement ("Last block");
		dto.setZipCode ("10011");
		dto.setCityId (9);
		dto.setSchoolClassId (1);
		
		HttpEntity<StudentNewDTO> entity = new HttpEntity<> (dto); 
		ResponseEntity<String> response = restTemplate.exchange (INSERT_URL, HttpMethod.POST, entity, String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.CREATED.value ());
		System.out.println (response.getHeaders ().getLocation ());
	}

	@Test
	public void createStudentWhenDataIsNullShouldReturnStatusCode422 ()
	{
		// Data
		EmployeeNewDTO dto = new EmployeeNewDTO ();		
		HttpEntity<EmployeeNewDTO> entity = new HttpEntity<> (dto); 
		ResponseEntity<String> response = restTemplate.exchange (INSERT_URL, HttpMethod.POST, entity, String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.UNPROCESSABLE_ENTITY.value ());
		System.out.println (response.getBody ());
	}

	// --> UPDATE_URL = "/v1/protected/students/{id}/"
	
	@Test
	public void updateStudentShouldPersistDataWhenUserHasRoleStudentShouldReturnStatusCode204 ()
	{
		String[] headers = { "id" };
		String[] values = { "2" };
		URI location = HeaderUtils.buildUriWithPathParams (UPDATE_URL, headers, values);
		
		// Data
		EmployeeDTO dto = new EmployeeDTO ();
		dto.setName ("Geddy Lee");
		dto.setEmail ("geddy_lee@email.com");
		HttpEntity<EmployeeDTO> entity = new HttpEntity<> (dto, this.getStudentEntity ().getHeaders ());
		
		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.PUT, entity, String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.NO_CONTENT.value ());
		System.out.println (response);
	}
	
	@Test
	public void updateStudentWhenUserHasRoleEmployeeReturnStatusCode403 ()
	{
		String[] headers = { "id" };
		String[] values = { "2" };
		URI location = HeaderUtils.buildUriWithPathParams (UPDATE_URL, headers, values);
		
		// Data
		EmployeeDTO dto = new EmployeeDTO ();
		dto.setName ("Geddy Lee");
		dto.setEmail ("geddy_lee@email.com");
		HttpEntity<EmployeeDTO> entity = new HttpEntity<> (dto, this.getEmployeeEntity ().getHeaders ());
		
		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.PUT, entity, String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.FORBIDDEN.value ());
		System.out.println (response);
	}
	
	@Test
	public void updateStudentWhenStudentIsDifferentFromUserShouldReturnStatusCode403 ()
	{
		String[] headers = { "id" };
		String[] values = { "3" };
		URI location = HeaderUtils.buildUriWithPathParams (UPDATE_URL, headers, values);
		
		// Data
		EmployeeDTO dto = new EmployeeDTO ();
		dto.setName ("Alex Lifeson");
		dto.setEmail ("lifeson@email.com");
		HttpEntity<EmployeeDTO> entity = new HttpEntity<> (dto, this.getEmployeeEntity ().getHeaders ());
		
		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.PUT, entity, String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.FORBIDDEN.value ());
		System.out.println (response);
	}
	
	@Test
	public void updateStudentWhenDataIsNullShouldReturnStatusCode422 ()
	{
		String[] headers = { "id" };
		String[] values = { "2" };
		URI location = HeaderUtils.buildUriWithPathParams (UPDATE_URL, headers, values);
		
		// Data
		EmployeeDTO dto = new EmployeeDTO ();
		HttpEntity<EmployeeDTO> entity = new HttpEntity<> (dto, this.getStudentEntity ().getHeaders ());
		
		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.PUT, entity, String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.UNPROCESSABLE_ENTITY.value ());
		System.out.println (response.getBody ());
	}
}