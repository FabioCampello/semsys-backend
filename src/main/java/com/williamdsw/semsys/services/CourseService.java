package com.williamdsw.semsys.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.williamdsw.semsys.domain.Course;
import com.williamdsw.semsys.domain.enums.Profile;
import com.williamdsw.semsys.domain.enums.TimePeriod;
import com.williamdsw.semsys.repositories.CourseRepository;
import com.williamdsw.semsys.services.exceptions.ObjectNotFoundException;
import com.williamdsw.semsys.services.security.UserService;

@Service
public class CourseService 
{
	// FIELDS
	
	@Autowired private CourseRepository repository;
	
	// HELPER FUNCTIONS
	
	public Course findById (Integer id)
	{
		Optional<Course> course = repository.findById (id);
		return course.orElseThrow (() -> new ObjectNotFoundException (String.format ("Course not found for id : %s", id)));
	}
	
	public List<Course> findAllOrderByName ()
	{
		return repository.findAllByOrderByName ();
	}
	
	public Page<Course> findByPeriod (TimePeriod timePeriod, Integer page, Integer size, String direction, String orderBy)
	{
		UserService.checkAuthenticatedUser (Profile.EMPLOYEE);
		PageRequest pageRequest = PageRequest.of (page, size, Direction.valueOf (direction), orderBy);
		return repository.findByPeriod (timePeriod.getCode (), pageRequest);
	}
	
	public Page<Course> findByName (String name, Integer page, Integer size, String direction, String orderBy)
	{
		UserService.checkAuthenticatedUser (Profile.EMPLOYEE);
		PageRequest pageRequest = PageRequest.of (page, size, Direction.valueOf (direction), orderBy);
		return repository.findByNameContainingIgnoreCase (name, pageRequest);
	}
}