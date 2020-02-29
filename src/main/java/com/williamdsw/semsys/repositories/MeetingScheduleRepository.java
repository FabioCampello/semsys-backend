package com.williamdsw.semsys.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.williamdsw.semsys.domain.Employee;
import com.williamdsw.semsys.domain.MeetingSchedule;
import com.williamdsw.semsys.domain.Student;

@Repository
public interface MeetingScheduleRepository extends JpaRepository<MeetingSchedule, Integer>
{
	@Transactional (readOnly = true)
	public List<MeetingSchedule> findByStatus (Integer status);
	
	@Transactional (readOnly = true)
	public Page<MeetingSchedule> findAll (Pageable pageable);
	
	@Transactional (readOnly = true)
	public Page<MeetingSchedule> findByEmployee (Employee employee, Pageable pageable);
	
	@Transactional (readOnly = true)
	public Page<MeetingSchedule> findByStudent (Student student, Pageable pageable);
}