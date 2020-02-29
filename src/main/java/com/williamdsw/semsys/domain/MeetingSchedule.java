package com.williamdsw.semsys.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.williamdsw.semsys.domain.enums.MeetingStatus;

@Entity
public class MeetingSchedule implements Serializable
{
	// FIELDS
	
	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue (strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private LocalDateTime datetime;
	
	@ManyToOne (cascade = CascadeType.ALL) @JoinColumn (name = "employee_id")
	private Employee employee;
	
	@ManyToOne (cascade = CascadeType.ALL) @JoinColumn (name = "student_id")
	private Student student;
	
	@CollectionTable (name = "meeting_status")
	private Integer status;
	
	@OneToOne (mappedBy = "schedule", cascade = CascadeType.ALL)
	private Report report;
	
	// CONSTRUCTORS
	
	public MeetingSchedule () {}
	public MeetingSchedule(Integer id, LocalDateTime datetime, Employee employee, Student student, MeetingStatus meetingStatus) 
	{
		super ();
		this.id = id;
		this.datetime = datetime;
		this.employee = employee;
		this.student = student;
		this.status = (meetingStatus != null ? meetingStatus.getCode () : null);
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
	
	public Employee getEmployee () 
	{
		return employee;
	}
	
	public void setEmployee (Employee employee) 
	{
		this.employee = employee;
	}
	
	public Student getStudent () 
	{
		return student;
	}
	
	public void setStudent (Student student) 
	{
		this.student = student;
	}
	
	public MeetingStatus getStatus () 
	{
		return (this.status != null ? MeetingStatus.toEnum (this.status) : null);
	}
	
	public void setMeetingStatus (MeetingStatus meetingStatus) 
	{
		this.status = (meetingStatus != null ? meetingStatus.getCode () : null);
	}
	
	public Report getReport () 
	{
		return report;
	}
	
	public void setReport (Report report) 
	{
		this.report = report;
	}
	
	// OVERRIDED FUNCTIONS
	
	@Override
	public int hashCode () 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode ());
		return result;
	}
	
	@Override
	public boolean equals (Object obj) 
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass ())
			return false;
		MeetingSchedule other = (MeetingSchedule) obj;
		if (id == null) 
		{
			if (other.id != null)
				return false;
		} 
		else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public String toString () 
	{
		StringBuilder builder = new StringBuilder ();
		builder.append ("Participants: \n");
		builder.append ("Employee: ");
		builder.append (getEmployee () != null ? getEmployee ().getName () : "Employee");
		builder.append ("\n");
		builder.append ("Student: ");
		builder.append (getStudent () != null ? getStudent ().getName () : "Student");
		builder.append ("\n");
		builder.append ("Meeting date: ");
		builder.append (DateTimeFormatter.ISO_DATE.format (getDatetime ()));
		builder.append (" ");
		builder.append (DateTimeFormatter.ISO_LOCAL_TIME.format (getDatetime ()));
		return builder.toString ();
	}
}