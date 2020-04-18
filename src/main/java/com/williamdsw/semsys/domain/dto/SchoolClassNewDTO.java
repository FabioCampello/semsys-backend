package com.williamdsw.semsys.domain.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

public class SchoolClassNewDTO implements Serializable
{
	// FIELDS 
	
	private static final long serialVersionUID = 1L;
	private Integer id;
	
	@NotNull(message = "Name is required")
	private String name;
	
	@NotNull(message = "Start date is required")
	private Date start;
	
	@NotNull(message = "End date is required")
	private Date end;
	
	@NotNull(message = "Course is required")
	private Integer courseId;
	
	// CONSTRUCTORS
	
	public SchoolClassNewDTO () {}
	
	// GETTERS / SETTERS
	
	public Integer getId () 
	{
		return id;
	}
	
	public void setId (Integer id) 
	{
		this.id = id;
	}
	
	public String getName () 
	{
		return name;
	}
	
	public void setName (String name) 
	{
		this.name = name;
	}
	
	public Date getStart () 
	{
		return start;
	}
	
	public void setStart (Date start) 
	{
		this.start = start;
	}
	
	public Date getEnd () 
	{
		return end;
	}
	
	public void setEnd (Date end) 
	{
		this.end = end;
	}
	
	public Integer getCourseId () 
	{
		return courseId;
	}
	
	public void setCourseId (Integer courseId) 
	{
		this.courseId = courseId;
	}
}