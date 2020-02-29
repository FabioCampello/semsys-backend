package com.williamdsw.semsys.repositories;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.williamdsw.semsys.domain.SchoolClass;

@Repository
public interface SchoolClassRepository extends JpaRepository<SchoolClass, Integer>
{
	@Transactional (readOnly = true)
	public List<SchoolClass> findByCourseIdOrderByNameAsc (Integer courseId);
	
	@Transactional (readOnly = true)
	public Page<SchoolClass> findByCourseIdAndNameContainingIgnoreCase (Integer courseId, String name, Pageable pageable);
}