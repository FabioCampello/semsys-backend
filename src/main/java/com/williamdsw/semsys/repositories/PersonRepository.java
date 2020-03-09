package com.williamdsw.semsys.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.williamdsw.semsys.domain.Person;

@Repository
public interface PersonRepository<T extends Person> extends JpaRepository<T, Integer>
{
	@Transactional (readOnly = true)
	public List<T> findAllByNameContainingIgnoreCase (String name);
	
	@Transactional (readOnly = true)
	public T findByEmail (String email);
	
	@Transactional (readOnly = true)
	public T findBySocialSecurityNumber (String socialSecurityNumber);	
}