package com.williamdsw.semsys.services.mail;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.williamdsw.semsys.domain.MeetingSchedule;
import com.williamdsw.semsys.domain.Person;
import com.williamdsw.semsys.domain.Report;
import com.williamdsw.semsys.mail.MailConstants;

public abstract class AbstractEmailService implements EmailService
{
	// FIELDS
	
	private String sender = MailConstants.getDefaultSender ();
	
	@Autowired private TemplateEngine templateEngine;
	@Autowired private JavaMailSender javaMailSender;
	
	// OVERRIDED FUNCTIONS
	
	@Override
	public void sendMeetingScheduleConfirmationEmail (MeetingSchedule schedule) 
	{
		SimpleMailMessage message = prepareSimpleMailMessage (schedule.getStudent ().getEmail (), "A new meeting has been scheduled", schedule.toString ());
		sendEmail (message);
	}
	
	@Override
	public void sendMeetingScheduleConfirmationHtmlEmail (MeetingSchedule schedule) 
	{
		try 
		{
			String htmlTemplate = htmlFromTemplate ("schedule", schedule, "schedule/meeting-schedule-confirmation");
			MimeMessage message = prepareMimeMessage (schedule.getStudent ().getEmail (), "A new meeting has been scheduled", htmlTemplate);
			sendHtmlEmail (message);
		} 
		catch (Exception e) 
		{
			sendMeetingScheduleConfirmationEmail (schedule);
		}
	}

	@Override
	public void sendIssuedReportEmail (Report report) 
	{
		SimpleMailMessage message = prepareSimpleMailMessage (report.getSchedule ().getStudent ().getEmail (), "A new report has been issued", report.toString ());
		sendEmail (message);
	}
	
	@Override
	public void sendIssuedReportHtmlEmail (Report report) 
	{
		try 
		{
			String htmlTemplate = htmlFromTemplate ("report", report, "report/issued-report");
			MimeMessage message = prepareMimeMessage (report.getSchedule ().getStudent ().getEmail (), "A new report has been issued", htmlTemplate);
			sendHtmlEmail (message);
		} 
		catch (Exception e) 
		{
			sendIssuedReportEmail (report);
		}
	}
	
	@Override
	public void sendNewPasswordEmail (Person person, String newPassword) 
	{
		SimpleMailMessage message = prepareNewPasswordEmail (person, newPassword);
		sendEmail (message);
	}
	
	// HELPER FUNCTIONS
	
	protected SimpleMailMessage prepareSimpleMailMessage (String recipient, String subject, String text) 
	{
		SimpleMailMessage message = new SimpleMailMessage();

		// Parameters
		message.setTo (recipient);
		message.setFrom (sender);
		message.setSubject (subject);
		message.setSentDate(new Date (System.currentTimeMillis ()));
		message.setText (text);

		return message;
	}
	
	protected MimeMessage prepareMimeMessage (String recipient, String subject, String htmlTemplate) throws MessagingException
	{
		MimeMessage message = javaMailSender.createMimeMessage ();
		MimeMessageHelper helper = new MimeMessageHelper (message, true);
		
		// Parameters
		helper.setTo (recipient);
		helper.setFrom (sender);
		helper.setSubject (subject);
		helper.setSentDate(new Date (System.currentTimeMillis ()));
		helper.setText (htmlTemplate, true);
		
		return message;
	}
	
	protected String htmlFromTemplate (String objectName, Object object, String template)
	{
		Context context = new Context ();
		context.setVariable (objectName, object);
		return templateEngine.process (template, context);
	}
	
	protected SimpleMailMessage prepareNewPasswordEmail (Person person, String newPassword)
	{
		SimpleMailMessage message = new SimpleMailMessage ();
		
		// Parameters
		message.setTo (person.getEmail ());
		message.setFrom (sender);
		message.setSubject ("New password request!");
		message.setSentDate (new Date (System.currentTimeMillis ()));
		message.setText (String.format ("New password generated:\n%s", newPassword));
		
		return message;
	}
}