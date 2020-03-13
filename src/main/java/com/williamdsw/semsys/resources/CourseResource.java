package com.williamdsw.semsys.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.williamdsw.semsys.domain.Course;
import com.williamdsw.semsys.domain.SchoolClass;
import com.williamdsw.semsys.domain.dto.CourseDTO;
import com.williamdsw.semsys.domain.dto.SchoolClassDTO;
import com.williamdsw.semsys.domain.enums.TimePeriod;
import com.williamdsw.semsys.services.CourseService;
import com.williamdsw.semsys.services.SchoolClassService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping (path = "/v1")
public class CourseResource 
{
	// FIELDS
	
	@Autowired private CourseService courseService;
	@Autowired private SchoolClassService schoolClassService;	
	
	// ENDPOINTS -- COURSES
	
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
	public ResponseEntity<List<CourseDTO>> findByName (@RequestParam (value = "name", defaultValue = "Morning") String name)
	{
		List<Course> courses = courseService.findByName (name);
		List<CourseDTO> listDto = courses.stream ().map (course -> new CourseDTO (course)).collect (Collectors.toList ());
		return ResponseEntity.ok ().body (listDto);
	}
	
	// ENDPOINTS -- SCHOOL CLASSES
	
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
}