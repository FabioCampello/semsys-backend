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
import com.williamdsw.semsys.domain.Employee;
import com.williamdsw.semsys.domain.dto.EmployeeDTO;
import com.williamdsw.semsys.domain.dto.EmployeeNewDTO;
import com.williamdsw.semsys.services.EmployeeService;
import com.williamdsw.semsys.services.PersonService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping (path = "/v1")
public class EmployeeResource 
{
	// FIELDS
	
	@Autowired private EmployeeService employeeService;
	@Autowired private PersonService personService;
	
	// ENDPOINTS
	
	@ApiOperation (value = "Find all employees", response = EmployeeDTO[].class)
	@PreAuthorize ("hasAnyRole('EMPLOYEE') or hasAnyRole('STUDENT')")
	@GetMapping (path = "/protected/employees")
	public ResponseEntity<List<EmployeeDTO>> findAllEmployees ()
	{
		List<Employee> employees = employeeService.findAllEmployees ();
		List<EmployeeDTO> listDto = employees.stream ().map (employee -> new EmployeeDTO (employee)).collect (Collectors.toList ());
		return ResponseEntity.ok ().body (listDto);
	}

	
	@ApiOperation (value = "Find all employees by name", response = EmployeeDTO[].class)
	@PreAuthorize ("hasAnyRole('EMPLOYEE')")
	@GetMapping (path = "/protected/employees/name")
	public ResponseEntity<List<EmployeeDTO>> findAllByName (@RequestParam (value = "name", defaultValue = "") String name)
	{
		List<Employee> employees = employeeService.findAllByName (name);
		List<EmployeeDTO> listDto = employees.stream ().map (employee -> new EmployeeDTO (employee)).collect (Collectors.toList ());
		return ResponseEntity.ok ().body (listDto);
	}
	
	@ApiOperation (value = "Find all employees by name with pagination", response = EmployeeDTO[].class)
	@PreAuthorize ("hasRole('EMPLOYEE')")
	@GetMapping (path = "/protected/employees/page")
	public ResponseEntity<Page<EmployeeDTO>> findByNamePage 
	(
		@RequestParam (value = "name", defaultValue = "") String name, 
		@RequestParam (value = "page", defaultValue = "0") Integer page, 
		@RequestParam (value = "size", defaultValue = "24") Integer size, 
		@RequestParam (value = "direction", defaultValue = "ASC") String direction, 
		@RequestParam (value = "orderBy", defaultValue = "name") String orderBy
	)
	{
		Page<Employee> employees = employeeService.findByNamePage (name, page, size, direction, orderBy);
		Page<EmployeeDTO> pageDto = employees.map (employee -> new EmployeeDTO (employee));
		return ResponseEntity.ok ().body (pageDto);
	}
	
	@ApiOperation (value = "Find employee by email", response = EmployeeDTO.class)
	@PreAuthorize ("hasRole('ADMIN')")
	@GetMapping (path = "/admin/employees/email")
	public ResponseEntity<EmployeeDTO> findByEmail (@RequestParam (value = "value") String email)
	{
		Employee employee = employeeService.findByEmail (email);
		EmployeeDTO dto = new EmployeeDTO (employee);
		return ResponseEntity.ok ().body (dto);
	}
	
	@ApiOperation (value = "Find employee by SSN", response = EmployeeDTO.class)
	@PreAuthorize ("hasRole('ADMIN')")
	@GetMapping (path = "/admin/employees/ssn")
	public ResponseEntity<EmployeeDTO> findBySSN (@RequestParam (value = "value") String ssn)
	{
		Employee employee = employeeService.findBySocialSecurityNumber (ssn);
		EmployeeDTO dto = new EmployeeDTO (employee);
		return ResponseEntity.ok ().body (dto);
	}
	
	@ApiOperation (value = "Insert new employee")
	@PreAuthorize ("hasRole('ADMIN')")
	@PostMapping (path = "/admin/employees")
	public ResponseEntity<Void> insert (@Valid @RequestBody EmployeeNewDTO dto)
	{
		Employee employee = employeeService.fromDTO (dto);		
		employee = (Employee) personService.insert (employee);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest ().path("/{id}").buildAndExpand (employee.getId ()).toUri ();
		return ResponseEntity.created (location).build ();		
	}
	
	@ApiOperation (value = "Update employee's data")
	@PreAuthorize ("hasRole('EMPLOYEE')")
	@PutMapping (path = "/protected/employees/{id}")
	public ResponseEntity<Void> update (@Valid @RequestBody EmployeeDTO dto, @PathVariable Integer id)
	{
		Employee employee = employeeService.fromDTO (dto);
		employee.setId (id);
		personService.update (employee);
		return ResponseEntity.noContent ().build ();
	}
}