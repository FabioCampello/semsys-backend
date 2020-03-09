package com.williamdsw.semsys.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.williamdsw.semsys.domain.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer>
{
	@Transactional (readOnly = true)
	public List<Course> findAllByOrderByName ();
	
	@Transactional (readOnly = true)
	public List<Course> findByPeriod (Integer period);
	
	@Transactional (readOnly = true)
	public List<Course> findByNameContainingIgnoreCase (String name);
}