package com.williamdsw.semsys.domain.dto;

import java.io.Serializable;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class EmailDTO implements Serializable
{
	// FIELDS
	
	private static final long serialVersionUID = 1L;
	
	@NotEmpty (message = "Email is required")
	@Email (message = "Invalid email")
	private String email;
	
	// CONSTRUCTORS
	
	public EmailDTO () {}
	public EmailDTO (String email) 
	{
		this.email = email;
	}
	
	// GETTERS / SETTERS
	
	public String getEmail () 
	{
		return email;
	}
	
	public void setEmail (String email) 
	{
		this.email = email;
	}
}