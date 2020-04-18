package com.williamdsw.semsys.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.williamdsw.semsys.domain.Course;
import com.williamdsw.semsys.domain.dto.CourseDTO;
import com.williamdsw.semsys.domain.dto.CourseNewDTO;
import com.williamdsw.semsys.domain.enums.TimePeriod;
import com.williamdsw.semsys.services.CourseService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping (path = "/v1")
public class CourseResource 
{
	// FIELDS
	
	@Autowired private CourseService courseService;
	
	// ENDPOINTS
		
	@ApiOperation (value = "Find all courses", response = CourseDTO[].class)
	@GetMapping (path = "/public/courses")
	public ResponseEntity<List<CourseDTO>> findAll ()
	{
		List<Course> courses = courseService.findAllOrderByName ();
		List<CourseDTO> listDto = courses.stream ().map (course -> new CourseDTO (course)).collect (Collectors.toList ());
		return ResponseEntity.ok ().body (listDto);
	}
	
	@ApiOperation (value = "Find all courses by given period", response = CourseDTO[].class)
	@PreAuthorize ("hasRole('EMPLOYEE')")
	@GetMapping (path = "/protected/courses/period")
	public ResponseEntity<List<CourseDTO>> findByPeriod (@RequestParam (value = "value", defaultValue = "Morning") String description)
	{
		TimePeriod timePeriod = TimePeriod.toEnum (description);
		List<Course> courses = courseService.findByPeriod (timePeriod);
		List<CourseDTO> listDto = courses.stream ().map (course -> new CourseDTO (course)).collect (Collectors.toList ());
		return ResponseEntity.ok ().body (listDto);
	}
	
	@ApiOperation (value = "Find all courses by name", response = CourseDTO[].class)
	@PreAuthorize ("hasRole('EMPLOYEE')")
	@GetMapping (path = "/protected/courses/name")
	public ResponseEntity<List<CourseDTO>> findByName (@RequestParam (value = "name", defaultValue = "") String name)
	{
		List<Course> courses = courseService.findByName (name);
		List<CourseDTO> listDto = courses.stream ().map (course -> new CourseDTO (course)).collect (Collectors.toList ());
		return ResponseEntity.ok ().body (listDto);
	}
	
	@ApiOperation (value = "Insert a new course")
	@PreAuthorize ("hasRole('ADMIN')")
	@PostMapping (path = "/admin/courses")
	public ResponseEntity<Void> insert (@Valid @RequestBody CourseNewDTO dto)
	{
		Course course = courseService.fromDTO (dto);
		course = courseService.insert (course);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest ().path ("/{id}").buildAndExpand (course.getId ()).toUri ();
		return ResponseEntity.created (location).build ();
	}
}