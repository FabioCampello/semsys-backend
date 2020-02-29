package com.williamdsw.semsys.services;

import java.util.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import com.williamdsw.semsys.domain.Employee;
import com.williamdsw.semsys.domain.MeetingSchedule;
import com.williamdsw.semsys.domain.Report;
import com.williamdsw.semsys.domain.Student;
import com.williamdsw.semsys.domain.dto.ReportDTO;
import com.williamdsw.semsys.domain.dto.ReportNewDTO;
import com.williamdsw.semsys.domain.enums.MeetingStatus;
import com.williamdsw.semsys.domain.enums.Profile;
import com.williamdsw.semsys.repositories.ReportRepository;
import com.williamdsw.semsys.security.UserDetailsSS;
import com.williamdsw.semsys.services.exceptions.AuthorizationException;
import com.williamdsw.semsys.services.exceptions.DataIntegrityException;
import com.williamdsw.semsys.services.exceptions.ObjectNotFoundException;
import com.williamdsw.semsys.services.mail.EmailService;
import com.williamdsw.semsys.services.security.UserService;

@Service
public class ReportService 
{
	// FIELDS
	
	@Autowired private ReportRepository reportRepository;
	@Autowired private PersonService personService;
	@Autowired private MeetingScheduleService meetingScheduleService;
	@Autowired private EmailService emailService;
	
	// HELPER FUNCTIONS
	
	public Report findById (Integer id)
	{
		UserDetailsSS user = UserService.getAuthenticated ();
		if (user == null || !user.hasRole (Profile.EMPLOYEE) && !user.hasRole (Profile.STUDENT))
		{
			throw new AuthorizationException ("Access Denied");
		}
		
		Optional<Report> report = reportRepository.findById (id);
		return report.orElseThrow (() -> new ObjectNotFoundException (String.format ("Report not found for id :%s", id)));
	}
	
	public Report findBySchedule (Integer scheduleId)
	{
		UserDetailsSS user = UserService.getAuthenticated ();
		if (user == null || !user.hasRole (Profile.EMPLOYEE) && !user.hasRole (Profile.STUDENT))
		{
			throw new AuthorizationException ("Access Denied");
		}
		
		MeetingSchedule schedule = meetingScheduleService.findById (scheduleId);
		Report report = reportRepository.findBySchedule (schedule);
		if (report == null)
		{
			throw new ObjectNotFoundException ("No report found for such schedule!");
		}
		
		return report;
	}
	
	public Page<Report> findByEmployee (Integer employeeId, Integer page, Integer size, String direction, String orderBy)
	{
		UserService.checkAuthenticatedUser (Profile.EMPLOYEE);
		Employee employee = (Employee) personService.findById (employeeId);
		PageRequest pageRequest = PageRequest.of (page, size, Direction.valueOf (direction), orderBy);
		return reportRepository.findByScheduleEmployee (employee, pageRequest);
	}
	
	public Page<Report> findByStudent (Integer studentId, Integer page, Integer size, String direction, String orderBy)
	{
		UserDetailsSS user = UserService.getAuthenticated ();
		if (user == null || !user.hasRole (Profile.EMPLOYEE) && !user.hasRole (Profile.STUDENT) ||
			user.hasRole (Profile.STUDENT) && !user.getId().equals (studentId))
		{
			throw new AuthorizationException ("Access Denied");
		}
		
		Student student = (Student) personService.findById (studentId);
		PageRequest pageRequest = PageRequest.of (page, size, Direction.valueOf (direction), orderBy);
		return reportRepository.findByScheduleStudent (student, pageRequest);
	}
	
	public Report insert (Report report)
	{
		UserService.checkAuthenticatedUser (Profile.EMPLOYEE);
		UserDetailsSS user = UserService.getAuthenticated ();
		if (!user.getId ().equals (report.getSchedule ().getEmployee ().getId ()))
		{
			throw new AuthorizationException ("Access Denied");
		}
		
		report.setId (null);
		report.setEmission (new Date (System.currentTimeMillis ()));
		MeetingSchedule schedule = report.getSchedule ();
		schedule.setMeetingStatus (MeetingStatus.FINISHED);
		report = reportRepository.save (report);
		emailService.sendIssuedReportHtmlEmail (report);
		return report;
	}
	
	public Report fromDTO (ReportDTO dto)
	{
		MeetingSchedule schedule = meetingScheduleService.fromDTO (dto.getSchedule ());
		return new Report (dto.getId (), dto.getTitle (), dto.getContent (), dto.getEmission (), schedule);
	}
	
	public Report fromDTO (ReportNewDTO dto)
	{
		// REPORT
		Report report = new Report ();
		report.setId (dto.getId ());
		report.setTitle (dto.getTitle());
		report.setContent (dto.getContent ());
		report.setEmission (dto.getEmission ());
		
		// MEETING SCHEDULE / EMPLOYEE / STUDENT
		
		if (dto.getSchedule () == null)
		{
			throw new ObjectNotFoundException ("Schedule reference is missing!");
		}
		
		MeetingSchedule schedule = meetingScheduleService.findById (dto.getSchedule ().getId ());
		if (!schedule.getStatus ().equals (MeetingStatus.SCHEDULED))
		{
			throw new DataIntegrityException ("Cannot issue report for finished or canceled schedule");
		}
		
		report.setSchedule (schedule);
		
		return report;
	}
}