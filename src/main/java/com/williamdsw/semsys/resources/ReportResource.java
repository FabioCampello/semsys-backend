package com.williamdsw.semsys.resources;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.williamdsw.semsys.domain.Report;
import com.williamdsw.semsys.domain.dto.ReportDTO;
import com.williamdsw.semsys.domain.dto.ReportNewDTO;
import com.williamdsw.semsys.services.ReportService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping (path = "/v1/protected/reports")
public class ReportResource 
{
	// FIELDS
	
	@Autowired private ReportService reportService;
	
	// ENDPOINTS
	
	@ApiOperation (value = "Find by id", response = ReportDTO.class)
	@PreAuthorize ("hasRole('EMPLOYEE') or hasRole ('STUDENT')")
	@GetMapping (path = "/{id}")
	public ResponseEntity<ReportDTO> findById (@PathVariable Integer id)
	{
		Report report = reportService.findById (id);
		ReportDTO dto = new ReportDTO (report);
		return ResponseEntity.ok ().body (dto);
	}
	
	@ApiOperation (value = "Find by schedule", response = ReportDTO.class)
	@PreAuthorize ("hasRole('EMPLOYEE') or hasRole ('STUDENT')")
	@GetMapping (path = "/schedule/{id}")
	public ResponseEntity<ReportDTO> findBySchedule (@PathVariable Integer id)
	{
		Report report = reportService.findBySchedule (id);
		ReportDTO dto = new ReportDTO (report);
		return ResponseEntity.ok ().body (dto);
	}
	
	@ApiOperation (value = "List all by employee", response = ReportDTO[].class)
	@PreAuthorize ("hasRole('EMPLOYEE')")
	@GetMapping (path = "/employee/{id}")
	public ResponseEntity<Page<ReportDTO>> findByEmployee 
	(
		@PathVariable Integer id,
		@RequestParam (value = "page", defaultValue = "0") Integer page,
		@RequestParam (value = "size", defaultValue = "24") Integer size,
		@RequestParam (value = "direction", defaultValue = "DESC") String direction,
		@RequestParam (value = "orderBy", defaultValue = "emission") String orderBy
	)
	{
		Page<Report> pageReport = reportService.findByEmployee (id, page, size, direction, orderBy);
		Page<ReportDTO> pageDto = pageReport.map (report -> new ReportDTO (report));
		return ResponseEntity.ok ().body (pageDto);
	}
	
	@ApiOperation (value = "List all by student", response = ReportDTO[].class)
	@PreAuthorize ("hasRole('EMPLOYEE') or hasRole ('STUDENT')")
	@GetMapping (path = "/student/{id}")
	public ResponseEntity<Page<ReportDTO>> findByStudent 
	(
		@PathVariable Integer id,
		@RequestParam (value = "page", defaultValue = "0") Integer page,
		@RequestParam (value = "size", defaultValue = "24") Integer size,
		@RequestParam (value = "direction", defaultValue = "DESC") String direction,
		@RequestParam (value = "orderBy", defaultValue = "emission") String orderBy
	)
	{
		Page<Report> pageReport = reportService.findByStudent (id, page, size, direction, orderBy);
		Page<ReportDTO> pageDto = pageReport.map (report -> new ReportDTO (report));
		return ResponseEntity.ok ().body (pageDto);
	}
	
	@ApiOperation (value = "Insert new report")
	@PreAuthorize ("hasRole('EMPLOYEE')")
	@PostMapping
	public ResponseEntity<Void> insert (@Valid @RequestBody ReportNewDTO dto)
	{
		Report report = reportService.fromDTO (dto);
		report = reportService.insert (report);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest ().path ("/{id}").buildAndExpand (report.getId ()).toUri ();
		return ResponseEntity.created (location).build ();
	}
}