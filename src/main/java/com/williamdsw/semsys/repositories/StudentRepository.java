package com.williamdsw.semsys.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.williamdsw.semsys.domain.Student;

public interface StudentRepository extends PersonRepository<Student>
{
	@Override
	public List<Student> findAllByNameContainingIgnoreCase (String name);
	
	@Override
	public Student findByEmail (String email);
	
	@Override
	public Student findBySocialSecurityNumber (String socialSecurityNumber);
	
	@Override
	public Page<Student> findByNameContainingIgnoreCase (String name, Pageable pageRequest);
}