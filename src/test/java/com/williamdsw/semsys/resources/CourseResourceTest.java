package com.williamdsw.semsys.resources;

import java.net.URI;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.williamdsw.semsys.domain.dto.CourseNewDTO;
import com.williamdsw.semsys.resources.utils.HeaderUtils;

@ExtendWith (SpringExtension.class)
@SpringBootTest (webEnvironment = WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
@ActiveProfiles (profiles = "test")
public class CourseResourceTest extends GlobalResourceConfigureTest
{
	// FIELDS
	
	private final String FIND_ALL_COURSES_URL = "/v1/public/courses/";
	private final String FIND_ALL_COURSES_BY_PERIOD_PAGE_URL = "/v1/protected/courses/period";
	private final String FIND_ALL_COURSES_BY_NAME_PAGE_URL = "/v1/protected/courses/name";
	private final String INSERT_URL = "/v1/admin/courses/";
	
	// TESTS
	
	// --> FIND_ALL_COURSES_URL = "/v1/public/courses/"
	
	@Test
	public void findAllCoursesShouldReturnStatusCode200 ()
	{
		ResponseEntity<String> response = restTemplate.exchange (FIND_ALL_COURSES_URL, HttpMethod.GET, null, String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.OK.value ());
		System.out.println (response.getBody ());
	}
	
	// --> FIND_ALL_COURSES_BY_PERIOD_PAGE_URL = "/protected/courses/period"

	@Test
	public void findAllCoursesByPeriodWhenUserHasRoleEmployeeShouldReturnStatusCode200 ()
	{
		String[] names = { "value"};
		String[] values = { "Afternoon"};
		URI location = HeaderUtils.buildUriWithQueryParams (FIND_ALL_COURSES_BY_PERIOD_PAGE_URL, names, values);
		
		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.GET, this.getEmployeeEntity (), String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.OK.value ());
		System.out.println (response.getBody ());
	}

	@Test
	public void findAllCoursesByPeriodWhenUserHasRoleStudentShouldReturnStatusCode403 ()
	{
		String[] names = { "value"};
		String[] values = { "Afternoon"};
		URI location = HeaderUtils.buildUriWithQueryParams (FIND_ALL_COURSES_BY_PERIOD_PAGE_URL, names, values);
		
		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.GET, this.getStudentEntity (), String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.FORBIDDEN.value ());
		System.out.println (response.getBody ());
	}	

	@Test
	public void findAllCoursesByPeriodWhenUserIsInvalidShouldReturnStatusCode403 ()
	{
		String[] names = { "value"};
		String[] values = { "Afternoon"};
		URI location = HeaderUtils.buildUriWithQueryParams (FIND_ALL_COURSES_BY_PERIOD_PAGE_URL, names, values);
		
		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.GET, this.getWrongEntity (), String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.FORBIDDEN.value ());
		System.out.println (response.getBody ());
	}	

	@Test
	public void findAllCoursesByPeriodWhenPeriodIsInvalidShouldReturnStatusCode400 ()
	{
		String[] names = { "value"};
		String[] values = { "Night"};
		URI location = HeaderUtils.buildUriWithQueryParams (FIND_ALL_COURSES_BY_PERIOD_PAGE_URL, names, values);
		
		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.GET, this.getEmployeeEntity (), String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.BAD_REQUEST.value ());
		System.out.println (response.getBody ());
	}
	
	// --> FIND_ALL_COURSES_BY_NAME_PAGE_URL = "/v1/protected/courses/name"

	@Test
	public void findAllCoursesByNameWhenUserHasRoleEmployeeShouldReturnStatusCode200 ()
	{
		String[] names = { "name"};
		String[] values = { "Inform"};
		URI location = HeaderUtils.buildUriWithQueryParams (FIND_ALL_COURSES_BY_NAME_PAGE_URL, names, values);
		
		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.GET, this.getEmployeeEntity (), String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.OK.value ());
		System.out.println (response.getBody ());
	}

	@Test
	public void findAllCoursesByNameWhenUserHasRoleStudentShouldReturnStatusCode403 ()
	{
		String[] names = { "name"};
		String[] values = { "Inform"};
		URI location = HeaderUtils.buildUriWithQueryParams (FIND_ALL_COURSES_BY_NAME_PAGE_URL, names, values);
		
		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.GET, this.getStudentEntity (), String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.FORBIDDEN.value ());
		System.out.println (response.getBody ());
	}	

	@Test
	public void findAllCoursesByNameWhenUserIsInvalidShouldReturnStatusCode403 ()
	{
		String[] names = { "name"};
		String[] values = { "Inform"};
		URI location = HeaderUtils.buildUriWithQueryParams (FIND_ALL_COURSES_BY_NAME_PAGE_URL, names, values);
		
		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.GET, this.getWrongEntity (), String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.FORBIDDEN.value ());
		System.out.println (response.getBody ());
	}
	
	

	
	// --> INSERT_URL = "/v1/admin/courses/"
	
	@Test
	public void createCourseShouldPersistDataAndReturnStatusCode201 ()
	{
		CourseNewDTO dto = new CourseNewDTO ();
		dto.setId (null);
		dto.setName ("Course Test");
		dto.setPeriod("Afternoon");
		dto.setType("Technical Course");
		
		HttpEntity<CourseNewDTO> entity = new HttpEntity<>(dto, this.getAdminEntity().getHeaders());
		ResponseEntity<String> response = restTemplate.exchange(this.INSERT_URL, HttpMethod.POST, entity, String.class);
		Assertions.assertEquals(response.getStatusCodeValue(), HttpStatus.CREATED.value());
		System.out.println(response.getHeaders().getLocation());
	}
	
	@Test
	public void createCourseWhenUserHasRoleEmployeeShouldReturnStatusCode403 ()
	{
		CourseNewDTO dto = new CourseNewDTO ();
		dto.setId (null);
		dto.setName ("Course Test");
		dto.setPeriod("Afternoon");
		dto.setType("Technical Course");
		
		HttpEntity<CourseNewDTO> entity = new HttpEntity<>(dto, this.getEmployeeEntity().getHeaders());
		ResponseEntity<String> response = restTemplate.exchange(this.INSERT_URL, HttpMethod.POST, entity, String.class);
		Assertions.assertEquals(response.getStatusCodeValue(), HttpStatus.FORBIDDEN.value());
		System.out.println(response.getBody());
	}
	
	@Test
	public void createCourseWhenUserHasRoleStudentShouldReturnStatusCode403 ()
	{
		CourseNewDTO dto = new CourseNewDTO ();
		dto.setId (null);
		dto.setName ("Course Test");
		dto.setPeriod("Afternoon");
		dto.setType("Technical Course");
		
		HttpEntity<CourseNewDTO> entity = new HttpEntity<>(dto, this.getStudentEntity().getHeaders());
		ResponseEntity<String> response = restTemplate.exchange(this.INSERT_URL, HttpMethod.POST, entity, String.class);
		Assertions.assertEquals(response.getStatusCodeValue(), HttpStatus.FORBIDDEN.value());
		System.out.println(response.getBody());
	}
	
	@Test
	public void createCourseWhenUserIsInvalidShouldReturnStatusCode403 ()
	{
		CourseNewDTO dto = new CourseNewDTO ();
		dto.setId (null);
		dto.setName ("Course Test");
		dto.setPeriod ("Afternoon");
		dto.setType ("Technical Course");
		
		HttpEntity<CourseNewDTO> entity = new HttpEntity<>(dto, this.getWrongEntity().getHeaders());
		ResponseEntity<String> response = restTemplate.exchange(this.INSERT_URL, HttpMethod.POST, entity, String.class);
		Assertions.assertEquals(response.getStatusCodeValue(), HttpStatus.FORBIDDEN.value());
		System.out.println(response.getBody());
	}
	
	@Test
	public void createCourseWhenDataIsInvalidShouldReturnStatus422 ()
	{
		CourseNewDTO dto = new CourseNewDTO ();
		dto.setId(null);
		dto.setName(null);
		dto.setPeriod(null);
		dto.setType(null);
		
		HttpEntity<CourseNewDTO> entity = new HttpEntity<>(dto, this.getAdminEntity().getHeaders());
		ResponseEntity<String> response = restTemplate.exchange(this.INSERT_URL, HttpMethod.POST, entity, String.class);
		Assertions.assertEquals(response.getStatusCodeValue(), HttpStatus.UNPROCESSABLE_ENTITY.value());
		System.out.println(response.getBody());
	}
}