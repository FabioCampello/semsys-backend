package com.williamdsw.semsys.resources.exceptions;

import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.williamdsw.semsys.services.exceptions.AuthorizationException;
import com.williamdsw.semsys.services.exceptions.DataIntegrityException;
import com.williamdsw.semsys.services.exceptions.FileException;
import com.williamdsw.semsys.services.exceptions.ObjectNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler 
{
	// EXCEPTION HANDLERS
	
	@ExceptionHandler (ObjectNotFoundException.class)
	public ResponseEntity<StandardError> handleObjectNotFoundException (ObjectNotFoundException exception, HttpServletRequest request)
	{
		HttpStatus notFound = HttpStatus.NOT_FOUND;
		StandardError error = new StandardError (System.currentTimeMillis (), notFound.value (), "Not found!", exception.getMessage (), request.getRequestURI ());
		return ResponseEntity.status (notFound).body (error);
	}
	
	@ExceptionHandler (DataIntegrityException.class)
	public ResponseEntity<StandardError> handleDataIntegrityException (DataIntegrityException exception, HttpServletRequest request)
	{
		HttpStatus badRequest = HttpStatus.BAD_REQUEST;
		StandardError error = new StandardError(System.currentTimeMillis(), badRequest.value(), "Data Integrity", exception.getMessage (), request.getRequestURI ());
		return ResponseEntity.status (badRequest).body (error);
	}
	
	@ExceptionHandler (AuthorizationException.class)
	public ResponseEntity<StandardError> handleAuthorizationException (AuthorizationException exception, HttpServletRequest request)
	{
		HttpStatus forbidden = HttpStatus.FORBIDDEN;
		StandardError error = new StandardError(System.currentTimeMillis(), forbidden.value(), "Access Denied", exception.getMessage (), request.getRequestURI ());
		return ResponseEntity.status (forbidden).body (error);
	}
	
	@ExceptionHandler (MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationError> handleMethodArgumentNotValidException (MethodArgumentNotValidException exception, HttpServletRequest request)
	{
		HttpStatus unprocessableEntity = HttpStatus.UNPROCESSABLE_ENTITY;
		ValidationError error = new ValidationError(System.currentTimeMillis (), unprocessableEntity.value (), "Validation Error", exception.getMessage (), request.getRequestURI ());
		
		exception.getBindingResult ().getFieldErrors ().forEach (fieldError -> 
		{
			error.addError (fieldError.getField (), fieldError.getDefaultMessage ());
		});
		
		return ResponseEntity.status (unprocessableEntity).body (error);
	}
	
	@ExceptionHandler (FileException.class)
	public ResponseEntity<StandardError> handleFileException (FileException exception, HttpServletRequest request)
	{
		HttpStatus badRequest = HttpStatus.BAD_REQUEST;
		StandardError error = new StandardError (System.currentTimeMillis (), badRequest.value (), "File Error", exception.getMessage (), request.getRequestURI ());
		return ResponseEntity.status (badRequest).body (error);
	}
	
	@ExceptionHandler (AmazonServiceException.class)
	public ResponseEntity<StandardError> handleAmazonServiceException (AmazonServiceException exception, HttpServletRequest request)
	{
		HttpStatus status = HttpStatus.valueOf (exception.getStatusCode ());
		StandardError error = new StandardError (System.currentTimeMillis (), status.value (), "Amazon Service Error", exception.getMessage (), request.getRequestURI ());
		return ResponseEntity.status (status).body (error);
	}
	
	@ExceptionHandler (AmazonClientException.class)
	public ResponseEntity<StandardError> handleAmazonClientException (AmazonClientException exception, HttpServletRequest request)
	{
		HttpStatus badRequest = HttpStatus.BAD_REQUEST;
		StandardError error = new StandardError (System.currentTimeMillis (), badRequest.value (), "Amazon Client Error", exception.getMessage (), request.getRequestURI ());
		return ResponseEntity.status (badRequest).body (error);
	}
	
	@ExceptionHandler (AmazonS3Exception.class)
	public ResponseEntity<StandardError> handleAmazonS3Client (AmazonS3Exception exception, HttpServletRequest request)
	{
		HttpStatus badRequest = HttpStatus.BAD_REQUEST;
		StandardError error = new StandardError (System.currentTimeMillis (), badRequest.value (), "Amazon S3 Client Error", exception.getMessage (), request.getRequestURI ());
		return ResponseEntity.status (badRequest).body (error);
	}
	
	@ExceptionHandler (MissingServletRequestPartException.class)
	public ResponseEntity<StandardError> handleMissingServletRequestPartException (MissingServletRequestPartException exception, HttpServletRequest request)
	{
		HttpStatus badRequest = HttpStatus.BAD_REQUEST;
		StandardError error = new StandardError (System.currentTimeMillis (), badRequest.value (), "Missing Request Part", exception.getMessage (), request.getRequestURI ());
		return ResponseEntity.status (badRequest).body (error);
	}
}