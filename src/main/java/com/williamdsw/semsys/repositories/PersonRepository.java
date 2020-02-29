package com.williamdsw.semsys.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.williamdsw.semsys.domain.Person;

@Repository
public interface PersonRepository<T extends Person> extends JpaRepository<T, Integer>
{
	@Transactional (readOnly = true)
	public T findByEmail (String email);
	
	@Transactional (readOnly = true)
	public T findBySocialSecurityNumber (String socialSecurityNumber);
	
	@Transactional (readOnly = true)
	public Page<T> findByNameContainingIgnoreCase (String name, Pageable pageRequest);	
}