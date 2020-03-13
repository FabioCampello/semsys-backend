package com.williamdsw.semsys.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.williamdsw.semsys.domain.SchoolClass;
import com.williamdsw.semsys.repositories.SchoolClassRepository;

@Service
public class SchoolClassService 
{
	// FIELDS
	
	@Autowired private SchoolClassRepository schoolClassRepository;
	@Autowired private CourseService courseService;
	
	// HELPER FUNCTIONS
	
	public List<SchoolClass> findByCourseAndName (Integer courseId, String name)
	{
		courseService.findById (courseId);
		return schoolClassRepository.findByCourseIdAndNameContainingIgnoreCase (courseId, name);
	}
}