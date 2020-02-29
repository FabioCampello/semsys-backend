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
public class CourseResourceTest extends GlobalResourceConfigureTest
{
	// FIELDS
	
	private final String FIND_ALL_COURSES_URL = "/v1/public/courses/";
	private final String FIND_ALL_COURSES_BY_PERIOD_PAGE_URL = "/v1/protected/courses/period";
	private final String FIND_ALL_COURSES_BY_NAME_PAGE_URL = "/v1/protected/courses/name";
	private final String FIND_ALL_CLASSES_BY_COURSE_URL = "/v1/public/courses/{courseId}/classes";
	private final String FIND_ALL_CLASSES_BY_COURSE_AND_NAME_PAGE_URL = "/v1/protected/courses/{courseId}/classes/page";
	
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
		String[] names = { "value", "page", "size", "direction", "orderBy"};
		String[] values = { "Afternoon", "0", "24", "ASC", "id"};
		URI location = HeaderUtils.buildUriWithQueryParams (FIND_ALL_COURSES_BY_PERIOD_PAGE_URL, names, values);
		
		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.GET, this.getEmployeeEntity (), String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.OK.value ());
		System.out.println (response.getBody ());
	}

	@Test
	public void findAllCoursesByPeriodWhenUserHasRoleStudentShouldReturnStatusCode403 ()
	{
		String[] names = { "value", "page", "size", "direction", "orderBy"};
		String[] values = { "Afternoon", "0", "24", "ASC", "id"};
		URI location = HeaderUtils.buildUriWithQueryParams (FIND_ALL_COURSES_BY_PERIOD_PAGE_URL, names, values);
		
		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.GET, this.getStudentEntity (), String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.FORBIDDEN.value ());
		System.out.println (response.getBody ());
	}	

	@Test
	public void findAllCoursesByPeriodWhenUserIsInvalidShouldReturnStatusCode403 ()
	{
		String[] names = { "value", "page", "size", "direction", "orderBy"};
		String[] values = { "Afternoon", "0", "24", "ASC", "id"};
		URI location = HeaderUtils.buildUriWithQueryParams (FIND_ALL_COURSES_BY_PERIOD_PAGE_URL, names, values);
		
		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.GET, this.getWrongEntity (), String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.FORBIDDEN.value ());
		System.out.println (response.getBody ());
	}	

	@Test
	public void findAllCoursesByPeriodWhenPeriodIsInvalidShouldReturnStatusCode400 ()
	{
		String[] names = { "value", "page", "size", "direction", "orderBy"};
		String[] values = { "Night", "0", "24", "ASC", "id"};
		URI location = HeaderUtils.buildUriWithQueryParams (FIND_ALL_COURSES_BY_PERIOD_PAGE_URL, names, values);
		
		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.GET, this.getEmployeeEntity (), String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.BAD_REQUEST.value ());
		System.out.println (response.getBody ());
	}
	
	// --> FIND_ALL_COURSES_BY_NAME_PAGE_URL = "/v1/protected/courses/name"

	@Test
	public void findAllCoursesByNameWhenUserHasRoleEmployeeShouldReturnStatusCode200 ()
	{
		String[] names = { "name", "page", "size", "direction", "orderBy"};
		String[] values = { "Inform", "0", "24", "ASC", "name"};
		URI location = HeaderUtils.buildUriWithQueryParams (FIND_ALL_COURSES_BY_NAME_PAGE_URL, names, values);
		
		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.GET, this.getEmployeeEntity (), String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.OK.value ());
		System.out.println (response.getBody ());
	}

	@Test
	public void findAllCoursesByNameWhenUserHasRoleStudentShouldReturnStatusCode403 ()
	{
		String[] names = { "name", "page", "size", "direction", "orderBy"};
		String[] values = { "Inform", "0", "24", "ASC", "name"};
		URI location = HeaderUtils.buildUriWithQueryParams (FIND_ALL_COURSES_BY_NAME_PAGE_URL, names, values);
		
		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.GET, this.getStudentEntity (), String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.FORBIDDEN.value ());
		System.out.println (response.getBody ());
	}	

	@Test
	public void findAllCoursesByNameWhenUserIsInvalidShouldReturnStatusCode403 ()
	{
		String[] names = { "name", "page", "size", "direction", "orderBy"};
		String[] values = { "Inform", "0", "24", "ASC", "name"};
		URI location = HeaderUtils.buildUriWithQueryParams (FIND_ALL_COURSES_BY_NAME_PAGE_URL, names, values);
		
		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.GET, this.getWrongEntity (), String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.FORBIDDEN.value ());
		System.out.println (response.getBody ());
	}
	
	// --> FIND_ALL_CLASSES_BY_COURSE_URL = "/v1/public/courses/{courseId}/classes"

	@Test
	public void findAllClassesByCourseShouldReturnStatusCode200 ()
	{
		String[] headers = { "courseId" };
		String[] values = { "1" };
		URI location = HeaderUtils.buildUriWithPathParams (FIND_ALL_CLASSES_BY_COURSE_URL, headers, values);
		
		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.GET, null, String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.OK.value ());
		System.out.println (response.getBody ());
	}

	@Test
	public void findAllClassesByCourseWhenCourseIsInvalidShouldReturnStatusCode404 ()
	{
		String[] headers = { "courseId" };
		String[] values = { "-1" };
		URI location = HeaderUtils.buildUriWithPathParams (FIND_ALL_CLASSES_BY_COURSE_URL, headers, values);
		
		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.GET, null, String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.NOT_FOUND.value ());
		System.out.println (response.getBody ());
	}

	
	// --> FIND_ALL_CLASSES_BY_COURSE_AND_NAME_PAGE_URL = "/v1/protected/courses/{courseId}/classes/page"

	@Test
	public void findAllClassesByCourseAndNameWhenUserHasRoleEmployeeShouldReturnStatusCode200 ()
	{
		String[] pathHeaders = { "courseId" };
		String[] pathValues = { "1" };
		URI location = HeaderUtils.buildUriWithPathParams (FIND_ALL_CLASSES_BY_COURSE_AND_NAME_PAGE_URL, pathHeaders, pathValues);
		
		String[] requestParamNames = { "name", "page", "size", "direction", "orderBy"};
		String[] requestParamValues = { "Class", "0", "24", "ASC", "name"};
		location = HeaderUtils.buildUriWithQueryParams (location.toString (), requestParamNames, requestParamValues);
		
		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.GET, this.getEmployeeEntity (), String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.OK.value ());
		System.out.println (response.getBody ());
	}

	@Test
	public void findAllClassesByCourseAndNameWhenUserHasRoleStudentShouldReturnStatusCode403 ()
	{
		String[] pathHeaders = { "courseId" };
		String[] pathValues = { "1" };
		URI location = HeaderUtils.buildUriWithPathParams (FIND_ALL_CLASSES_BY_COURSE_AND_NAME_PAGE_URL, pathHeaders, pathValues);
		
		String[] requestParamNames = { "name", "page", "size", "direction", "orderBy"};
		String[] requestParamValues = { "Class", "0", "24", "ASC", "name"};
		location = HeaderUtils.buildUriWithQueryParams (location.toString (), requestParamNames, requestParamValues);
		
		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.GET, this.getStudentEntity (), String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.FORBIDDEN.value ());
		System.out.println (response.getBody ());
	}

	@Test
	public void findAllClassesByCourseAndNameWhenUserIsInvalidShouldReturnStatusCode403 ()
	{
		String[] pathHeaders = { "courseId" };
		String[] pathValues = { "1" };
		URI location = HeaderUtils.buildUriWithPathParams (FIND_ALL_CLASSES_BY_COURSE_AND_NAME_PAGE_URL, pathHeaders, pathValues);
		
		String[] requestParamNames = { "name", "page", "size", "direction", "orderBy"};
		String[] requestParamValues = { "Class", "0", "24", "ASC", "name"};
		location = HeaderUtils.buildUriWithQueryParams (location.toString (), requestParamNames, requestParamValues);
		
		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.GET, this.getWrongEntity (), String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.FORBIDDEN.value ());
		System.out.println (response.getBody ());
	}
	
	@Test
	public void findAllClassesByCourseAndNameWhenCourseIsInvalidReturnStatusCode404 ()
	{
		String[] pathHeaders = { "courseId" };
		String[] pathValues = { "-1" };
		URI location = HeaderUtils.buildUriWithPathParams (FIND_ALL_CLASSES_BY_COURSE_AND_NAME_PAGE_URL, pathHeaders, pathValues);
		
		String[] requestParamNames = { "name", "page", "size", "direction", "orderBy"};
		String[] requestParamValues = { "Class", "0", "24", "ASC", "name"};
		location = HeaderUtils.buildUriWithQueryParams (location.toString (), requestParamNames, requestParamValues);
		
		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.GET, this.getEmployeeEntity (), String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.NOT_FOUND.value ());
		System.out.println (response.getBody ());
	}
}