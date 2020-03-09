package com.williamdsw.semsys.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.williamdsw.semsys.domain.Address;
import com.williamdsw.semsys.domain.City;
import com.williamdsw.semsys.domain.Employee;
import com.williamdsw.semsys.domain.dto.EmployeeDTO;
import com.williamdsw.semsys.domain.dto.EmployeeNewDTO;
import com.williamdsw.semsys.domain.enums.Profile;
import com.williamdsw.semsys.repositories.EmployeeRepository;
import com.williamdsw.semsys.security.UserDetailsSS;
import com.williamdsw.semsys.services.exceptions.AuthorizationException;
import com.williamdsw.semsys.services.exceptions.ObjectNotFoundException;
import com.williamdsw.semsys.services.security.UserService;

@Service
public class EmployeeService 
{
	// FIELDS
	
	@Autowired private EmployeeRepository employeeRepository;
	@Autowired private BCryptPasswordEncoder passwordEncoder;
	
	// HELPER FUNCTIONS
	
	public List<Employee> findAllEmployees ()
	{
		UserDetailsSS user = UserService.getAuthenticated ();
		if (user == null || !user.hasRole (Profile.EMPLOYEE) && !user.hasRole (Profile.STUDENT))
		{
			throw new AuthorizationException ("Access Denied!");
		}
		
		return employeeRepository.findAll ();
	}
	
	public List<Employee> findAllByName (String name)
	{
		UserDetailsSS user = UserService.getAuthenticated ();
		if (user == null | !user.hasRole (Profile.EMPLOYEE) && !user.hasRole (Profile.STUDENT))
		{
			throw new AuthorizationException ("Access Denied!");
		}
		
		return employeeRepository.findAllByNameContainingIgnoreCase (name);
	}
	
	public Employee findByEmail (String email)
	{
		UserService.checkAuthenticatedUser (Profile.ADMIN);
		Employee employee = employeeRepository.findByEmail (email);
		if (employee == null)
		{
			throw new ObjectNotFoundException (String.format ("Employee not found for email: %s", email));
		}
		
		return employee;
	}
	
	public Employee findBySocialSecurityNumber (String socialSecurityNumber)
	{
		UserService.checkAuthenticatedUser (Profile.ADMIN);
		Employee employee = employeeRepository.findBySocialSecurityNumber (socialSecurityNumber);
		if (employee == null)
		{
			throw new ObjectNotFoundException (String.format ("Employee not found for SSN: %s ", socialSecurityNumber));
		}
		
		return employee;
	}
	
	// Update - List...
	public Employee fromDTO (EmployeeDTO dto)
	{
		return new Employee (dto.getId (), dto.getName (), dto.getEmail (), null, null, null);
	}
	
	// Insert
	public Employee fromDTO (EmployeeNewDTO dto)
	{
		// STUDENT
		Employee employee = new Employee ();
		employee.setId (dto.getId ());
		employee.setName (dto.getName ());
		employee.setEmail (dto.getEmail ());
		employee.setSocialSecurityNumber (dto.getSocialSecurityNumber ());
		employee.setPassword (passwordEncoder.encode (dto.getPassword ()));
		employee.setPhoneNumbers (dto.getPhoneNumbers ());
		
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
		address.setPerson (employee);
		employee.setAddress(address);
		
		return employee;
	}
}