package com.williamdsw.semsys.services.aws.util;

public class ImageUtil 
{
	// FIELDS
	
	private final static Integer IMAGE_SIZE = 200;
	
	// GETTERS
	
	public static Integer getImageSize () 
	{
		return IMAGE_SIZE;
	}
	
	// HELPER FUNCTIONS
	
	public static String buildFileName (String fileName)
	{
		try 
		{
			fileName = fileName.toLowerCase ();
			fileName = fileName.replace (" ", "-");
			fileName = fileName.replace ("_", "-");
			fileName = fileName.concat (".jpg");
			return fileName;
		} 
		catch (Exception e) 
		{
			return null;
		}
	}
}
