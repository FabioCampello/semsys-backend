package com.williamdsw.semsys.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.williamdsw.semsys.domain.MeetingSchedule;
import com.williamdsw.semsys.domain.dto.MeetingScheduleDTO;
import com.williamdsw.semsys.domain.dto.MeetingScheduleNewDTO;
import com.williamdsw.semsys.domain.enums.MeetingStatus;
import com.williamdsw.semsys.services.MeetingScheduleService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping ("/v1/protected/schedules")
public class MeetingScheduleResource 
{
	// FIELDS
	
	@Autowired private MeetingScheduleService service;
	
	// ENDPOINTS
	
	@ApiOperation (value = "Find by id", response = MeetingScheduleDTO.class)
	@PreAuthorize ("hasRole('STUDENT') or hasRole('EMPLOYEE')")
	@GetMapping (path = "/{id}")
	public ResponseEntity<MeetingScheduleDTO> findById (@PathVariable Integer id)
	{
		MeetingSchedule schedule = service.findById (id);
		MeetingScheduleDTO scheduleDto = new MeetingScheduleDTO (schedule);
		return ResponseEntity.ok ().body (scheduleDto);
	}
	
	@ApiOperation (value = "Find all by status", response = MeetingScheduleDTO[].class)
	@PreAuthorize ("hasRole('EMPLOYEE')")
	@GetMapping (path = "/status")
	public ResponseEntity<List<MeetingScheduleDTO>> findByStatus (@RequestParam (value = "value", defaultValue = "Finished") String description)
	{
		List<MeetingSchedule> schedules = service.findByStatus (MeetingStatus.toEnum (description));
		List<MeetingScheduleDTO> listDto = schedules.stream ().map (schedule -> new MeetingScheduleDTO (schedule)).collect (Collectors.toList ());
		return ResponseEntity.ok ().body (listDto);
	}
	
	@ApiOperation (value = "Find all with pagination", response = MeetingScheduleDTO[].class)
	@PreAuthorize ("hasRole('EMPLOYEE')")
	@GetMapping (path = "/page")
	public ResponseEntity<Page<MeetingScheduleDTO>> findPage 
	(
		@RequestParam (value = "page", defaultValue = "0") Integer page,
		@RequestParam (value = "size", defaultValue = "24") Integer size,
		@RequestParam (value = "direction", defaultValue = "ASC") String direction,
		@RequestParam (value = "orderBy", defaultValue = "status") String orderBy
	)
	{
		Page<MeetingSchedule> pageSchedule = service.findPage (page, size, direction, orderBy);
		Page<MeetingScheduleDTO> pageDto = pageSchedule.map (schedule -> new MeetingScheduleDTO (schedule));
		return ResponseEntity.ok ().body (pageDto);
	}
	
	@ApiOperation (value = "Find all by employee with pagination", response = MeetingScheduleDTO[].class)
	@PreAuthorize ("hasRole('EMPLOYEE')")
	@GetMapping (path = "/employee/{employeeId}")
	public ResponseEntity<Page<MeetingScheduleDTO>> findByEmployee 
	(
		@PathVariable Integer employeeId,
		@RequestParam (value = "page", defaultValue = "0") Integer page,
		@RequestParam (value = "size", defaultValue = "24") Integer size,
		@RequestParam (value = "direction", defaultValue = "ASC") String direction,
		@RequestParam (value = "orderBy", defaultValue = "status") String orderBy
	)
	{
		Page<MeetingSchedule> pageSchedule = service.findByEmployee (employeeId, page, size, direction, orderBy);
		Page<MeetingScheduleDTO> pageDto = pageSchedule.map (schedule -> new MeetingScheduleDTO (schedule));
		return ResponseEntity.ok ().body (pageDto);
	}
	
	@ApiOperation (value = "Find all by student with pagination", response = MeetingScheduleDTO[].class)
	@PreAuthorize ("hasRole('STUDENT') or hasRole('EMPLOYEE')")
	@GetMapping (path = "/student/{studentId}")
	public ResponseEntity<Page<MeetingScheduleDTO>> findByStudent 
	(
		@PathVariable Integer studentId,
		@RequestParam (value = "page", defaultValue = "0") Integer page,
		@RequestParam (value = "size", defaultValue = "24") Integer size,
		@RequestParam (value = "direction", defaultValue = "ASC") String direction,
		@RequestParam (value = "orderBy", defaultValue = "id") String orderBy
	)
	{
		Page<MeetingSchedule> pageSchedule = service.findByStudent (studentId, page, size, direction, orderBy);
		Page<MeetingScheduleDTO> pageDto = pageSchedule.map (schedule -> new MeetingScheduleDTO (schedule));
		return ResponseEntity.ok ().body (pageDto);
	}
	
	@ApiOperation (value = "Insert new meeting schedule")
	@PreAuthorize ("hasRole('EMPLOYEE')")
	@PostMapping
	public ResponseEntity<Void> insert (@Valid @RequestBody MeetingScheduleNewDTO dto)
	{
		MeetingSchedule schedule = service.fromDTO (dto);
		schedule = service.insert (schedule);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest ().path ("/{id}").buildAndExpand (schedule.getId ()).toUri ();
		return ResponseEntity.created (location).build ();
	}
	
	@ApiOperation (value = "Update status of scheduled meeting to CANCELED or FINISHED")
	@PreAuthorize ("hasRole('EMPLOYEE')")
	@PutMapping (path = "/{id}")
	public ResponseEntity<Void> updateStatus (@Valid @RequestBody MeetingScheduleDTO dto, @PathVariable Integer id)
	{
		MeetingSchedule schedule = service.fromDTO (dto);
		schedule.setId (id);
		service.updateStatus (schedule);
		return ResponseEntity.noContent ().build ();
	}	
}