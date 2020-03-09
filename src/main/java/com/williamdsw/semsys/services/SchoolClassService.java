package com.williamdsw.semsys.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.williamdsw.semsys.domain.SchoolClass;
import com.williamdsw.semsys.domain.enums.Profile;
import com.williamdsw.semsys.repositories.SchoolClassRepository;
import com.williamdsw.semsys.services.security.UserService;

@Service
public class SchoolClassService 
{
	// FIELDS
	
	@Autowired private SchoolClassRepository schoolClassRepository;
	@Autowired private CourseService courseService;
	
	// HELPER FUNCTIONS
	
	public List<SchoolClass> findByCourseAndName (Integer courseId, String name)
	{
		UserService.checkAuthenticatedUser (Profile.EMPLOYEE);
		courseService.findById (courseId);
		return schoolClassRepository.findByCourseIdAndNameContainingIgnoreCase (courseId, name);
	}
}