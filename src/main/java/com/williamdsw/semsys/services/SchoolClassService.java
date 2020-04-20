package com.williamdsw.semsys.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.williamdsw.semsys.domain.Course;
import com.williamdsw.semsys.domain.SchoolClass;
import com.williamdsw.semsys.domain.dto.SchoolClassNewDTO;
import com.williamdsw.semsys.domain.enums.Profile;
import com.williamdsw.semsys.repositories.CourseRepository;
import com.williamdsw.semsys.repositories.SchoolClassRepository;
import com.williamdsw.semsys.services.security.UserService;

@Service
public class SchoolClassService 
{
	// FIELDS
	
	@Autowired private SchoolClassRepository schoolClassRepository;
	@Autowired private CourseRepository courseRepository;
	@Autowired private CourseService courseService;
	
	// HELPER FUNCTIONS
	
	public List<SchoolClass> findByCourse (Integer courseId)
	{
		courseService.findById (courseId);
		return schoolClassRepository.findByCourseId (courseId);
	}
	
	public List<SchoolClass> findByCourseAndName (Integer courseId, String name)
	{
		courseService.findById (courseId);
		return schoolClassRepository.findByCourseIdAndNameContainingIgnoreCase (courseId, name);
	}
	
	public SchoolClass insert (SchoolClass schoolClass)
	{
		UserService.checkAuthenticatedUser (Profile.ADMIN);
		schoolClass.setId (null);
		schoolClass = schoolClassRepository.save (schoolClass);
		courseRepository.save (schoolClass.getCourse ());
		
		return schoolClass;
	}
	
	public void deleteAllInList (List<SchoolClass> schoolClasses)
	{
		UserService.checkAuthenticatedUser (Profile.ADMIN);
		schoolClassRepository.deleteAll(schoolClasses);
	}
	
	public SchoolClass fromDTO (SchoolClassNewDTO dto)
	{
		SchoolClass schoolClass = new SchoolClass ();
		schoolClass.setId (dto.getId ());
		schoolClass.setName (dto.getName ());
		schoolClass.setStart (dto.getStart());
		schoolClass.setEnd (dto.getEnd ());
		
		// COURSE
		Course course = courseService.findById(dto.getCourseId ());
		schoolClass.setCourse (course);
		
		return schoolClass;
	}
}