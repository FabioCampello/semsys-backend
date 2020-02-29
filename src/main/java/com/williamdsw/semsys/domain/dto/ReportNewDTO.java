package com.williamdsw.semsys.domain.dto;

import java.io.Serializable;
import java.util.Date;
import javax.validation.constraints.NotEmpty;

public class ReportNewDTO implements Serializable
{
	// FIELDS
	
	private static final long serialVersionUID = 1L;
	private Integer id;
	
	@NotEmpty (message = "Title is required!")
	private String title;
	
	@NotEmpty (message = "Content is required!")
	private String content;
	private Date emission;
	private MeetingScheduleDTO schedule;
	
	// CONSTRUCTORS
	
	public ReportNewDTO () {}
	
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
	
	public MeetingScheduleDTO getSchedule () 
	{
		return schedule;
	}
	
	public void setSchedule (MeetingScheduleDTO schedule) 
	{
		this.schedule = schedule;
	}
}