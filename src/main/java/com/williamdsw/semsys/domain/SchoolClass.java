package com.williamdsw.semsys.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class SchoolClass implements Serializable
{
	// FIELDS 
	
	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue (strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	
	@JsonIgnore @ManyToOne @JoinColumn (name = "course_id")
	private Course course;
	
	@JsonFormat (pattern = "yyyy-MM-dd")
	private Date start;
	
	@JsonFormat (pattern = "yyyy-MM-dd")
	private Date end;
	
	@JsonIgnore @OneToMany (mappedBy = "schoolClass")
	private List<Student> students = new ArrayList<>();
	
	// CONSTRUCTORS
	
	public SchoolClass () {}
	public SchoolClass (Integer id, String name, Course course, Date start, Date end) 
	{
		super ();
		this.id = id;
		this.name = name;
		this.course = course;
		this.start = start;
		this.end = end;
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
	
	public Course getCourse () 
	{
		return course;
	}
	
	public void setCourse (Course course) 
	{
		this.course = course;
	}
	
	public Date getStart () 
	{
		return start;
	}
	
	public void setStart (Date begin) 
	{
		this.start = begin;
	}
	
	public Date getEnd () 
	{
		return end;
	}
	
	public void setEnd (Date end) 
	{
		this.end = end;
	}
	
	public List<Student> getStudents () 
	{
		return students;
	}
	
	public void setStudents (List<Student> students) 
	{
		this.students = students;
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
		SchoolClass other = (SchoolClass) obj;
		if (id == null) 
		{
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}