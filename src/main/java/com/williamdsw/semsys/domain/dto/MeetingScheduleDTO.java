package com.williamdsw.semsys.domain.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.validation.constraints.NotEmpty;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.williamdsw.semsys.domain.MeetingSchedule;

public class MeetingScheduleDTO implements Serializable
{
	// FIELDS
	
	private static final long serialVersionUID = 1L;
	private Integer id;
	
	@JsonFormat (pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime datetime;
	private EmployeeDTO employee;
	private StudentDTO student;
	
	@NotEmpty (message = "Meeting Status is required!")
	private String meetingStatus;
	
	// CONSTRUCTORS
	
	public MeetingScheduleDTO () {}
	public MeetingScheduleDTO (MeetingSchedule schedule) 
	{
		super ();
		this.id = schedule.getId ();
		this.datetime = schedule.getDatetime ();
		this.employee = new EmployeeDTO (schedule.getEmployee ());
		this.student = new StudentDTO (schedule.getStudent ());
		this.meetingStatus = (schedule.getStatus () != null ? schedule.getStatus ().getDescription () : null);
	}
	
	// GETTERS / SETTERS
	
	public Integer getId () 
	{
		return id;
	}
	
	public void setId (Integer id) 
	{
		this.id = id;
	}
	
	public LocalDateTime getDatetime () 
	{
		return datetime;
	}
	
	public void setDatetime (LocalDateTime datetime) 
	{
		this.datetime = datetime;
	}
	
	public EmployeeDTO getEmployee () 
	{
		return employee;
	}
	
	public void setEmployee (EmployeeDTO employeeDto) 
	{
		this.employee = employeeDto;
	}
	
	public StudentDTO getStudent () 
	{
		return student;
	}
	
	public void setStudent (StudentDTO studentDto) 
	{
		this.student = studentDto;
	}
	
	public String getMeetingStatus () 
	{
		return meetingStatus;
	}
	
	public void setMeetingStatus (String meetingStatus) 
	{
		this.meetingStatus = meetingStatus;
	}
}