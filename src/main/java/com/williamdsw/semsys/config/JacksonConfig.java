package com.williamdsw.semsys.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.williamdsw.semsys.domain.Employee;
import com.williamdsw.semsys.domain.Student;

@Configuration
public class JacksonConfig 
{
	// BEANS
	
	@Bean
	public Jackson2ObjectMapperBuilder objectMapperBuilder ()
	{
		Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder ()
		{
			@Override
			public void configure (ObjectMapper objectMapper) 
			{
				objectMapper.registerSubtypes (Employee.class);
				objectMapper.registerSubtypes (Student.class);
				super.configure (objectMapper);
			}
		};
		
		return builder;
	}
}