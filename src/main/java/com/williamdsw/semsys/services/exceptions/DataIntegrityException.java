package com.williamdsw.semsys.services.exceptions;

public class DataIntegrityException extends RuntimeException 
{
	// FIELDS
	
	private static final long serialVersionUID = 1L;

	// CONSTRUCTOR
	
	public DataIntegrityException (String message) 
	{
		super (message);
	}
}