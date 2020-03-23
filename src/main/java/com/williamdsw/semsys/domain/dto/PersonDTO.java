package com.williamdsw.semsys.domain.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;
import com.williamdsw.semsys.domain.Person;

public class PersonDTO implements Serializable
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
	
	protected String type;
	protected Set<String> profiles = new HashSet<>();
	
	// CONSTRUCTORS
	
	public PersonDTO () {}
	public PersonDTO (Person person) 
	{
		super ();
		this.id = person.getId ();
		this.name = person.getName ();
		this.email = person.getEmail ();
		this.type = person.getClass ().getSimpleName ();		
		person.getProfiles ().forEach (profile -> {
			this.profiles.add (profile.toString());
		});
	}
	
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
	
	public String getType () 
	{
		return type;
	}
	
	public void setType (String type) 
	{
		this.type = type;
	}
	
	public Set<String> getProfiles () 
	{
		return profiles;
	}
	
	public void setProfiles (Set<String> profiles) 
	{
		this.profiles = profiles;
	}
}