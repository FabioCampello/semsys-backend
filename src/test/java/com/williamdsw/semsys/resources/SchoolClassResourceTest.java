package com.williamdsw.semsys.resources;

import java.net.URI;
import java.text.SimpleDateFormat;

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

import com.williamdsw.semsys.domain.dto.SchoolClassNewDTO;
import com.williamdsw.semsys.resources.utils.HeaderUtils;

@ExtendWith (SpringExtension.class)
@SpringBootTest (webEnvironment = WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
@ActiveProfiles (profiles = "test")
public class SchoolClassResourceTest extends GlobalResourceConfigureTest
{
	// FIELDS
	
	private final String FIND_ALL_CLASSES_BY_COURSE_URL = "/v1/public/courses/{courseId}/classes";
	private final String INSERT_URL = "/v1/admin/classes";
	private final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	
	// TESTS
	
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
	
	// --> INSERT_URL = "/v1/admin/classes"
	
	@Test
	public void createSchoolClassShouldPersistDataAndReturnStatusCode201 ()
	{
		try 
		{		
			SchoolClassNewDTO dto = new SchoolClassNewDTO();
			dto.setId(null);
			dto.setName("School Class Test");
			dto.setStart(FORMAT.parse("2018-01-01"));
			dto.setEnd(FORMAT.parse("2019-01-01"));
			dto.setCourseId(1);
			
			HttpEntity<SchoolClassNewDTO> entity = new HttpEntity<SchoolClassNewDTO>(dto, this.getAdminEntity().getHeaders());
			ResponseEntity<String> response = restTemplate.exchange(this.INSERT_URL, HttpMethod.POST, entity, String.class);
			Assertions.assertEquals(response.getStatusCodeValue(), HttpStatus.CREATED.value());
			System.out.println(response.getHeaders().getLocation());
		} 
		catch (Exception e) 
		{
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void createSchoolClassWhenUserHasRoleEmployeeShouldReturnStatusCode403 ()
	{
		try 
		{			
			SchoolClassNewDTO dto = new SchoolClassNewDTO();
			dto.setId(null);
			dto.setName("School Class Test");
			dto.setStart(FORMAT.parse("2018-01-01"));
			dto.setEnd(FORMAT.parse("2019-01-01"));
			dto.setCourseId(1);
			
			HttpEntity<SchoolClassNewDTO> entity = new HttpEntity<SchoolClassNewDTO>(dto, this.getEmployeeEntity().getHeaders());
			ResponseEntity<String> response = restTemplate.exchange(this.INSERT_URL, HttpMethod.POST, entity, String.class);
			Assertions.assertEquals(response.getStatusCodeValue(), HttpStatus.FORBIDDEN.value());
			System.out.println(response.getBody());
		} 
		catch (Exception e) 
		{
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void createSchoolClassWhenUserHasRoleStudentShouldReturnStatusCode403 ()
	{
		try 
		{		
			SchoolClassNewDTO dto = new SchoolClassNewDTO();
			dto.setId(null);
			dto.setName("School Class Test");
			dto.setStart(FORMAT.parse("2018-01-01"));
			dto.setEnd(FORMAT.parse("2019-01-01"));
			dto.setCourseId(1);
			
			HttpEntity<SchoolClassNewDTO> entity = new HttpEntity<SchoolClassNewDTO>(dto, this.getStudentEntity().getHeaders());
			ResponseEntity<String> response = restTemplate.exchange(this.INSERT_URL, HttpMethod.POST, entity, String.class);
			Assertions.assertEquals(response.getStatusCodeValue(), HttpStatus.FORBIDDEN.value());
			System.out.println(response.getBody());
		} 
		catch (Exception e) 
		{
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void createSchoolClassWhenUserIsInvalidShouldReturnStatusCode403 ()
	{
		try 
		{			
			SchoolClassNewDTO dto = new SchoolClassNewDTO();
			dto.setId(null);
			dto.setName("School Class Test");
			dto.setStart(FORMAT.parse("2018-01-01"));
			dto.setEnd(FORMAT.parse("2019-01-01"));
			dto.setCourseId(1);
			
			HttpEntity<SchoolClassNewDTO> entity = new HttpEntity<SchoolClassNewDTO>(dto, this.getWrongEntity().getHeaders());
			ResponseEntity<String> response = restTemplate.exchange(this.INSERT_URL, HttpMethod.POST, entity, String.class);
			Assertions.assertEquals(response.getStatusCodeValue(), HttpStatus.FORBIDDEN.value());
			System.out.println(response.getBody());
		} 
		catch (Exception e) 
		{
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void createSchoolClassWhenDataIsInvalidShouldReturnStatusCode422 ()
	{
		try 
		{
			SchoolClassNewDTO dto = new SchoolClassNewDTO();
			dto.setId(null);
			dto.setName(null);
			dto.setStart(null);
			dto.setEnd(null);
			dto.setCourseId(null);
			
			HttpEntity<SchoolClassNewDTO> entity = new HttpEntity<SchoolClassNewDTO>(dto, this.getAdminEntity().getHeaders());
			ResponseEntity<String> response = restTemplate.exchange(this.INSERT_URL, HttpMethod.POST, entity, String.class);
			Assertions.assertEquals(response.getStatusCodeValue(), HttpStatus.UNPROCESSABLE_ENTITY.value());
			System.out.println(response.getBody());
		} 
		catch (Exception e) 
		{
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void createSchoolClassWhenCourseIsInvalidShouldReturnStatusCode404 ()
	{
		try 
		{			
			SchoolClassNewDTO dto = new SchoolClassNewDTO();
			dto.setId(null);
			dto.setName("School Class Test");
			dto.setStart(FORMAT.parse("2018-01-01"));
			dto.setEnd(FORMAT.parse("2019-01-01"));
			dto.setCourseId(-1);
			
			HttpEntity<SchoolClassNewDTO> entity = new HttpEntity<SchoolClassNewDTO>(dto, this.getAdminEntity().getHeaders());
			ResponseEntity<String> response = restTemplate.exchange(this.INSERT_URL, HttpMethod.POST, entity, String.class);
			Assertions.assertEquals(response.getStatusCodeValue(), HttpStatus.NOT_FOUND.value());
			System.out.println(response.getBody());
		} 
		catch (Exception e) 
		{
			System.out.println(e.getMessage());
		}
	}
}