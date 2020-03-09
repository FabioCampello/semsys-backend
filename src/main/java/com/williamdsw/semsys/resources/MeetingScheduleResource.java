package com.williamdsw.semsys.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	@ApiOperation (value = "Find all schedules", response = MeetingScheduleDTO[].class)
	@PreAuthorize ("hasRole('EMPLOYEE')")
	@GetMapping ()
	public ResponseEntity<List<MeetingScheduleDTO>> findAll ()
	{
		List<MeetingSchedule> schedules = service.findAll ();
		List<MeetingScheduleDTO> listDto = schedules.stream ().map (schedule -> new MeetingScheduleDTO (schedule)).collect (Collectors.toList ());
		return ResponseEntity.ok ().body (listDto);
	}
	
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
	
	@ApiOperation (value = "Find all by employee", response = MeetingScheduleDTO[].class)
	@PreAuthorize ("hasRole('EMPLOYEE')")
	@GetMapping (path = "/employee/{employeeId}")
	public ResponseEntity<List<MeetingScheduleDTO>> findByEmployee (@PathVariable Integer employeeId)
	{
		List<MeetingSchedule> schedules = service.findByEmployee (employeeId);
		List<MeetingScheduleDTO> listDto = schedules.stream ().map (schedule -> new MeetingScheduleDTO (schedule)).collect (Collectors.toList ());
		return ResponseEntity.ok ().body (listDto);
	}
	
	@ApiOperation (value = "Find all by student", response = MeetingScheduleDTO[].class)
	@PreAuthorize ("hasRole('STUDENT') or hasRole('EMPLOYEE')")
	@GetMapping (path = "/student/{studentId}")
	public ResponseEntity<List<MeetingScheduleDTO>> findByStudent (@PathVariable Integer studentId)
	{
		List<MeetingSchedule> schedules = service.findByStudent (studentId);
		List<MeetingScheduleDTO> listDto = schedules.stream ().map (schedule -> new MeetingScheduleDTO (schedule)).collect (Collectors.toList ());
		return ResponseEntity.ok ().body (listDto);
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