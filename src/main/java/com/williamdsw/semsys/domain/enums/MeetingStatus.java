package com.williamdsw.semsys.domain.enums;

import com.williamdsw.semsys.services.exceptions.DataIntegrityException;

public enum MeetingStatus 
{
	// VALUES
	
	SCHEDULED (1, "Scheduled"),
	FINISHED (2, "Finished"),
	CANCELED (3, "Canceled");
	
	// FIELDS
	
	private Integer code;
	private String description;
	
	// CONSTRUCTOR
	
	private MeetingStatus (Integer code, String description)
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
	
	public static MeetingStatus toEnum (Integer code)
	{
		if (code == null) { return null; }
		
		for (MeetingStatus profile : MeetingStatus.values ())
		{
			if (profile.getCode ().equals (code))
			{
				return profile;
			}
		}
		
		throw new IllegalArgumentException (String.format ("Meeting Status invalid for code : %s", code));
	}
	
	public static MeetingStatus toEnum (String description)
	{
		if (description == null) { return null; }
		
		for (MeetingStatus profile : MeetingStatus.values ())
		{
			if (profile.getDescription ().equals (description))
			{
				return profile;
			}
		}
		
		throw new DataIntegrityException (String.format ("Meeting Status invalid for description : %s", description));
	}	
}