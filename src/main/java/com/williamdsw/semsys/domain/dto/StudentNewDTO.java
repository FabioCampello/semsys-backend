package com.williamdsw.semsys.domain.dto;

import java.util.Date;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonFormat;

public class StudentNewDTO extends PersonNewDTO
{
	// FIELDS
	
	private static final long serialVersionUID = 1L;
	
	@NotNull (message = "Birthdate is required")
	@JsonFormat (pattern = "yyyy-MM-dd")
	private Date birthdate;
	
	@NotNull (message = "Class is required")
	private Integer schoolClassId;
	
	// CONSTRUCTORS
	
	public StudentNewDTO () {}
	
	// GETTERS / SETTERS
	
	public Date getBirthdate () 
	{
		return birthdate;
	}
	
	public void setBirthdate (Date birthdate) 
	{
		this.birthdate = birthdate;
	}
	
	public Integer getSchoolClassId () 
	{
		return schoolClassId;
	}
	
	public void setSchoolClassId (Integer schooClassId) 
	{
		this.schoolClassId = schooClassId;
	}
}