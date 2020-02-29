package com.williamdsw.semsys.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CollectionTable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.williamdsw.semsys.domain.enums.CourseType;
import com.williamdsw.semsys.domain.enums.TimePeriod;

@Entity
public class Course implements Serializable
{
	// FIELDS 
	
	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue (strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	
	@CollectionTable (name = "time_period")
	private Integer period;
	
	@CollectionTable (name = "course_type")
	private Integer type;
	
	@JsonIgnore @OneToMany (mappedBy = "course")
	private List<SchoolClass> classes = new ArrayList<>();
	
	// CONSTRUCTORS
	
	public Course () {}
	public Course (Integer id, String name, TimePeriod period, CourseType type) 
	{
		super();
		this.id = id;
		this.name = name;
		this.period = period.getCode ();
		this.type = type.getCode ();
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
	
	public TimePeriod getPeriod () 
	{
		return TimePeriod.toEnum (this.period);
	}
	
	public void setPeriod (TimePeriod timePeriod) 
	{
		this.period = timePeriod.getCode ();
	}
	
	public CourseType getType () 
	{
		return CourseType.toEnum (this.type);
	}
	
	public void setType (CourseType courseType) 
	{
		this.type = courseType.getCode ();
	}
	
	public List<SchoolClass> getClasses () 
	{
		return classes;
	}
	
	public void setClasses (List<SchoolClass> classes) 
	{
		this.classes = classes;
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
		Course other = (Course) obj;
		if (id == null) 
		{
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}