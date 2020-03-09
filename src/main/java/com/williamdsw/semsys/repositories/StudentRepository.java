package com.williamdsw.semsys.repositories;

import java.util.List;

import com.williamdsw.semsys.domain.Student;

public interface StudentRepository extends PersonRepository<Student>
{
	@Override
	public List<Student> findAllByNameContainingIgnoreCase (String name);
	
	@Override
	public Student findByEmail (String email);
	
	@Override
	public Student findBySocialSecurityNumber (String socialSecurityNumber);
}