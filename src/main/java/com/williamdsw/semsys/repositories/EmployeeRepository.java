package com.williamdsw.semsys.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.williamdsw.semsys.domain.Employee;

public interface EmployeeRepository extends PersonRepository<Employee>
{
	@Override
	public Employee findByEmail (String email);
	
	@Override
	public Employee findBySocialSecurityNumber (String socialSecurityNumber);
	
	@Override
	public Page<Employee> findByNameContainingIgnoreCase (String name, Pageable pageRequest);
}