package com.williamdsw.semsys.domain.dto;

import java.io.Serializable;
import com.williamdsw.semsys.domain.Country;

public class CountryDTO implements Serializable
{
	// FIELDS
	
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private String abbreviation;
	
	// CONSTRUCTORS
	
	public CountryDTO () {}
	public CountryDTO (Country country) 
	{
		super ();
		this.id = country.getId ();
		this.name = country.getName ();
		this.abbreviation = country.getAbbreviation ();
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