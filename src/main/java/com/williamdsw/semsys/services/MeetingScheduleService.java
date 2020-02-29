package com.williamdsw.semsys.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.williamdsw.semsys.domain.Employee;
import com.williamdsw.semsys.domain.MeetingSchedule;
import com.williamdsw.semsys.domain.Person;
import com.williamdsw.semsys.domain.Student;
import com.williamdsw.semsys.domain.dto.MeetingScheduleDTO;
import com.williamdsw.semsys.domain.dto.MeetingScheduleNewDTO;
import com.williamdsw.semsys.domain.enums.MeetingStatus;
import com.williamdsw.semsys.domain.enums.Profile;
import com.williamdsw.semsys.repositories.MeetingScheduleRepository;
import com.williamdsw.semsys.repositories.PersonRepository;
import com.williamdsw.semsys.security.UserDetailsSS;
import com.williamdsw.semsys.services.exceptions.AuthorizationException;
import com.williamdsw.semsys.services.exceptions.DataIntegrityException;
import com.williamdsw.semsys.services.exceptions.ObjectNotFoundException;
import com.williamdsw.semsys.services.mail.EmailService;
import com.williamdsw.semsys.services.security.UserService;

@Service
public class MeetingScheduleService 
{
	// FIELDS
	
	@Autowired private MeetingScheduleRepository scheduleRepository;
	@Autowired private PersonRepository<Person> personRepository;
	@Autowired private PersonService personService;
	@Autowired private EmailService emailService;
	
	// HELPER FUNCTIONS
	
	public MeetingSchedule findById (Integer id)
	{
		UserDetailsSS user = UserService.getAuthenticated ();
		if (user == null || !user.hasRole (Profile.EMPLOYEE) && !user.hasRole (Profile.STUDENT)) 
		{
			throw new AuthorizationException ("Access Denied");
		}
		
		Optional<MeetingSchedule> schedule = scheduleRepository.findById (id);
		return schedule.orElseThrow (() -> new ObjectNotFoundException (String.format ("Meeting Schedule not found for id: %s", id)));
	}

	public List<MeetingSchedule> findByStatus (MeetingStatus status)
	{
		UserService.checkAuthenticatedUser (Profile.EMPLOYEE);
		return scheduleRepository.findByStatus (status.getCode ());
	}

	public Page<MeetingSchedule> findPage (Integer page, Integer size, String direction, String orderBy)
	{
		UserService.checkAuthenticatedUser (Profile.EMPLOYEE);
		PageRequest pageRequest = PageRequest.of (page, size, Direction.valueOf (direction), orderBy);
		return scheduleRepository.findAll (pageRequest);
	}

	public Page<MeetingSchedule> findByEmployee (Integer employeeId, Integer page, Integer size, String direction, String orderBy)
	{
		UserService.checkAuthenticatedUser (Profile.EMPLOYEE);
		Employee employee = (Employee) personService.findById (employeeId);
		PageRequest pageRequest = PageRequest.of (page, size, Direction.valueOf (direction), orderBy);
		return scheduleRepository.findByEmployee (employee, pageRequest);
	}

	public Page<MeetingSchedule> findByStudent (Integer studentId, Integer page, Integer size, String direction, String orderBy)
	{
		UserDetailsSS user = UserService.getAuthenticated ();
		if (user == null || !user.hasRole (Profile.EMPLOYEE) && !user.hasRole (Profile.STUDENT) ||
			user.hasRole (Profile.STUDENT) && !user.getId ().equals (studentId)) 
		{
			throw new AuthorizationException ("Access Denied");
		}
		
		Student student = (Student) personService.findById (studentId);
		PageRequest pageRequest = PageRequest.of (page, size, Direction.valueOf (direction), orderBy);
		return scheduleRepository.findByStudent (student, pageRequest);
	}
	
	@Transactional
	public MeetingSchedule insert (MeetingSchedule schedule)
	{
		UserService.checkAuthenticatedUser (Profile.EMPLOYEE);
		UserDetailsSS user = UserService.getAuthenticated ();
		if (!user.getId ().equals (schedule.getEmployee ().getId ())) 
		{
			throw new AuthorizationException ("Access Denied");
		}
		
		schedule.setId (null);
		schedule.setMeetingStatus (MeetingStatus.SCHEDULED);
		schedule = scheduleRepository.save (schedule);
		personRepository.save (schedule.getEmployee ());
		personRepository.save (schedule.getStudent ());
		emailService.sendMeetingScheduleConfirmationHtmlEmail (schedule);
		return schedule;
	}
	
	@Transactional
	public MeetingSchedule updateStatus (MeetingSchedule schedule)
	{
		UserService.checkAuthenticatedUser (Profile.EMPLOYEE);
		
		MeetingSchedule newSchedule = findById (schedule.getId ());
		
		UserDetailsSS user = UserService.getAuthenticated ();
		if (!user.getId ().equals (newSchedule.getEmployee ().getId ())) 
		{
			throw new AuthorizationException ("Access Denied");
		}
		
		updateData (schedule, newSchedule);
		return scheduleRepository.save (newSchedule);
	}
	
	public MeetingSchedule fromDTO (MeetingScheduleDTO dto)
	{
		return new MeetingSchedule (dto.getId (), dto.getDatetime (), null, null, MeetingStatus.toEnum (dto.getMeetingStatus ()));
	}
	
	public MeetingSchedule fromDTO (MeetingScheduleNewDTO dto)
	{
		MeetingSchedule schedule = new MeetingSchedule ();
		schedule.setId (dto.getId ());
		schedule.setDatetime (dto.getDatetime ());
		schedule.setMeetingStatus (null);
		
		// IDs
		Employee employee = (Employee) personService.findById (dto.getEmployeeId ());
		Student student = (Student) personService.findById (dto.getStudentId ());
		schedule.setEmployee (employee);
		schedule.setStudent (student);
		
		return schedule;
	}

	private void updateData (MeetingSchedule schedule, MeetingSchedule newSchedule)
	{
		if (newSchedule.getStatus ().equals (MeetingStatus.FINISHED) || newSchedule.getStatus ().equals (MeetingStatus.CANCELED))
		{
			throw new DataIntegrityException ("Cannot modify an finished or canceled meeting!");
		}
		
		newSchedule.setMeetingStatus (schedule.getStatus ());
	}
}