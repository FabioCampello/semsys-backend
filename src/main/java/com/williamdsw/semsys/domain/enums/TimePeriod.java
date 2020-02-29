package com.williamdsw.semsys.domain.enums;

import com.williamdsw.semsys.services.exceptions.DataIntegrityException;

public enum TimePeriod 
{
	// VALUES
	
	MORNING (1, "Morning"),
	AFTERNOON (2, "Afternoon"),
	EVENING (3, "Evening");
	
	// FIELDS
	
	private Integer code;
	private String description;
	
	// CONSTRUCTOR
	
	private TimePeriod (Integer code, String description)
	{
		this.code = code;
		this.description = description;
	}
	
	// GETTERS
	
	public Integer getCode () 
	{
		return code;
	}
	
	public String getDescription () 
	{
		return description;
	}
	
	// HELPER FUNCTIONS
	
	public static TimePeriod toEnum (Integer code)
	{
		if (code == null) { return null; }
		
		for (TimePeriod profile : TimePeriod.values ())
		{
			if (profile.getCode ().equals (code))
			{
				return profile;
			}
		}
		
		throw new IllegalArgumentException (String.format ("Time Period invalid for code : %s", code));
	}
	
	public static TimePeriod toEnum (String description)
	{
		if (description == null) { return null; }
		
		for (TimePeriod profile : TimePeriod.values ())
		{
			if (profile.getDescription ().equals (description))
			{
				return profile;
			}
		}
		
		throw new DataIntegrityException (String.format ("Time Period invalid for description : %s", description));
	}
}