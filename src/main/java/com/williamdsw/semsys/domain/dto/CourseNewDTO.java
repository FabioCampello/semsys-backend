package com.williamdsw.semsys.domain.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

public class CourseNewDTO implements Serializable
{
	// FIELDS 
	
	private static final long serialVersionUID = 1L;
	private Integer id;
	
	@NotNull(message = "Name is required")
	private String name;
	
	@NotNull(message = "Period is required")
	private String period;
	
	@NotNull(message = "Type is required")
	private String type;
	
	// CONSTRUCTORS
	
	public CourseNewDTO () {}
	
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