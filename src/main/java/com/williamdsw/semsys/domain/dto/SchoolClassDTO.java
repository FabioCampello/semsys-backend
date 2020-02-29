package com.williamdsw.semsys.domain.dto;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.williamdsw.semsys.domain.SchoolClass;

public class SchoolClassDTO implements Serializable
{
	// FIELDS 
	
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	
	@JsonFormat (pattern = "yyyy-MM-dd")
	private Date start;
	
	@JsonFormat (pattern = "yyyy-MM-dd")
	private Date end;
	
	// CONSTRUCTORS
	
	public SchoolClassDTO () {}
	public SchoolClassDTO (SchoolClass schoolClass) 
	{
		super ();
		this.id = schoolClass.getId ();
		this.name = schoolClass.getName ();
		this.start = schoolClass.getStart ();
		this.end = schoolClass.getEnd ();
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
}