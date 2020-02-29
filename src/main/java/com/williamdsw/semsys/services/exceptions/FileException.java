package com.williamdsw.semsys.services.exceptions;

public class FileException extends RuntimeException 
{
	// FIELDS
	
	private static final long serialVersionUID = 1L;

	// CONSTRUCTOR
	
	public FileException (String message) 
	{
		super (message);
	}
}