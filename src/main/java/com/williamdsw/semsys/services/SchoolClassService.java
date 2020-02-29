package com.williamdsw.semsys.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
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
	
	public List<SchoolClass> findByCourse (Integer courseId)
	{
		courseService.findById (courseId);
		return schoolClassRepository.findByCourseIdOrderByNameAsc (courseId);
	}
	
	public Page<SchoolClass> findByCourseAndNamePage (Integer courseId, String name, Integer page, Integer size, String direction, String orderBy)
	{
		UserService.checkAuthenticatedUser (Profile.EMPLOYEE);
		courseService.findById (courseId);
		PageRequest pageRequest = PageRequest.of (page, size, Direction.valueOf (direction), orderBy);
		return schoolClassRepository.findByCourseIdAndNameContainingIgnoreCase (courseId, name, pageRequest);
	}
}