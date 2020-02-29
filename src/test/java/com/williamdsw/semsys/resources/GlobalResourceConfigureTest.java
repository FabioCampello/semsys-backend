package com.williamdsw.semsys.resources;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import com.williamdsw.semsys.resources.utils.HeaderUtils;
import com.williamdsw.semsys.security.SecurityConstants;

public class GlobalResourceConfigureTest
{
	// FIELDS
	
	@Autowired protected TestRestTemplate restTemplate;
	@LocalServerPort protected int port;
	    
	private HttpEntity<Void> adminEntity;
	private HttpEntity<Void> employeeEntity;
	private HttpEntity<Void> studentEntity;
	private HttpEntity<Void> wrongEntity;
	
	// CONSTRUCTORS
	
	public GlobalResourceConfigureTest () {}
	
	// GETTERS / SETTERS
	
	protected HttpEntity<Void> getAdminEntity () 
	{
		return adminEntity;
	}
	
	protected HttpEntity<Void> getEmployeeEntity () 
	{
		return employeeEntity;
	}
	
	protected HttpEntity<Void> getStudentEntity () 
	{
		return studentEntity;
	}
	
	protected HttpEntity<Void> getWrongEntity () 
	{
		return wrongEntity;
	}
	
	// HELPER FUNCTIONS -- ANNOTATIONS
	
	@BeforeEach
	public void configureAdminHeaders ()
	{
		String json = HeaderUtils.getCredentialsJson ("039-58-6788", "111");
		ResponseEntity<String> response = restTemplate.postForEntity ("/login", json, String.class);
		HttpHeaders headers = response.getHeaders ();
		this.adminEntity = new HttpEntity<>(headers);
	}
	
	@BeforeEach
	private void configureEmployeeHeaders ()
	{
		String json = HeaderUtils.getCredentialsJson ("554-90-1122", "222");
		ResponseEntity<String> response = restTemplate.postForEntity ("/login", json, String.class);
		HttpHeaders headers = response.getHeaders ();
		this.employeeEntity = new HttpEntity<>(headers);	
	}
	
	@BeforeEach
	private void configureStudentHeaders ()
	{
		String json = HeaderUtils.getCredentialsJson ("500-28-0871", "555");
		ResponseEntity<String> response = restTemplate.postForEntity ("/login", json, String.class);
		HttpHeaders headers = response.getHeaders ();
		this.studentEntity = new HttpEntity<>(headers);	
	}
	
	@BeforeEach
	private void configureWrongHeaders ()
	{
		HttpHeaders headers = new HttpHeaders();
		headers.add (SecurityConstants.getAuthorizationHeader (), "11111");
		this.wrongEntity = new HttpEntity<>(headers);	
	}
}