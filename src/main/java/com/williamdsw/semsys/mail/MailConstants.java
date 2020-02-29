package com.williamdsw.semsys.mail;

public class MailConstants 
{
	// FIELDS
	
	private static final String DEFAULT_SENDER = "sender@email.com";
	private static final String DEFAULT_RECIPIENT = "recipient@email.com";
	
	// GETTERS
	
	public static String getDefaultSender () 
	{
		return DEFAULT_SENDER;
	}
	
	public static String getDefaultRecipient () 
	{
		return DEFAULT_RECIPIENT;
	}
}