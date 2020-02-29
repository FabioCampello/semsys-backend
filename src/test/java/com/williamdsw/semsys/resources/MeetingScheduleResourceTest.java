package com.williamdsw.semsys.resources;

import java.net.URI;
import java.time.LocalDateTime;

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
import com.williamdsw.semsys.domain.dto.MeetingScheduleNewDTO;
import com.williamdsw.semsys.domain.enums.MeetingStatus;
import com.williamdsw.semsys.resources.utils.HeaderUtils;

@ExtendWith (SpringExtension.class)
@SpringBootTest (webEnvironment = WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
@ActiveProfiles (profiles = "test")
public class MeetingScheduleResourceTest extends GlobalResourceConfigureTest
{
	// FIELDS
	
	private final String FIND_BY_ID_URL = "/v1/protected/schedules/{id}/";
	private final String FIND_ALL_BY_STATUS_URL = "/v1/protected/schedules/status";
	private final String FIND_ALL_PAGE_URL = "/v1/protected/schedules/page";
	private final String FIND_ALL_BY_EMPLOYEE_URL = "/v1/protected/schedules/employee/{employeeId}";
	private final String FIND_ALL_BY_STUDENT_URL = "/v1/protected/schedules/student/{studentId}";
	private final String INSERT_URL = "/v1/protected/schedules/";
	private final String UPDATE_URL = "/v1/protected/schedules/{id}/";
	
	// TESTS
	
	// --> FIND_BY_ID_URL = "/v1/protected/schedules/{id}/";
	
	@Test
	public void findByIdWhenUserHasRoleStudentAndScheduleExistsShouldReturnStatusCode200 ()
	{
		String[] headers = { "id" };
		String[] values = { "1" };
		URI location = HeaderUtils.buildUriWithPathParams (FIND_BY_ID_URL, headers, values);
		
		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.GET, this.getStudentEntity (), String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.OK.value ());
		System.out.println (response.getBody ());
	}
	
	@Test
	public void findByIdWhenUserHasRoleEmployeeAndScheduleExistsShouldReturnStatusCode200 ()
	{
		String[] headers = { "id" };
		String[] values = { "1" };
		URI location = HeaderUtils.buildUriWithPathParams (FIND_BY_ID_URL, headers, values);
		
		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.GET, this.getEmployeeEntity (), String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.OK.value ());
		System.out.println (response.getBody ());
	}
	
	@Test
	public void findByIdWhenUserInInvalidAndScheduleExistsShouldReturnStatusCode403 ()
	{
		String[] headers = { "id" };
		String[] values = { "1" };
		URI location = HeaderUtils.buildUriWithPathParams (FIND_BY_ID_URL, headers, values);
		
		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.GET, this.getWrongEntity (), String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.FORBIDDEN.value ());
		System.out.println (response.getBody ());
	}
	
	@Test
	public void findByIdWhenUserHasRoleStudentAndScheduleDoesNotExistsShouldReturnStatusCode404 ()
	{
		String[] headers = { "id" };
		String[] values = { "-1" };
		URI location = HeaderUtils.buildUriWithPathParams (FIND_BY_ID_URL, headers, values);
		
		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.GET, this.getStudentEntity (), String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.NOT_FOUND.value ());
		System.out.println (response.getBody ());
	}
	
	@Test
	public void findByIdWhenUserHasRoleEmployeeAndScheduleDoesNotExistsShouldReturnStatusCode404 ()
	{
		String[] headers = { "id" };
		String[] values = { "-1" };
		URI location = HeaderUtils.buildUriWithPathParams (FIND_BY_ID_URL, headers, values);
		
		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.GET, this.getEmployeeEntity (), String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.NOT_FOUND.value ());
		System.out.println (response.getBody ());
	}
	
	// --> FIND_ALL_BY_STATUS_URL = "/v1/protected/schedules/status"
	
	@Test
	public void findAllByStatusWhenUserHasRoleEmployeeShouldReturnStatusCode200 ()
	{
		String[] names = { "value" };
		String[] values = { "Scheduled" };
		URI location = HeaderUtils.buildUriWithQueryParams (FIND_ALL_BY_STATUS_URL, names, values);
		
		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.GET, this.getEmployeeEntity (), String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.OK.value ());
		System.out.println (response.getBody ());
	}
	
	@Test
	public void findAllByStatusWhenUserHasRoleStudentShouldReturnStatusCode403 ()
	{
		String[] names = { "value" };
		String[] values = { "Scheduled" };
		URI location = HeaderUtils.buildUriWithQueryParams (FIND_ALL_BY_STATUS_URL, names, values);
		
		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.GET, this.getStudentEntity (), String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.FORBIDDEN.value ());
		System.out.println (response.getBody ());
	}
	
	@Test
	public void findAllByStatusWhenUserIsInvalidShouldReturnStatusCode403 ()
	{
		String[] names = { "value" };
		String[] values = { "Scheduled" };
		URI location = HeaderUtils.buildUriWithQueryParams (FIND_ALL_BY_STATUS_URL, names, values);
		
		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.GET, this.getWrongEntity (), String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.FORBIDDEN.value ());
		System.out.println (response.getBody ());
	}
	
	@Test
	public void findAllByStatusWhenUserHasRoleEmployeeAndStatusIsInvalidShouldReturnStatusCode400 ()
	{
		String[] names = { "value" };
		String[] values = { "Another" };
		URI location = HeaderUtils.buildUriWithQueryParams (FIND_ALL_BY_STATUS_URL, names, values);
		
		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.GET, this.getEmployeeEntity (), String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.BAD_REQUEST.value ());
		System.out.println (response.getBody ());
	}
	
	// --> FIND_ALL_PAGE_URL = "/v1/protected/schedules/page"
	
	@Test
	public void findAllPageWhenUserHasRoleEmployeeShouldReturnStatusCode200 ()
	{
		String[] names = { "page", "size", "direction", "orderBy" };
		String[] values = { "0", "24", "ASC", "status"};
		URI location = HeaderUtils.buildUriWithQueryParams (FIND_ALL_PAGE_URL, names, values);
		
		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.GET, this.getEmployeeEntity (), String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.OK.value ());
		System.out.println (response.getBody ());
	}
	
	@Test
	public void findAllPageWhenUserHasRoleStudentShouldReturnStatusCode403 ()
	{
		String[] names = { "page", "size", "direction", "orderBy" };
		String[] values = { "0", "24", "ASC", "status"};
		URI location = HeaderUtils.buildUriWithQueryParams (FIND_ALL_PAGE_URL, names, values);
		
		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.GET, this.getStudentEntity (), String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.FORBIDDEN.value ());
		System.out.println (response.getBody ());
	}
	
	@Test
	public void findAllPageWhenUserIsInvalidShouldReturnStatusCode403 ()
	{
		String[] names = { "page", "size", "direction", "orderBy" };
		String[] values = { "0", "24", "ASC", "status"};
		URI location = HeaderUtils.buildUriWithQueryParams (FIND_ALL_PAGE_URL, names, values);
		
		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.GET, this.getWrongEntity (), String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.FORBIDDEN.value ());
		System.out.println (response.getBody ());
	}
	
	// --> FIND_ALL_BY_EMPLOYEE_URL = "/v1/protected/schedules/employee/{employeeId}/";
	
	@Test
	public void findAllPageByEmployeeWhenUserHasRoleEmployeeShouldReturnStatusCode200 ()
	{
		String[] pathHeaders = { "employeeId" };
		String[] pathValues = { "1" };
		URI location = HeaderUtils.buildUriWithPathParams (FIND_ALL_BY_EMPLOYEE_URL, pathHeaders, pathValues);
				
		String[] queryNames = { "page", "size", "direction", "orderBy" };
		String[] queryValues = { "0", "24", "ASC", "status"};
		location = HeaderUtils.buildUriWithQueryParams (location.toString (), queryNames, queryValues);
				
		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.GET, this.getEmployeeEntity (), String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.OK.value ());
		System.out.println (response.getBody ());
	}
	
	@Test
	public void findAllPageByEmployeeWhenUserHasRoleStudentShouldReturnStatusCode403 ()
	{
		String[] pathHeaders = { "employeeId" };
		String[] pathValues = { "1" };
		URI location = HeaderUtils.buildUriWithPathParams (FIND_ALL_BY_EMPLOYEE_URL, pathHeaders, pathValues);
				
		String[] queryNames = { "page", "size", "direction", "orderBy" };
		String[] queryValues = { "0", "24", "ASC", "status"};
		location = HeaderUtils.buildUriWithQueryParams (location.toString (), queryNames, queryValues);
				
		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.GET, this.getStudentEntity (), String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.FORBIDDEN.value ());
		System.out.println (response.getBody ());
	}
	
	@Test
	public void findAllPageByEmployeeWhenIsInvalidShouldReturnStatusCode403 ()
	{
		String[] pathHeaders = { "employeeId" };
		String[] pathValues = { "1" };
		URI location = HeaderUtils.buildUriWithPathParams (FIND_ALL_BY_EMPLOYEE_URL, pathHeaders, pathValues);
				
		String[] queryNames = { "page", "size", "direction", "orderBy" };
		String[] queryValues = { "0", "24", "ASC", "status"};
		location = HeaderUtils.buildUriWithQueryParams (location.toString (), queryNames, queryValues);
				
		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.GET, this.getWrongEntity (), String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.FORBIDDEN.value ());
		System.out.println (response.getBody ());
	}
	
	@Test
	public void findAllPageByEmployeeWhenUserHasRoleEmployeeAndEmployeeDoesNotExistsShouldReturnStatusCode200 ()
	{
		String[] pathHeaders = { "employeeId" };
		String[] pathValues = { "-1" };
		URI location = HeaderUtils.buildUriWithPathParams (FIND_ALL_BY_EMPLOYEE_URL, pathHeaders, pathValues);
				
		String[] queryNames = { "page", "size", "direction", "orderBy" };
		String[] queryValues = { "0", "24", "ASC", "status"};
		location = HeaderUtils.buildUriWithQueryParams (location.toString (), queryNames, queryValues);
				
		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.GET, this.getEmployeeEntity (), String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.NOT_FOUND.value ());
		System.out.println (response.getBody ());
	}
	
	// --> FIND_ALL_BY_STUDENT_URL = "/v1/protected/schedules/student/{studentId}";
	
	@Test
	public void findAllPageByStudentWhenUserHasRoleStudentShouldReturnStatusCode200 ()
	{
		String[] pathHeaders = { "studentId" };
		String[] pathValues = { "2" };
		URI location = HeaderUtils.buildUriWithPathParams (FIND_ALL_BY_STUDENT_URL, pathHeaders, pathValues);
				
		String[] queryNames = { "page", "size", "direction", "orderBy" };
		String[] queryValues = { "0", "24", "ASC", "id"};
		location = HeaderUtils.buildUriWithQueryParams (location.toString (), queryNames, queryValues);
				
		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.GET, this.getStudentEntity (), String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.OK.value ());
		System.out.println (response.getBody ());
	}
	
	@Test
	public void findAllPageByStudentWhenUserHasRoleEmployeeShouldReturnStatusCode200 ()
	{
		String[] pathHeaders = { "studentId" };
		String[] pathValues = { "2" };
		URI location = HeaderUtils.buildUriWithPathParams (FIND_ALL_BY_STUDENT_URL, pathHeaders, pathValues);
				
		String[] queryNames = { "page", "size", "direction", "orderBy" };
		String[] queryValues = { "0", "24", "ASC", "id"};
		location = HeaderUtils.buildUriWithQueryParams (location.toString (), queryNames, queryValues);
				
		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.GET, this.getEmployeeEntity (), String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.OK.value ());
		System.out.println (response.getBody ());
	}
	
	@Test
	public void findAllPageByStudentWhenUserIsInvalidShouldReturnStatusCode403 ()
	{
		String[] pathHeaders = { "studentId" };
		String[] pathValues = { "2" };
		URI location = HeaderUtils.buildUriWithPathParams (FIND_ALL_BY_STUDENT_URL, pathHeaders, pathValues);
				
		String[] queryNames = { "page", "size", "direction", "orderBy" };
		String[] queryValues = { "0", "24", "ASC", "id"};
		location = HeaderUtils.buildUriWithQueryParams (location.toString (), queryNames, queryValues);
				
		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.GET, this.getWrongEntity (), String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.FORBIDDEN.value ());
		System.out.println (response.getBody ());
	}
	
	@Test
	public void findAllPageByStudentWhenUserHasRoleStudentAndStudentDoesNotExistsOrStudentIsDifferentShouldReturnStatusCode403 ()
	{
		String[] pathHeaders = { "studentId" };
		String[] pathValues = { "-1" };
		URI location = HeaderUtils.buildUriWithPathParams (FIND_ALL_BY_STUDENT_URL, pathHeaders, pathValues);
				
		String[] queryNames = { "page", "size", "direction", "orderBy" };
		String[] queryValues = { "0", "24", "ASC", "id"};
		location = HeaderUtils.buildUriWithQueryParams (location.toString (), queryNames, queryValues);
				
		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.GET, this.getStudentEntity (), String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.FORBIDDEN.value ());
		System.out.println (response.getBody ());
	}
	
	@Test
	public void findAllPageByStudentWhenUserHasRoleEmployeeAndStudentDoesNotExistsShouldReturnStatusCode404 ()
	{
		String[] pathHeaders = { "studentId" };
		String[] pathValues = { "-1" };
		URI location = HeaderUtils.buildUriWithPathParams (FIND_ALL_BY_STUDENT_URL, pathHeaders, pathValues);
				
		String[] queryNames = { "page", "size", "direction", "orderBy" };
		String[] queryValues = { "0", "24", "ASC", "id"};
		location = HeaderUtils.buildUriWithQueryParams (location.toString (), queryNames, queryValues);
				
		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.GET, this.getEmployeeEntity (), String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.NOT_FOUND.value ());
		System.out.println (response.getBody ());
	}
	
	// --> INSERT_URL = "/v1/protected/schedules/"
	
	@Test
	public void insertShouldPersistDataWhenUserHasRoleEmployeeShouldReturnStatusCode201 ()
	{
		// Data
		MeetingScheduleNewDTO dto = new MeetingScheduleNewDTO ();
		dto.setDatetime (LocalDateTime.parse ("2020-01-16T15:38"));
		dto.setEmployeeId (5);
		dto.setStudentId (2);
		
		HttpEntity<MeetingScheduleNewDTO> entity = new HttpEntity<>(dto, this.getEmployeeEntity ().getHeaders ());
		ResponseEntity<String> response = restTemplate.exchange (INSERT_URL, HttpMethod.POST, entity, String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.CREATED.value ());
		System.out.println (response.getHeaders ().getLocation ());
	}
	
	@Test
	public void insertWhenUserHasRoleStudentShouldReturnStatusCode403 ()
	{
		// Data
		MeetingScheduleNewDTO dto = new MeetingScheduleNewDTO ();
		dto.setDatetime (LocalDateTime.parse ("2020-01-16T15:38"));
		dto.setEmployeeId (1);
		dto.setStudentId (2);
		
		HttpEntity<MeetingScheduleNewDTO> entity = new HttpEntity<>(dto, this.getStudentEntity ().getHeaders ());
		ResponseEntity<String> response = restTemplate.exchange (INSERT_URL, HttpMethod.POST, entity, String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.FORBIDDEN.value ());
		System.out.println (response.getBody ());
	}
	
	@Test
	public void insertWhenUserIsInvalidShouldReturnStatusCode403 ()
	{
		// Data
		MeetingScheduleNewDTO dto = new MeetingScheduleNewDTO ();
		dto.setDatetime (LocalDateTime.parse ("2020-01-16T15:38"));
		dto.setEmployeeId (1);
		dto.setStudentId (2);
		
		HttpEntity<MeetingScheduleNewDTO> entity = new HttpEntity<>(dto, this.getWrongEntity ().getHeaders ());
		ResponseEntity<String> response = restTemplate.exchange (INSERT_URL, HttpMethod.POST, entity, String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.FORBIDDEN.value ());
		System.out.println (response.getBody ());
	}
	
	@Test
	public void insertWhenDataIsNullShouldReturnStatusCode422 ()
	{
		// Data
		MeetingScheduleNewDTO dto = new MeetingScheduleNewDTO ();
		HttpEntity<MeetingScheduleNewDTO> entity = new HttpEntity<>(dto, this.getEmployeeEntity ().getHeaders ());
		ResponseEntity<String> response = restTemplate.exchange (INSERT_URL, HttpMethod.POST, entity, String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.UNPROCESSABLE_ENTITY.value ());
		System.out.println (response.getBody ());
	}
	
	@Test
	public void insertWhenEmployeeDoesNotExistsShouldReturnStatusCode404 ()
	{
		// Data
		MeetingScheduleNewDTO dto = new MeetingScheduleNewDTO ();
		dto.setDatetime (LocalDateTime.parse ("2020-01-16T15:38"));
		dto.setEmployeeId (-1);
		dto.setStudentId (2);
		
		HttpEntity<MeetingScheduleNewDTO> entity = new HttpEntity<>(dto, this.getEmployeeEntity ().getHeaders ());
		ResponseEntity<String> response = restTemplate.exchange (INSERT_URL, HttpMethod.POST, entity, String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.NOT_FOUND.value ());
		System.out.println (response.getBody ());
	}
	
	@Test
	public void insertWhenStudentDoesNotExistsShouldReturnStatusCode404 ()
	{
		// Data
		MeetingScheduleNewDTO dto = new MeetingScheduleNewDTO ();
		dto.setDatetime (LocalDateTime.parse ("2020-01-16T15:38"));
		dto.setEmployeeId (1);
		dto.setStudentId (-2);
		
		HttpEntity<MeetingScheduleNewDTO> entity = new HttpEntity<>(dto, this.getEmployeeEntity ().getHeaders ());
		ResponseEntity<String> response = restTemplate.exchange (INSERT_URL, HttpMethod.POST, entity, String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.NOT_FOUND.value ());
		System.out.println (response.getBody ());
	}

	// --> UPDATE_URL = "/v1/protected/schedules/{id}/"

	@Test
	public void updateStatusWhenUserHasRoleStudentShouldReturnStatusCode403 ()
	{
		String[] headers = { "id" };
		String[] values = { "1" };
		URI location = HeaderUtils.buildUriWithPathParams (UPDATE_URL, headers, values);
		
		// Data
		MeetingScheduleDTO dto = new MeetingScheduleDTO ();
		dto.setMeetingStatus (MeetingStatus.FINISHED.getDescription ());

		HttpEntity<MeetingScheduleDTO> entity = new HttpEntity<>(dto, this.getStudentEntity ().getHeaders ());
		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.PUT, entity, String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.FORBIDDEN.value ());
		System.out.println (response.getBody ());
	}

	@Test
	public void updateStatusWhenUserIsInvalidShouldReturnStatusCode403 ()
	{
		String[] headers = { "id" };
		String[] values = { "1" };
		URI location = HeaderUtils.buildUriWithPathParams (UPDATE_URL, headers, values);
		
		// Data
		MeetingScheduleDTO dto = new MeetingScheduleDTO ();
		dto.setMeetingStatus (MeetingStatus.FINISHED.getDescription ());

		HttpEntity<MeetingScheduleDTO> entity = new HttpEntity<>(dto, this.getWrongEntity ().getHeaders ());
		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.PUT, entity, String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.FORBIDDEN.value ());
		System.out.println (response.getBody ());
	}
	
	@Test
	public void updateStatusToFinishedWhenUserHasRoleEmployeeShouldReturnStatusCode204 ()
	{
		String[] headers = { "id" };
		String[] values = { "11" };
		URI location = HeaderUtils.buildUriWithPathParams (UPDATE_URL, headers, values);
		
		// Data
		MeetingScheduleDTO dto = new MeetingScheduleDTO ();
		dto.setMeetingStatus (MeetingStatus.FINISHED.getDescription ());

		HttpEntity<MeetingScheduleDTO> entity = new HttpEntity<>(dto, this.getEmployeeEntity ().getHeaders ());
		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.PUT, entity, String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.NO_CONTENT.value ());
		System.out.println (response);
	}

	@Test
	public void updateStatusToCanceledWhenUserHasRoleEmployeeShouldReturnStatusCode204 ()
	{
		String[] headers = { "id" };
		String[] values = { "12" };
		URI location = HeaderUtils.buildUriWithPathParams (UPDATE_URL, headers, values);
		
		// Data
		MeetingScheduleDTO dto = new MeetingScheduleDTO ();
		dto.setMeetingStatus (MeetingStatus.CANCELED.getDescription ());

		HttpEntity<MeetingScheduleDTO> entity = new HttpEntity<>(dto, this.getEmployeeEntity ().getHeaders ());
		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.PUT, entity, String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.NO_CONTENT.value ());
		System.out.println (response);
	}
	
	@Test
	public void updateStatusWhenStatusIsInvalidShoudReturnStatusCode400 ()
	{
		String[] headers = { "id" };
		String[] values = { "9" };
		URI location = HeaderUtils.buildUriWithPathParams (UPDATE_URL, headers, values);
		
		// Data
		MeetingScheduleDTO dto = new MeetingScheduleDTO ();
		dto.setMeetingStatus ("Something...");

		HttpEntity<MeetingScheduleDTO> entity = new HttpEntity<>(dto, this.getEmployeeEntity ().getHeaders ());
		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.PUT, entity, String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.BAD_REQUEST.value ());
		System.out.println (response.getBody ());
	}
	
	@Test
	public void updateStatusToScheduledWhenCurrentStatusIsFinishedShouldReturn400 ()
	{
		String[] headers = { "id" };
		String[] values = { "5" };
		URI location = HeaderUtils.buildUriWithPathParams (UPDATE_URL, headers, values);
		
		// Data
		MeetingScheduleDTO dto = new MeetingScheduleDTO ();
		dto.setMeetingStatus (MeetingStatus.SCHEDULED.getDescription ());

		HttpEntity<MeetingScheduleDTO> entity = new HttpEntity<>(dto, this.getEmployeeEntity ().getHeaders ());
		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.PUT, entity, String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.BAD_REQUEST.value ());
		System.out.println (response.getBody ());
	}
	
	@Test
	public void updateStatusToCanceledWhenCurrentStatusIsFinishedShouldReturn400 ()
	{
		String[] headers = { "id" };
		String[] values = { "5" };
		URI location = HeaderUtils.buildUriWithPathParams (UPDATE_URL, headers, values);
		
		// Data
		MeetingScheduleDTO dto = new MeetingScheduleDTO ();
		dto.setMeetingStatus (MeetingStatus.CANCELED.getDescription ());

		HttpEntity<MeetingScheduleDTO> entity = new HttpEntity<>(dto, this.getEmployeeEntity ().getHeaders ());
		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.PUT, entity, String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.BAD_REQUEST.value ());
		System.out.println (response.getBody ());
	}
	
	@Test
	public void updateStatusToScheduledWhenCurrentStatusIsCanceledShouldReturn400 ()
	{
		String[] headers = { "id" };
		String[] values = { "4" };
		URI location = HeaderUtils.buildUriWithPathParams (UPDATE_URL, headers, values);
		
		// Data
		MeetingScheduleDTO dto = new MeetingScheduleDTO ();
		dto.setMeetingStatus (MeetingStatus.SCHEDULED.getDescription ());

		HttpEntity<MeetingScheduleDTO> entity = new HttpEntity<>(dto, this.getEmployeeEntity ().getHeaders ());
		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.PUT, entity, String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.BAD_REQUEST.value ());
		System.out.println (response.getBody ());
	}
	
	@Test
	public void updateStatusToFinishedWhenCurrentStatusIsCanceledShouldReturn400 ()
	{
		String[] headers = { "id" };
		String[] values = { "4" };
		URI location = HeaderUtils.buildUriWithPathParams (UPDATE_URL, headers, values);
		
		// Data
		MeetingScheduleDTO dto = new MeetingScheduleDTO ();
		dto.setMeetingStatus (MeetingStatus.FINISHED.getDescription ());

		HttpEntity<MeetingScheduleDTO> entity = new HttpEntity<>(dto, this.getEmployeeEntity ().getHeaders ());
		ResponseEntity<String> response = restTemplate.exchange (location, HttpMethod.PUT, entity, String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.BAD_REQUEST.value ());
		System.out.println (response.getBody ());
	}
}