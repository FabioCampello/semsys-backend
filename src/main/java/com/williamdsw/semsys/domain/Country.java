package com.williamdsw.semsys.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Country implements Serializable
{
	// FIELDS
	
	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue (strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private String abbreviation;
	
	@JsonIgnore @OneToMany (mappedBy = "country")
	private List<State> states = new ArrayList<>();
	
	// CONSTRUCTORS
	
	public Country () {}
	public Country (Integer id, String name, String abbreviation) 
	{
		super ();
		this.id = id;
		this.name = name;
		this.abbreviation = abbreviation;
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
	
	public String getAbbreviation () 
	{
		return abbreviation;
	}
	
	public void setAbbreviation (String abbreviation) 
	{
		this.abbreviation = abbreviation;
	}
	
	public List<State> getStates () 
	{
		return states;
	}
	
	public void setStates (List<State> states) 
	{
		this.states = states;
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
		Country other = (Country) obj;
		if (id == null) 
		{
			if (other.id != null)
				return false;
		} 
		else if (!id.equals(other.id))
			return false;
		return true;
	}
}