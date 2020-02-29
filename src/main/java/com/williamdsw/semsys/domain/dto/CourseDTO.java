package com.williamdsw.semsys.domain.dto;

import java.io.Serializable;
import com.williamdsw.semsys.domain.Course;

public class CourseDTO implements Serializable
{
	// FIELDS 
	
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private String period;
	private String type;
	
	// CONSTRUCTORS
	
	public CourseDTO () {}
	public CourseDTO (Course course) 
	{
		super ();
		this.id = course.getId ();
		this.name = course.getName ();
		this.period = course.getPeriod ().getDescription ();
		this.type = course.getType ().getDescription ();
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
	
	public String getName () 
	{
		return name;
	}
	
	public void setName (String name) 
	{
		this.name = name;
	}
	
	public String getPeriod () 
	{
		return period;
	}
	
	public void setPeriod (String period) 
	{
		this.period = period;
	}
	
	public String getType () 
	{
		return type;
	}
	
	public void setType (String type) 
	{
		this.type = type;
	}
}