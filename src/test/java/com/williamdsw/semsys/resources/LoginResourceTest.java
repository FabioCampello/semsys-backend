package com.williamdsw.semsys.resources;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.williamdsw.semsys.resources.utils.HeaderUtils;

@ExtendWith (SpringExtension.class)
@SpringBootTest (webEnvironment = WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
@ActiveProfiles (profiles = "test")
public class LoginResourceTest extends GlobalResourceConfigureTest 
{
	// FIELDS
	
	private final String LOGIN_URL = "/login";
	
	// TESTS
	
	@Test
	public void loginWithAdminCredentialsShouldReturnStatusCode200 ()
	{
		String json = HeaderUtils.getCredentialsJson ("039-58-6788", "111");
		HttpEntity<String> entity = new HttpEntity<>(json, null);
		ResponseEntity<String> response = restTemplate.exchange (LOGIN_URL, HttpMethod.POST, entity, String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.OK.value ());
		String bearer = response.getHeaders ().getFirst (HttpHeaders.AUTHORIZATION);
		System.out.println ("Admin Bearer: " + bearer);
	}
	
	@Test
	public void loginWithEmployeeCredentialsShouldReturnStatusCode200 ()
	{
		String json = HeaderUtils.getCredentialsJson ("554-90-1122", "222");
		HttpEntity<String> entity = new HttpEntity<>(json, null);
		ResponseEntity<String> response = restTemplate.exchange (LOGIN_URL, HttpMethod.POST, entity, String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.OK.value ());
		String bearer = response.getHeaders ().getFirst (HttpHeaders.AUTHORIZATION);
		System.out.println ("Employee Bearer: " + bearer);
	}
	
	@Test
	public void loginWithStudentCredentialsShouldReturnStatusCode200 ()
	{
		String json = HeaderUtils.getCredentialsJson ("500-28-0871", "555");
		HttpEntity<String> entity = new HttpEntity<>(json, null);
		ResponseEntity<String> response = restTemplate.exchange (LOGIN_URL, HttpMethod.POST, entity, String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.OK.value ());
		String bearer = response.getHeaders ().getFirst (HttpHeaders.AUTHORIZATION);
		System.out.println ("Student Bearer: " + bearer);
	}
	
	// Include <artifactId> httpclient </artifactId> to solve 401 - UNAUTHORIZED issue
	@Test
	public void loginWithWrongCredentialsShouldReturnStatus401 ()
	{
		String json = HeaderUtils.getCredentialsJson ("123-45-6789", "000");
		HttpEntity<String> entity = new HttpEntity<>(json, null);
		ResponseEntity<String> response = restTemplate.exchange (LOGIN_URL, HttpMethod.POST, entity, String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.UNAUTHORIZED.value ());
		System.out.println (response.getBody ());
	}
}