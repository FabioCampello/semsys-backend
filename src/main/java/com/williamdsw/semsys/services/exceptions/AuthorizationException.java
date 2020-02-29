package com.williamdsw.semsys.services.exceptions;

public class AuthorizationException extends RuntimeException 
{
	// FIELDS
	
	private static final long serialVersionUID = 1L;

	// CONSTRUCTOR
	
	public AuthorizationException (String message) 
	{
		super (message);
	}
}