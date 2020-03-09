package com.williamdsw.semsys.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.williamdsw.semsys.domain.Address;
import com.williamdsw.semsys.domain.City;
import com.williamdsw.semsys.domain.SchoolClass;
import com.williamdsw.semsys.domain.Student;
import com.williamdsw.semsys.domain.dto.StudentDTO;
import com.williamdsw.semsys.domain.dto.StudentNewDTO;
import com.williamdsw.semsys.domain.enums.Profile;
import com.williamdsw.semsys.repositories.SchoolClassRepository;
import com.williamdsw.semsys.repositories.StudentRepository;
import com.williamdsw.semsys.services.exceptions.ObjectNotFoundException;
import com.williamdsw.semsys.services.security.UserService;

@Service
public class StudentService 
{
	// FIELDS
	
	@Autowired private StudentRepository studentRepository;
	@Autowired private SchoolClassRepository schoolClassRepository;
	@Autowired private BCryptPasswordEncoder passwordEncoder;
	
	// HELPER FUNCTIONS
	
	public List<Student> findAllStudents ()
	{
		UserService.checkAuthenticatedUser (Profile.EMPLOYEE);		
		return studentRepository.findAll();
	}
	
	public List<Student> findAllByName (String name)
	{
		UserService.checkAuthenticatedUser (Profile.EMPLOYEE);		
		return studentRepository.findAllByNameContainingIgnoreCase (name);
	}	
	
	public Student findByEmail (String email)
	{
		UserService.checkAuthenticatedUser (Profile.EMPLOYEE);
		
		Student Student = studentRepository.findByEmail (email);
		if (Student == null)
		{
			throw new ObjectNotFoundException (String.format ("Student not found for email: %s", email));
		}
		
		return Student;
	}
	
	public Student findBySocialSecurityNumber (String socialSecurityNumber)
	{
		UserService.checkAuthenticatedUser (Profile.EMPLOYEE);
		
		Student Student = studentRepository.findBySocialSecurityNumber (socialSecurityNumber);
		if (Student == null)
		{
			throw new ObjectNotFoundException (String.format ("Student not found for SSN: %s", socialSecurityNumber));
		}
		
		return Student;
	}
	
	// Update - List...
	public Student fromDTO (StudentDTO dto)
	{
		return new Student (dto.getId (), dto.getName (), dto.getEmail (), null, null, null, null, null);
	}
	
	// Insert
	public Student fromDTO (StudentNewDTO dto)
	{
		// STUDENT
		Student student = new Student ();
		student.setId (dto.getId ());
		student.setName (dto.getName ());
		student.setEmail (dto.getEmail ());
		student.setSocialSecurityNumber (dto.getSocialSecurityNumber ());
		student.setBirthdate (dto.getBirthdate ());
		student.setPassword (passwordEncoder.encode (dto.getPassword ()));
		student.setPhoneNumbers (dto.getPhoneNumbers ());
		
		// CITY
		City city = new City (dto.getCityId (), null, null);
		
		// ADDRESS
		Address address = new Address ();
		address.setId (null);
		address.setStreet (dto.getStreet ());
		address.setNumber (dto.getNumber ());
		address.setComplement (dto.getComplement ());
		address.setZipCode (dto.getZipCode ());
		address.setCity (city);
		address.setPerson (student);
		student.setAddress (address);
		
		// SCHOOL CLASS
		Optional<SchoolClass> optional = schoolClassRepository.findById (dto.getSchoolClassId ());
		if (optional == null)
		{
			throw new ObjectNotFoundException ("School Class could not be found!");
		}
		
		SchoolClass schoolClass = optional.get ();
		student.setSchoolClass (schoolClass);
		
		return student;
	}
}