package com.williamdsw.semsys.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.williamdsw.semsys.domain.Course;
import com.williamdsw.semsys.domain.SchoolClass;
import com.williamdsw.semsys.domain.dto.CourseNewDTO;
import com.williamdsw.semsys.domain.enums.CourseType;
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
	@Autowired private SchoolClassService schoolClassService;
	
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
	
	public List<Course> findByPeriod (TimePeriod timePeriod)
	{
		UserService.checkAuthenticatedUser (Profile.EMPLOYEE);
		return repository.findByPeriod (timePeriod.getCode ());
	}
	
	public List<Course> findByName (String name)
	{
		UserService.checkAuthenticatedUser (Profile.EMPLOYEE);
		return repository.findByNameContainingIgnoreCase (name);
	}
	
	@Transactional
	public Course insert (Course course) 
	{
		UserService.checkAuthenticatedUser (Profile.ADMIN);
		course.setId (null);
		course = repository.save (course);
		return course;
	}
	
	public void deleteById (Integer id)
	{
		UserService.checkAuthenticatedUser (Profile.ADMIN);
		findById (id);
		
		// School Classes
		List<SchoolClass> schoolClasses = schoolClassService.findByCourse (id);
		if (schoolClasses.size() >= 1)
		{
			schoolClassService.deleteAllInList (schoolClasses);
		}

		repository.deleteById (id);
	}

	public Course fromDTO (CourseNewDTO dto) 
	{
		Course course = new Course ();
		course.setId (dto.getId ());
		course.setName (dto.getName ());
		course.setPeriod (TimePeriod.toEnum (dto.getPeriod ()));
		course.setType (CourseType.toEnum (dto.getType ()));

		return course;
	}
}