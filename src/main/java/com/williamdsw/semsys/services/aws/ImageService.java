package com.williamdsw.semsys.services.aws;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.williamdsw.semsys.services.exceptions.FileException;

@Service
public class ImageService 
{
	// HELPER FUNCTIONS
	
	// Creates new image from multipart
	public BufferedImage getJpgImageFromFile (MultipartFile multipartFile)
	{
		// Check file's extension
		String extension = FilenameUtils.getExtension (multipartFile.getOriginalFilename ());
		if (!extension.equals ("png") && !extension.equals ("jpg"))
		{
			throw new FileException ("Only PNG and JPG files are allowed");
		}
		
		try 
		{
			BufferedImage image = ImageIO.read (multipartFile.getInputStream ());
			if (extension.equals ("png"))
			{
				image = convertPngToJpg (image);
			}
			
			return image;
		} 
		catch (IOException ex) 
		{
			throw new FileException ("File reading error");
		}		
	}
	
	// Draws a new JPG image based on PNG image
	private BufferedImage convertPngToJpg (BufferedImage image)
	{
		BufferedImage newImage = new BufferedImage (image.getWidth (), image.getHeight (), BufferedImage.TYPE_INT_RGB);
		newImage.createGraphics ().drawImage (image, 0, 0, Color.WHITE, null);
		return newImage;
	}
	
	// Returns the input stream from image
	public InputStream getInputStream (BufferedImage image, String extension)
	{
		try 
		{
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream ();
			ImageIO.write (image, extension, outputStream);
			return new ByteArrayInputStream (outputStream.toByteArray ());
		} 
		catch (IOException ex) 
		{
			throw new FileException ("File reading error");
		}
	}
	
	// Crops image starting from center point
	public BufferedImage cropSquare (BufferedImage image)
	{
		int minSize = (image.getHeight () <= image.getWidth ()) ? image.getHeight () : image.getWidth ();
		int x = (image.getWidth () / 2) - (minSize / 2);
		int y = (image.getHeight () / 2) - (minSize / 2);
		return Scalr.crop (image, x, y, minSize, minSize);
	}
	
	// Resizes image with Ultra Quality and given size
	public BufferedImage resize (BufferedImage image, int size)
	{
		return Scalr.resize (image, Scalr.Method.ULTRA_QUALITY, size);
	}
}