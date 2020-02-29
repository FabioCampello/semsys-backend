package com.williamdsw.semsys.services.mail;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;

public class MockMailService extends AbstractEmailService 
{
	// FIELDS
	
	private static final Logger LOG = LoggerFactory.getLogger (MockMailService.class);
	
	// OVERRIDED FUNCTIONS
	
	@Override
	public void sendEmail (SimpleMailMessage message) 
	{
		LOG.info ("Simulating sending email...");
		LOG.info (message.toString ());
		LOG.info ("Email sent!");
	}
	
	@Override
	public void sendHtmlEmail(MimeMessage message) 
	{
		LOG.info ("Simulating sending HTML email...");
		LOG.info (message.toString ());
		LOG.info ("Email sent!");
	}
}
