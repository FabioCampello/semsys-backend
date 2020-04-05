package com.williamdsw.semsys.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.williamdsw.semsys.domain.Person;
import com.williamdsw.semsys.domain.dto.PersonDTO;
import com.williamdsw.semsys.services.PersonService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping (path = "/v1")
public class PersonResource 
{
	// FIELDS
	
	@Autowired private PersonService personService;
	
	// ENDPOINTS
	
	@ApiOperation (value = "Find all persons", response = PersonDTO[].class)
	@PreAuthorize ("hasRole('ADMIN')")
	@GetMapping (path = "/admin/persons")
	public ResponseEntity<List<PersonDTO>> findAllPersons ()
	{
		List<Person> persons = personService.findAllPersons ();
		List<PersonDTO> listDto = persons.stream ().map (person -> new PersonDTO (person)).collect (Collectors.toList ());
		return ResponseEntity.ok ().body (listDto);
	}
	
	@ApiOperation (value = "Find a person (employee or student) by id")
	@PreAuthorize ("hasAnyRole('EMPLOYEE') or hasAnyRole('STUDENT')")
	@GetMapping (path = "/protected/persons/{id}")
	public ResponseEntity<?> findById (@PathVariable Integer id)
	{
		Person person = personService.findById (id);
		return ResponseEntity.ok ().body (person);
	}
	
	@ApiOperation (value = "Find person by SSN", response = PersonDTO.class)
	@GetMapping (path = "/public/persons/ssn")
	public ResponseEntity<PersonDTO> findBySSN (@RequestParam (value = "value") String ssn)
	{
		Person person = personService.findBySocialSecurityNumber (ssn);
		PersonDTO dto = new PersonDTO (person);
		return ResponseEntity.ok ().body (dto);
	}
	
	@ApiOperation (value = "Find person by email", response = PersonDTO.class)
	@GetMapping (path = "/public/persons/email")
	public ResponseEntity<PersonDTO> findByEmail (@RequestParam (value = "value") String email)
	{
		Person person = personService.findByEmail (email);
		PersonDTO dto = new PersonDTO (person);
		return ResponseEntity.ok ().body (dto);
	}

	@ApiOperation (value = "Delete a person by id")
	@PreAuthorize ("hasRole('ADMIN')")
	@DeleteMapping (path = "/admin/persons/{id}")
	public ResponseEntity<Void> deleteById (@PathVariable Integer id)
	{
		personService.deleteById (id);
		return ResponseEntity.noContent ().build ();
	}
	
	@ApiOperation (value = "Upload profile picture of current user")
	@PreAuthorize ("hasRole('EMPLOYEE') or hasRole('STUDENT')")
	@PostMapping (path = "/protected/persons/upload-picture")
	public ResponseEntity<Void> uploadProfilePicture (@RequestParam (name = "file") MultipartFile multipartFile)
	{
		URI location = personService.uploadProfilePicture (multipartFile);
		return ResponseEntity.created (location).build ();
	}
}