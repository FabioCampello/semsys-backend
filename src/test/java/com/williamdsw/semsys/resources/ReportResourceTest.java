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

import com.williamdsw.semsys.domain.dto.MeetingScheduleDTO;
import com.williamdsw.semsys.domain.dto.ReportNewDTO;
import com.williamdsw.semsys.resources.utils.HeaderUtils;

@ExtendWith (SpringExtension.class)
@SpringBootTest (webEnvironment = WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
@ActiveProfiles (profiles = "test")
public class ReportResourceTest extends GlobalResourceConfigureTest
{
	// FIELDS

	private final String FIND_BY_ID_URL = "/v1/protected/reports/{id}/";
	private final String FIND_BY_SCHEDULE_URL = "/v1/protected/reports/schedule/{id}/";
	private final String FIND_BY_EMPLOYEE_URL = "/v1/protected/reports/employee/{id}/";
	private final String FIND_BY_STUDENT_URL = "/v1/protected/reports/student/{id}/";
	private final String INSERT_URL = "/v1/protected/reports/";

	// TESTS

	// --> FIND_BY_ID_URL = "/v1/protected/reports/{id}/";
	
	@Test
	public void findByIdWhenUserHasRoleEmployeeShouldReturnStatusCode200 ()
	{
		String[] headers = { "id" };
		String[] values = { "1" };
		URI location = HeaderUtils.buildUriWithPathParams (FIND_BY_ID_URL, headers, values);

		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.GET, this.getEmployeeEntity (), String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.OK.value ());
		System.out.println (response.getBody ());
	}

	@Test
	public void findByIdWhenUserHasRoleStudentShouldReturnStatusCode200 ()
	{
		String[] headers = { "id" };
		String[] values = { "1" };
		URI location = HeaderUtils.buildUriWithPathParams (FIND_BY_ID_URL, headers, values);

		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.GET, this.getStudentEntity (), String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.OK.value ());
		System.out.println (response.getBody ());
	}

	@Test
	public void findByIdWhenUserIsInvalidShouldReturnStatusCode403 ()
	{
		String[] headers = { "id" };
		String[] values = { "1" };
		URI location = HeaderUtils.buildUriWithPathParams (FIND_BY_ID_URL, headers, values);

		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.GET, this.getWrongEntity (), String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.FORBIDDEN.value ());
		System.out.println (response.getBody ());
	}

	@Test
	public void findByIdWhenReportDoesNotExistsShouldReturnStatusCode404 ()
	{
		String[] headers = { "id" };
		String[] values = { "-1" };
		URI location = HeaderUtils.buildUriWithPathParams (FIND_BY_ID_URL, headers, values);

		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.GET, this.getEmployeeEntity (), String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.NOT_FOUND.value ());
		System.out.println (response.getBody ());
	}

	// --> FIND_BY_SCHEDULE_URL = "/v1/protected/reports/schedule/{id}/";
	
	@Test
	public void findByScheduleWhenUserHasRoleEmployeeShouldReturnStatusCode200 ()
	{
		String[] headers = { "id" };
		String[] values = { "1" };
		URI location = HeaderUtils.buildUriWithPathParams (FIND_BY_SCHEDULE_URL, headers, values);

		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.GET, this.getEmployeeEntity (), String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.OK.value ());
		System.out.println (response.getBody ());
	}

	@Test
	public void findByScheduleWhenUserHasRoleStudentShouldReturnStatusCode200 ()
	{
		String[] headers = { "id" };
		String[] values = { "1" };
		URI location = HeaderUtils.buildUriWithPathParams (FIND_BY_SCHEDULE_URL, headers, values);

		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.GET, this.getStudentEntity (), String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.OK.value ());
		System.out.println (response.getBody ());
	}

	@Test
	public void findByScheduleWhenUserIsInvalidShouldReturnStatusCode403 ()
	{
		String[] headers = { "id" };
		String[] values = { "1" };
		URI location = HeaderUtils.buildUriWithPathParams (FIND_BY_SCHEDULE_URL, headers, values);

		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.GET, this.getWrongEntity (), String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.FORBIDDEN.value ());
		System.out.println (response.getBody ());
	}

	@Test
	public void findByScheduleWhenScheduleDoesNotExistsShouldReturnStatusCode404 ()
	{
		String[] headers = { "id" };
		String[] values = { "-1" };
		URI location = HeaderUtils.buildUriWithPathParams (FIND_BY_SCHEDULE_URL, headers, values);

		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.GET, this.getEmployeeEntity (), String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.NOT_FOUND.value ());
		System.out.println (response.getBody ());
	}
	
	// --> FIND_BY_EMPLOYEE_URL = "/v1/protected/reports/employee/{id}/"
	
	@Test
	public void findByEmployeePageWhenUserHasRoleEmployeeShouldReturnStatusCode200 ()
	{
		String[] pathHeaders = { "id" };
		String[] pathValues = { "1" };
		URI location = HeaderUtils.buildUriWithPathParams (FIND_BY_EMPLOYEE_URL, pathHeaders, pathValues);
		
		String[] queryNames = { "page", "size", "direction", "orderBy" };
		String[] queryValues = { "0", "24", "DESC", "emission" };
		location = HeaderUtils.buildUriWithQueryParams (location.toString (), queryNames, queryValues);
		
		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.GET, this.getEmployeeEntity (), String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.OK.value ());
		System.out.println (response.getBody ());
	}
	
	@Test
	public void findByEmployeePageWhenUserHasRoleStudentShouldReturnStatusCode403 ()
	{
		String[] pathHeaders = { "id" };
		String[] pathValues = { "1" };
		URI location = HeaderUtils.buildUriWithPathParams (FIND_BY_EMPLOYEE_URL, pathHeaders, pathValues);
		
		String[] queryNames = { "page", "size", "direction", "orderBy" };
		String[] queryValues = { "0", "24", "DESC", "emission" };
		location = HeaderUtils.buildUriWithQueryParams (location.toString (), queryNames, queryValues);
		
		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.GET, this.getStudentEntity (), String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.FORBIDDEN.value ());
		System.out.println (response.getBody ());
	}
	
	@Test
	public void findByEmployeePageWhenUserIsInvalidShouldReturnStatusCode403 ()
	{
		String[] pathHeaders = { "id" };
		String[] pathValues = { "1" };
		URI location = HeaderUtils.buildUriWithPathParams (FIND_BY_EMPLOYEE_URL, pathHeaders, pathValues);
		
		String[] queryNames = { "page", "size", "direction", "orderBy" };
		String[] queryValues = { "0", "24", "DESC", "emission" };
		location = HeaderUtils.buildUriWithQueryParams (location.toString (), queryNames, queryValues);
		
		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.GET, this.getWrongEntity (), String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.FORBIDDEN.value ());
		System.out.println (response.getBody ());
	}
	
	@Test
	public void findByEmployeeWhenEmployeeDoesNotExistsShouldReturnStatusCode404 ()
	{
		String[] pathHeaders = { "id" };
		String[] pathValues = { "-1" };
		URI location = HeaderUtils.buildUriWithPathParams (FIND_BY_EMPLOYEE_URL, pathHeaders, pathValues);
		
		String[] queryNames = { "page", "size", "direction", "orderBy" };
		String[] queryValues = { "0", "24", "DESC", "emission" };
		location = HeaderUtils.buildUriWithQueryParams (location.toString (), queryNames, queryValues);
		
		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.GET, this.getEmployeeEntity (), String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.NOT_FOUND.value ());
		System.out.println (response.getBody ());
	}
	
	// --> FIND_BY_STUDENT_URL = "/v1/protected/reports/student/{id}/"
	
	@Test
	public void findByStudentPageWhenUserHasRoleStudentShouldReturnStatusCode200 ()
	{
		String[] pathHeaders = { "id" };
		String[] pathValues = { "2" };
		URI location = HeaderUtils.buildUriWithPathParams (FIND_BY_STUDENT_URL, pathHeaders, pathValues);
		
		String[] queryNames = { "page", "size", "direction", "orderBy" };
		String[] queryValues = { "0", "24", "DESC", "emission" };
		location = HeaderUtils.buildUriWithQueryParams (location.toString (), queryNames, queryValues);
		
		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.GET, this.getStudentEntity (), String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.OK.value ());
		System.out.println (response.getBody ());
	}
	
	@Test
	public void findByStudentPageWhenUserHasRoleEmployeeShouldReturnStatusCode200 ()
	{
		String[] pathHeaders = { "id" };
		String[] pathValues = { "2" };
		URI location = HeaderUtils.buildUriWithPathParams (FIND_BY_STUDENT_URL, pathHeaders, pathValues);
		
		String[] queryNames = { "page", "size", "direction", "orderBy" };
		String[] queryValues = { "0", "24", "DESC", "emission" };
		location = HeaderUtils.buildUriWithQueryParams (location.toString (), queryNames, queryValues);
		
		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.GET, this.getEmployeeEntity (), String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.OK.value ());
		System.out.println (response.getBody ());
	}
	
	@Test
	public void findByStudentPageWhenUserIsInvalidShouldReturnStatusCode403 ()
	{
		String[] pathHeaders = { "id" };
		String[] pathValues = { "2" };
		URI location = HeaderUtils.buildUriWithPathParams (FIND_BY_STUDENT_URL, pathHeaders, pathValues);
		
		String[] queryNames = { "page", "size", "direction", "orderBy" };
		String[] queryValues = { "0", "24", "DESC", "emission" };
		location = HeaderUtils.buildUriWithQueryParams (location.toString (), queryNames, queryValues);
		
		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.GET, this.getWrongEntity (), String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.FORBIDDEN.value ());
		System.out.println (response.getBody ());
	}
	
	@Test
	public void findByStudentWhenStudentDoesNotExistsOrStudentIsDifferentShouldReturnStatusCode403 ()
	{
		String[] pathHeaders = { "id" };
		String[] pathValues = { "-2" };
		URI location = HeaderUtils.buildUriWithPathParams (FIND_BY_STUDENT_URL, pathHeaders, pathValues);
		
		String[] queryNames = { "page", "size", "direction", "orderBy" };
		String[] queryValues = { "0", "24", "DESC", "emission" };
		location = HeaderUtils.buildUriWithQueryParams (location.toString (), queryNames, queryValues);
		
		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.GET, this.getStudentEntity (), String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.FORBIDDEN.value ());
		System.out.println (response.getBody ());
	}
	
	// --> INSERT_URL = "/v1/protected/reports/"
	
	@Test
	public void insertShouldPersistDataWhenUserHasRoleEmployeeShouldReturnStatusCode201 ()
	{
		// Data
		ReportNewDTO dto = buildDTO (11);
		HttpEntity<ReportNewDTO> entity = new HttpEntity<>(dto, this.getEmployeeEntity ().getHeaders ());
		ResponseEntity<String> response = restTemplate.exchange (INSERT_URL, HttpMethod.POST, entity, String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.CREATED.value ());
		System.out.println (response.getHeaders ().getLocation ());
	}
	
	@Test
	public void insertWhenUserHasRoleStudentShouldReturnStatusCode403 ()
	{
		// Data
		ReportNewDTO dto = buildDTO (9);
		HttpEntity<ReportNewDTO> entity = new HttpEntity<>(dto, this.getStudentEntity ().getHeaders ());
		ResponseEntity<String> response = restTemplate.exchange (INSERT_URL, HttpMethod.POST, entity, String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.FORBIDDEN.value ());
		System.out.println (response.getBody ());
	}
	
	@Test
	public void insertWhenUserIsInvalidShouldReturnStatusCode403 ()
	{
		// Data
		ReportNewDTO dto = buildDTO (9);
		HttpEntity<ReportNewDTO> entity = new HttpEntity<>(dto, this.getWrongEntity ().getHeaders ());
		ResponseEntity<String> response = restTemplate.exchange (INSERT_URL, HttpMethod.POST, entity, String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.FORBIDDEN.value ());
		System.out.println (response.getBody ());
	}
	
	@Test
	public void insertWhenDataIsNullShouldReturnStatusCode422 ()
	{
		// Data
		ReportNewDTO dto = new ReportNewDTO ();
		HttpEntity<ReportNewDTO> entity = new HttpEntity<>(dto, this.getEmployeeEntity ().getHeaders ());
		ResponseEntity<String> response = restTemplate.exchange (INSERT_URL, HttpMethod.POST, entity, String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.UNPROCESSABLE_ENTITY.value ());
		System.out.println (response.getBody ());
	}
	
	@Test
	public void insertWhenScheduleDoesNotExistsShouldReturnStatusCode404 ()
	{
		// Data
		ReportNewDTO dto = buildDTO (-1);
		HttpEntity<ReportNewDTO> entity = new HttpEntity<>(dto, this.getEmployeeEntity ().getHeaders ());
		ResponseEntity<String> response = restTemplate.exchange (INSERT_URL, HttpMethod.POST, entity, String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.NOT_FOUND.value ());
		System.out.println (response.getBody ());
	}
	
	@Test
	public void insertWhenScheduleHasStatusFinishedShouldReturnStatusCode400 ()
	{
		// Data
		ReportNewDTO dto = buildDTO (1);
		HttpEntity<ReportNewDTO> entity = new HttpEntity<>(dto, this.getEmployeeEntity ().getHeaders ());
		ResponseEntity<String> response = restTemplate.exchange (INSERT_URL, HttpMethod.POST, entity, String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.BAD_REQUEST.value ());
		System.out.println (response.getBody ());
	}
	
	@Test
	public void insertWhenScheduleHasStatusCanceledShouldReturnStatusCode400 ()
	{
		// Data
		ReportNewDTO dto = buildDTO (2);
		HttpEntity<ReportNewDTO> entity = new HttpEntity<>(dto, this.getEmployeeEntity ().getHeaders ());
		ResponseEntity<String> response = restTemplate.exchange (INSERT_URL, HttpMethod.POST, entity, String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.BAD_REQUEST.value ());
		System.out.println (response.getBody ());
	}
	
	// HELPER FUNCTIONS
	
	private ReportNewDTO buildDTO (Integer scheduleId)
	{
		// Data
		ReportNewDTO dto = new ReportNewDTO ();
		dto.setId (null);
		dto.setTitle ("Something...");
		dto.setContent ("Put content here...");
		dto.setEmission (null);
		MeetingScheduleDTO schedule = new MeetingScheduleDTO ();
		schedule.setId (scheduleId);
		dto.setSchedule (schedule);
		
		return dto;
	}
}