package com.williamdsw.semsys.domain.dto;

import java.io.Serializable;
import com.williamdsw.semsys.domain.City;

public class CityDTO implements Serializable
{
	// FIELDS
	
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	
	// CONSTRUCTORS
	
	public CityDTO () {}
	public CityDTO (City city) 
	{
		super ();
		this.id = city.getId ();
		this.name = city.getName ();
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
}