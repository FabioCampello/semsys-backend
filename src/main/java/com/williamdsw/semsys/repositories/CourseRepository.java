package com.williamdsw.semsys.repositories;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	public Page<Course> findByPeriod (Integer period, Pageable pageable);
	
	@Transactional (readOnly = true)
	public Page<Course> findByNameContainingIgnoreCase (String name, Pageable pageable);
}