package com.williamdsw.semsys.domain.dto;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.williamdsw.semsys.domain.Report;

public class ReportDTO implements Serializable
{
	// FIELDS
	
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String title;
	private String content;
	
	@JsonFormat (pattern = "yyyy-MM-dd")
	private Date emission;
	private MeetingScheduleDTO schedule;
	
	// CONSTRUCTORS
	
	public ReportDTO () {}
	public ReportDTO (Report report) 
	{
		super ();
		this.id = report.getId ();
		this.title = report.getTitle ();
		this.content = report.getContent ();
		this.emission = report.getEmission ();
		this.schedule = new MeetingScheduleDTO (report.getSchedule ());
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
	
	public MeetingScheduleDTO getSchedule () 
	{
		return schedule;
	}
	
	public void setSchedule (MeetingScheduleDTO schedule) 
	{
		this.schedule = schedule;
	}
}