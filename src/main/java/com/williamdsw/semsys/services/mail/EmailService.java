package com.williamdsw.semsys.services.mail;

import javax.mail.internet.MimeMessage;
import org.springframework.mail.SimpleMailMessage;
import com.williamdsw.semsys.domain.MeetingSchedule;
import com.williamdsw.semsys.domain.Person;
import com.williamdsw.semsys.domain.Report;

public interface EmailService 
{
	// HELPER FUNCTIONS
	
	public void sendMeetingScheduleConfirmationEmail (MeetingSchedule schedule);
	public void sendMeetingScheduleConfirmationHtmlEmail (MeetingSchedule schedule);
	public void sendIssuedReportEmail (Report report);
	public void sendIssuedReportHtmlEmail (Report report);
	public void sendEmail (SimpleMailMessage message);
	public void sendHtmlEmail (MimeMessage message);
	public void sendNewPasswordEmail (Person person, String newPassword);
}