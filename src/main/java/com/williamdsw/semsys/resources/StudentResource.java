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
import com.williamdsw.semsys.domain.Student;
import com.williamdsw.semsys.domain.dto.StudentDTO;
import com.williamdsw.semsys.domain.dto.StudentNewDTO;
import com.williamdsw.semsys.services.PersonService;
import com.williamdsw.semsys.services.StudentService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping (path = "/v1")
public class StudentResource 
{
	// FIELDS

	@Autowired private StudentService studentService;
	@Autowired private PersonService personService;
	
	// ENDPOINTS
	
	@ApiOperation (value = "Find all students", response = StudentDTO[].class)
	@PreAuthorize ("hasRole('EMPLOYEE')")
	@GetMapping (path = "/protected/students")
	public ResponseEntity<List<StudentDTO>> findAllStudents ()
	{
		List<Student> students = studentService.findAllStudents ();
		List<StudentDTO> listDto = students.stream ().map (student -> new StudentDTO (student)).collect (Collectors.toList ());
		return ResponseEntity.ok ().body (listDto);
	}
	
	@ApiOperation (value = "Find all by name with pagination", response = StudentDTO[].class)
	@PreAuthorize ("hasRole('EMPLOYEE')")
	@GetMapping (path = "/protected/students/page")
	public ResponseEntity<Page<StudentDTO>> findByNamePage 
	(
		@RequestParam (name = "name", defaultValue = "") String name, 
		@RequestParam (name = "page", defaultValue = "0") Integer page, 
		@RequestParam (name = "size", defaultValue = "24") Integer size, 
		@RequestParam (name = "direction", defaultValue = "ASC") String direction, 
		@RequestParam (name = "orderBy", defaultValue = "name") String orderBy
	)
	{
		Page<Student> students = studentService.findByNamePage (name, page, size, direction, orderBy);
		Page<StudentDTO> pageDto = students.map (student -> new StudentDTO (student));
		return ResponseEntity.ok ().body (pageDto);
	}
	
	@ApiOperation (value = "Find by email", response = StudentDTO.class)
	@PreAuthorize ("hasRole('EMPLOYEE')")
	@GetMapping (path = "/protected/students/email")
	public ResponseEntity<StudentDTO> findByEmail (@RequestParam (value = "value") String email)
	{
		Student student = studentService.findByEmail (email);
		StudentDTO dto = new StudentDTO (student);
		return ResponseEntity.ok ().body (dto);
	}
	
	@ApiOperation (value = "Find by SSN", response = StudentDTO.class)
	@PreAuthorize ("hasRole('EMPLOYEE')")
	@GetMapping (path = "/protected/students/ssn")
	public ResponseEntity<StudentDTO> findBySSN (@RequestParam (value = "value") String ssn)
	{
		Student student = studentService.findBySocialSecurityNumber (ssn);
		StudentDTO dto = new StudentDTO (student);
		return ResponseEntity.ok ().body (dto);
	}
	
	@ApiOperation (value = "Insert new student")
	@PostMapping (path = "/public/students")
	public ResponseEntity<Void> insert (@Valid @RequestBody StudentNewDTO dto)
	{
		Student student = studentService.fromDTO (dto);
		student = (Student) personService.insert (student);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest ().path("/{id}").buildAndExpand (student.getId ()).toUri ();
		return ResponseEntity.created (location).build ();		
	}
	
	@ApiOperation (value = "Update student's data")
	@PreAuthorize ("hasRole('STUDENT')")
	@PutMapping (path = "/protected/students/{id}")
	public ResponseEntity<Void> update (@Valid @RequestBody StudentDTO dto, @PathVariable Integer id)
	{
		Student student = studentService.fromDTO (dto);
		student.setId (id);
		personService.update (student);
		return ResponseEntity.noContent ().build ();
	}
}