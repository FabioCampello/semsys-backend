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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.williamdsw.semsys.domain.SchoolClass;
import com.williamdsw.semsys.domain.dto.SchoolClassDTO;
import com.williamdsw.semsys.domain.dto.SchoolClassNewDTO;
import com.williamdsw.semsys.services.SchoolClassService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping (path = "/v1")
public class SchoolClassResource 
{
	// FIELDS
	
	@Autowired
	private SchoolClassService schoolClassService;
	
	// ENDPOINTS
	
	@ApiOperation (value = "Find all classes by given course and name", response = SchoolClassDTO[].class)
	@GetMapping (path = "/public/courses/{courseId}/classes")
	public ResponseEntity<List<SchoolClassDTO>> findAllClassesByCourseAndName 
	(
		@PathVariable Integer courseId,
		@RequestParam (value = "name", defaultValue = "") String name
	)
	{
		List<SchoolClass> classes = schoolClassService.findByCourseAndName (courseId, name);
		List<SchoolClassDTO> listDto = classes.stream ().map (schoolClass -> new SchoolClassDTO (schoolClass)).collect (Collectors.toList ());
		return ResponseEntity.ok ().body (listDto);
	}
	
	@ApiOperation (value = "Insert a new school class")
	@PreAuthorize ("hasRole('ADMIN')")
	@PostMapping (path = "/admin/classes")
	public ResponseEntity<Void> insert (@Valid @RequestBody SchoolClassNewDTO dto)
	{
		SchoolClass schoolClass = schoolClassService.fromDTO (dto);
		schoolClass = schoolClassService.insert (schoolClass);
		URI location = ServletUriComponentsBuilder.fromCurrentServletMapping ().path ("/v1/public/courses/{id}/classes").buildAndExpand (schoolClass.getCourse ().getId ()).toUri ();
		return ResponseEntity.created (location).build ();
	}
}