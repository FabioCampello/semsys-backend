package com.williamdsw.semsys.services.mail;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class SmtpMailService extends AbstractEmailService 
{
	// FIELDS
	
	private static final Logger LOG = LoggerFactory.getLogger (SmtpMailService.class);
	
	@Autowired private MailSender mailSender;
	@Autowired private JavaMailSender javaMailSender;
	
	// OVERRIDED FUNCTIONS
	
	@Override
	public void sendEmail (SimpleMailMessage message) 
	{
		LOG.info ("Sending email...");
		mailSender.send (message);
		LOG.info ("Email sent!");
	}
	
	@Override
	public void sendHtmlEmail (MimeMessage message) 
	{
		LOG.info ("Sending HTML email...");
		javaMailSender.send (message);
		LOG.info ("Email sent!");		
	}
}