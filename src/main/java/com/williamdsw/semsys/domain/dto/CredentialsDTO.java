package com.williamdsw.semsys.domain.dto;

import java.io.Serializable;

public class CredentialsDTO implements Serializable
{
	// FIELDS
	
	private static final long serialVersionUID = 1L;
	private String socialSecurityNumber;
	private String password;
	
	// CONSTRUCTORS
	
	public CredentialsDTO () {}
	public CredentialsDTO (String socialSecurityNumber, String password) 
	{
		super ();
		this.socialSecurityNumber = socialSecurityNumber;
		this.password = password;
	}
	
	// GETTERS / SETTERS
	
	public String getSocialSecurityNumber () 
	{
		return socialSecurityNumber;
	}
	
	public void setSocialSecurityNumber (String socialSecurityNumber) 
	{
		this.socialSecurityNumber = socialSecurityNumber;
	}
	
	public String getPassword () 
	{
		return password;
	}
	
	public void setPassword (String password) 
	{
		this.password = password;
	}
}