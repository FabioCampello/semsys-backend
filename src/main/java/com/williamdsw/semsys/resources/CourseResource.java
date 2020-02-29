package com.williamdsw.semsys.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
	
	@ApiOperation (value = "Find all courses by given period with pagination", response = CourseDTO[].class)
	@PreAuthorize ("hasRole('EMPLOYEE')")
	@GetMapping (path = "/protected/courses/period")
	public ResponseEntity<Page<CourseDTO>> findByPeriodPage 
	(
		@RequestParam (value = "value", defaultValue = "Morning") String description,
		@RequestParam (value = "page", defaultValue = "0") Integer page,
		@RequestParam (value = "size", defaultValue = "24") Integer size,
		@RequestParam (value = "direction", defaultValue = "ASC") String direction,
		@RequestParam (value = "orderBy", defaultValue = "name") String orderBy
	)
	{
		TimePeriod timePeriod = TimePeriod.toEnum (description);
		Page<Course> courses = courseService.findByPeriod (timePeriod, page, size, direction, orderBy);
		Page<CourseDTO> pageDto = courses.map (course -> new CourseDTO (course));
		return ResponseEntity.ok ().body (pageDto);
	}
	
	@ApiOperation (value = "Find all courses by name with pagination", response = CourseDTO[].class)
	@PreAuthorize ("hasRole('EMPLOYEE')")
	@GetMapping (path = "/protected/courses/name")
	public ResponseEntity<Page<CourseDTO>> findByNamePage 
	(
		@RequestParam (value = "name", defaultValue = "") String name,
		@RequestParam (value = "page", defaultValue = "0") Integer page,
		@RequestParam (value = "size", defaultValue = "24") Integer size,
		@RequestParam (value = "direction", defaultValue = "ASC") String direction,
		@RequestParam (value = "orderBy", defaultValue = "name") String orderBy
	)
	{
		Page<Course> courses = courseService.findByName (name, page, size, direction, orderBy);
		Page<CourseDTO> pageDto = courses.map (course -> new CourseDTO (course));
		return ResponseEntity.ok ().body (pageDto);
	}
	
	// ENDPOINTS -- SCHOOL CLASSES
	
	@ApiOperation (value = "Find all classes by given course", response = SchoolClassDTO[].class)
	@GetMapping (path = "/public/courses/{courseId}/classes")
	public ResponseEntity<List<SchoolClassDTO>> findAllClassesByCourse (@PathVariable Integer courseId)
	{
		List<SchoolClass> classes = schoolClassService.findByCourse (courseId);
		List<SchoolClassDTO> listDto = classes.stream ().map (schoolClass -> new SchoolClassDTO (schoolClass)).collect (Collectors.toList ());
		return ResponseEntity.ok ().body (listDto);
	}
	
	@ApiOperation (value = "Find all classes by given course with pagination", response = SchoolClassDTO[].class)
	@PreAuthorize ("hasRole('EMPLOYEE')")
	@GetMapping (path = "/protected/courses/{courseId}/classes/page")
	public ResponseEntity<Page<SchoolClassDTO>> findAllClassesByCourseAndNamePage 
	(
		@PathVariable Integer courseId,
		@RequestParam (value = "name", defaultValue = "") String name,
		@RequestParam (value = "page", defaultValue = "0") Integer page,
		@RequestParam (value = "size", defaultValue = "24") Integer size,
		@RequestParam (value = "direction", defaultValue = "ASC") String direction,
		@RequestParam (value = "orderBy", defaultValue = "name") String orderBy
	)
	{
		Page<SchoolClass> classes = schoolClassService.findByCourseAndNamePage (courseId, name, page, size, direction, orderBy);
		Page<SchoolClassDTO> pageDto = classes.map (schoolClass -> new SchoolClassDTO (schoolClass));
		return ResponseEntity.ok ().body (pageDto);
	}
}