package com.williamdsw.semsys.domain.dto;

import com.williamdsw.semsys.domain.Employee;

public class EmployeeDTO extends PersonDTO
{
	// FIELDS
	
	private static final long serialVersionUID = 1L;
	
	// CONSTRUCTORS
	
	public EmployeeDTO () {}
	public EmployeeDTO (Employee employee) 
	{
		super (employee);
	}
}