package com.williamdsw.semsys.domain.enums;

public enum CourseType 
{
	// VALUES
	
	TECHNICAL_COURSE (1, "Technical Course"),
	LICENTIATE (2, "Licentiate"),
	BACHELOR_DEGREE (3, "Bachelor Degree");
	
	// FIELDS
	
	private Integer code;
	private String description;
	
	// CONSTRUCTOR
	
	private CourseType (Integer code, String description)
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
	
	public static CourseType toEnum (Integer code)
	{
		if (code == null) { return null; }
		
		for (CourseType profile : CourseType.values ())
		{
			if (profile.getCode ().equals (code))
			{
				return profile;
			}
		}
		
		throw new IllegalArgumentException (String.format ("Time Period invalid for code : %s", code));
	}
	
	public static CourseType toEnum (String description)
	{
		if (description == null) { return null; }
		
		for (CourseType profile : CourseType.values ())
		{
			if (profile.getDescription ().equals (description))
			{
				return profile;
			}
		}
		
		throw new IllegalArgumentException (String.format ("Time Period invalid for description : %s", description));
	}	
}