package com.williamdsw.semsys.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import com.williamdsw.semsys.services.MockDatabaseService;
import com.williamdsw.semsys.services.mail.EmailService;
import com.williamdsw.semsys.services.mail.MockMailService;

@Configuration
@Profile ("test")
public class TestConfig 
{
	// FIELDS
	
	@Autowired private MockDatabaseService mockDatabaseService;
	
	// BEANS
	
	@Bean
	public boolean mockDatabase () throws Exception
	{
		mockDatabaseService.mockDatabase();
		return true;
	}
	
	@Bean
	public EmailService emailService ()
	{
		return new MockMailService ();
	}
}