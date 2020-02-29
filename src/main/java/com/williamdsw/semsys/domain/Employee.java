package com.williamdsw.semsys.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.williamdsw.semsys.domain.enums.Profile;

@Entity
@JsonTypeName (value = "employee")
public class Employee extends Person
{
	// FIELDS
	
	private static final long serialVersionUID = 1L;
	
	@JsonIgnore @OneToMany (mappedBy = "employee")
	private List<MeetingSchedule> schedules = new ArrayList<>();
	
	// CONSTRUCTORS
	
	public Employee () {}
	public Employee (Integer id, String name, String email, String socialSecurityNumber, String password, Address address) 
	{
		super (id, name, email, socialSecurityNumber, password, address);
		this.addProfile (Profile.EMPLOYEE);
	}
	
	// GETTERS / SETTERS
	
	public List<MeetingSchedule> getSchedules () 
	{
		return schedules;
	}
	
	public void setSchedules (List<MeetingSchedule> schedules) 
	{
		this.schedules = schedules;
	}
}