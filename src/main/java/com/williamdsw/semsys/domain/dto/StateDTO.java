package com.williamdsw.semsys.domain.dto;

import java.io.Serializable;
import com.williamdsw.semsys.domain.State;

public class StateDTO implements Serializable
{
	// FIELDS
	
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private String abbreviation;
	
	// CONSTRUCTORS
	
	public StateDTO () {}
	public StateDTO (State state) 
	{
		super ();
		this.id = state.getId ();
		this.name = state.getName ();
		this.abbreviation = state.getAbbreviation ();
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
	
	public String getAbbreviation () 
	{
		return abbreviation;
	}
	
	public void setAbbreviation (String abbreviation) 
	{
		this.abbreviation = abbreviation;
	}
}