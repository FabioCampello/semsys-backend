package com.williamdsw.semsys.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.williamdsw.semsys.services.MockDatabaseService;
import com.williamdsw.semsys.services.mail.EmailService;
import com.williamdsw.semsys.services.mail.SmtpMailService;

@Configuration
@Profile ("prod")
public class ProdConfig 
{
	// FIELDS
	
	@Value ("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;
	
	@Autowired private MockDatabaseService mockDatabaseService;
	
	// BEANS
	
	@Bean
	public boolean mockDatabase () throws Exception
	{
		if (!strategy.equals ("create")) 
		{
			return false;
		}
		
		mockDatabaseService.mockDatabase ();
		return true;
	}
	
	@Bean
	public EmailService emailService ()
	{
		return new SmtpMailService ();
	}
}