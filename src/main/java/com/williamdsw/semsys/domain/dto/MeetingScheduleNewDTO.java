package com.williamdsw.semsys.domain.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;

public class MeetingScheduleNewDTO implements Serializable
{
	// FIELDS
	
	private static final long serialVersionUID = 1L;
	private Integer id;

	@NotNull (message = "Schedule Date is required")
	private LocalDateTime datetime;
	
	// IDs
	@NotNull (message = "Employee is required")
	private Integer employeeId;
	
	@NotNull (message = "Student is required")
	private Integer studentId;
	
	// CONSTRUCTORS
	
	public MeetingScheduleNewDTO () {}
	
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
	
	public Integer getEmployeeId () 
	{
		return employeeId;
	}
	
	public void setEmployeeId (Integer employeeId) 
	{
		this.employeeId = employeeId;
	}
	
	public Integer getStudentId () 
	{
		return studentId;
	}
	
	public void setStudentId (Integer studentId) 
	{
		this.studentId = studentId;
	}
}