package com.williamdsw.semsys.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Address implements Serializable
{
	// FIELDS
	
	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue (strategy = GenerationType.IDENTITY)
	private Integer id;
	private String street;
	private String number;
	private String complement;
	private String zipCode;
	
	@JsonIgnore @OneToOne @JoinColumn (name = "person_id")
	private Person person;
	
	@ManyToOne @JoinColumn (name = "city_id")
	private City city;
	
	// CONSTRUCTORS
	
	public Address () {}
	public Address (Integer id, String publicPlace, String number, String complement, String zipCode, Person person, City city) 
	{
		super ();
		this.id = id;
		this.street = publicPlace;
		this.number = number;
		this.complement = complement;
		this.zipCode = zipCode;
		this.person = person;
		this.city = city;
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
	
	public Person getPerson () 
	{
		return person;
	}
	
	public void setPerson (Person person) 
	{
		this.person = person;
	}
	
	public City getCity() {
		return city;
	}
	
	public void setCity(City city) {
		this.city = city;
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
		Address other = (Address) obj;
		if (id == null) 
		{
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}