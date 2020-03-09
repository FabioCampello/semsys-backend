package com.williamdsw.semsys.repositories;

import java.util.List;

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
	public List<Report> findByScheduleEmployee (Employee employee);
	
	@Transactional (readOnly = true)
	public List<Report> findByScheduleStudent (Student student);
}