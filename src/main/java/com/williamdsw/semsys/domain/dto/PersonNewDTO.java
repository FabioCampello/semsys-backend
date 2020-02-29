package com.williamdsw.semsys.domain.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

public class PersonNewDTO implements Serializable
{
	// FIELDS
	
	private static final long serialVersionUID = 1L;
	protected Integer id;
	
	@NotEmpty (message = "Name is required")
	@Length (min = 5, max = 120, message = "Name must be between 5 and 120 characters")
	protected String name;
	
	@NotEmpty (message = "Email is required")
	@Email (message = "Invalid email")
	protected String email;
	
	@NotEmpty (message = "SSN is required")
	protected String socialSecurityNumber;
	
	@NotEmpty (message = "Password is required")
	protected String password;
	
	@NotEmpty (message = "At least one phone number is required")
	protected Set<String> phoneNumbers = new HashSet<>();
	
	// -- ADDRESS FIELDS
	
	@NotEmpty (message = "Street is required")
	protected String street;
	
	@NotEmpty (message = "Number is required")
	protected String number;
	protected String complement;
	
	@NotEmpty (message = "Zip Code is required")
	protected String zipCode;
	
	// -- CITY FIELDS
	
	protected Integer cityId;
	
	// CONSTRUCTOR
	
	public PersonNewDTO () {}
	
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
	
	public String getEmail () 
	{
		return email;
	}
	
	public void setEmail (String email) 
	{
		this.email = email;
	}
	
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
	
	public Set<String> getPhoneNumbers () 
	{
		return phoneNumbers;
	}
	
	public void setPhoneNumbers (Set<String> phoneNumbers) 
	{
		this.phoneNumbers = phoneNumbers;
	}
	
	public String getStreet () 
	{
		return street;
	}
	
	public void setStreet (String street) 
	{
		this.street = street;
	}
	
	public String getNumber () 
	{
		return number;
	}
	
	public void setNumber (String number) 
	{
		this.number = number;
	}
	
	public String getComplement () 
	{
		return complement;
	}
	
	public void setComplement (String complement) 
	{
		this.complement = complement;
	}
	
	public String getZipCode () 
	{
		return zipCode;
	}
	
	public void setZipCode (String zipCode) 
	{
		this.zipCode = zipCode;
	}
	
	public Integer getCityId () 
	{
		return cityId;
	}
	
	public void setCityId (Integer cityId) 
	{
		this.cityId = cityId;
	}
}