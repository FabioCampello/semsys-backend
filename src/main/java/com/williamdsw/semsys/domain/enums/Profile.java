package com.williamdsw.semsys.domain.enums;

public enum Profile 
{
	// VALUES
	
	ADMIN (1, "ROLE_ADMIN"),
	EMPLOYEE (2, "ROLE_EMPLOYEE"),
	STUDENT (3, "ROLE_STUDENT");
	
	// FIELDS
	
	private Integer code;
	private String description;
	
	// CONSTRUCTOR
	
	private Profile (Integer code, String description)
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
	
	public static Profile toEnum (Integer code)
	{
		if (code == null) { return null; }
		
		for (Profile profile : Profile.values ())
		{
			if (profile.getCode ().equals (code))
			{
				return profile;
			}
		}
		
		throw new IllegalArgumentException (String.format ("Profile invalid for code : %s", code));
	}
	
}