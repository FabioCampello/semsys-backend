package com.williamdsw.semsys.domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.Size;

@Entity
public class Report implements Serializable
{
	// FIELDS
	
	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue (strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Size (max = 30)
	private String title;
	
	@Size (max = 1000)
	private String content;
	private Date emission;
	
	@OneToOne @JoinColumn (name = "schedule_id")
	private MeetingSchedule schedule;
	
	// CONSTRUCTORS
	
	public Report () {}
	public Report (Integer id, String title, String content, Date emission, MeetingSchedule schedule) 
	{
		super ();
		this.id = id;
		this.title = title;
		this.content = content;
		this.emission = emission;
		this.schedule = schedule;
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
	
	public String getTitle () 
	{
		return title;
	}
	
	public void setTitle (String title) 
	{
		this.title = title;
	}
	
	public String getContent () 
	{
		return content;
	}
	
	public void setContent (String content) 
	{
		this.content = content;
	}
	
	public Date getEmission () 
	{
		return emission;
	}
	
	public void setEmission (Date emission) 
	{
		this.emission = emission;
	}
	
	public MeetingSchedule getSchedule () 
	{
		return schedule;
	}
	
	public void setSchedule (MeetingSchedule schedule) 
	{
		this.schedule = schedule;
	}
	
	// OVERRIDED FUNCTIONS
	
	@Override
	public int hashCode () 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	
	@Override
	public boolean equals (Object obj) 
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Report other = (Report) obj;
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
		SimpleDateFormat dateFormat = new SimpleDateFormat ("yyyy/MM/dd");
		Employee employee = (getSchedule () != null ? getSchedule ().getEmployee () : new Employee (null, "Employee", null, null, null, null));
		Student student = (getSchedule () != null ? getSchedule ().getStudent () : new Student (null, "Student", null, null, null, null, null, null));
		
		StringBuilder builder = new StringBuilder ();
		builder.append ("Issuer: ").append (employee.getName ()).append ("\n");
		builder.append ("About: ").append (student.getName ()).append ("\n\n");
		builder.append (getTitle ()).append ("\n");
		builder.append (getContent ()).append ("\n\n\n");
		builder.append ("Issued at ");
		builder.append (dateFormat.format (getEmission ()));
		return builder.toString ();
	}
}