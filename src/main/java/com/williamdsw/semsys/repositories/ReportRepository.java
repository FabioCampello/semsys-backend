package com.williamdsw.semsys.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.williamdsw.semsys.domain.Employee;
import com.williamdsw.semsys.domain.MeetingSchedule;
import com.williamdsw.semsys.domain.Report;
import com.williamdsw.semsys.domain.Student;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer>
{
	@Transactional (readOnly = true)
	public Report findBySchedule (MeetingSchedule schedule);
	
	@Transactional (readOnly = true)
	public Page<Report> findByScheduleEmployee (Employee employee, Pageable pageable);
	
	@Transactional (readOnly = true)
	public Page<Report> findByScheduleStudent (Student student, Pageable pageable);
}