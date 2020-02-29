package com.williamdsw.semsys.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.williamdsw.semsys.domain.enums.Profile;

@Entity
@Inheritance (strategy = InheritanceType.JOINED)
@JsonTypeInfo (use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "@type")
public abstract class Person implements Serializable
{
	// FIELDS
	
	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue (strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private String email;
	private String socialSecurityNumber;
	
	@JsonIgnore
	private String password;
	
	@OneToOne (mappedBy = "person", cascade = CascadeType.ALL)
	private Address address;
	
	@ElementCollection
	@CollectionTable (name = "phone_number")
	private Set<String> phoneNumbers = new HashSet<>();
	
	@JsonIgnore
	@ElementCollection (fetch = FetchType.EAGER)
	@CollectionTable (name = "profile")
	private Set<Integer> profiles = new HashSet<>();
	
	// CONSTRUCTORS
	
	public Person () {}
	public Person (Integer id, String name, String email, String socialSecurityNumber, String password, Address address) 
	{
		super ();
		this.id = id;
		this.name = name;
		this.email = email;
		this.socialSecurityNumber = socialSecurityNumber;
		this.password = password;
		this.address = address;
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
	
	public Address getAddress () 
	{
		return address;
	}
	
	public void setAddress (Address address) 
	{
		this.address = address;
	}
	
	public Set<String> getPhoneNumbers () 
	{
		return phoneNumbers;
	}
	
	public void setPhoneNumbers (Set<String> phoneNumbers) 
	{
		this.phoneNumbers = phoneNumbers;
	}
	
	public Set<Profile> getProfiles() 
	{
		return profiles.stream ().map (code -> Profile.toEnum (code)).collect (Collectors.toSet ());
	}

	// OVERRIDED FUNCTIONS
	
	@Override
	public int hashCode () 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	
	@Override
	public boolean equals (Object obj) 
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		if (id == null) 
		{
			if (other.id != null)
				return false;
		} 
		else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	// HELPER FUNCTIONS
	
	public void addProfile (Profile profile)
	{
		profiles.add (profile.getCode ());
	}
}