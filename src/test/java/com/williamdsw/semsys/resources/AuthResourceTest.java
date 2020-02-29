package com.williamdsw.semsys.resources;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.williamdsw.semsys.domain.dto.EmailDTO;

@ExtendWith (SpringExtension.class)
@SpringBootTest (webEnvironment = WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
@ActiveProfiles (profiles = "test")
public class AuthResourceTest extends GlobalResourceConfigureTest 
{
	// FIELDS
	
	private final String FORGOT_PASSWORD_URL = "/v1/public/auth/forgot-password/";
	private final String REFRESH_TOKEN_URL = "/v1/protected/auth/refresh-token/";
	
	// TESTS
	
	// --> FORGOT_PASSWORD_URL = "/v1/public/auth/forgot-password/"
	
	@Test
	public void forgotPasswordWhenEmailDoesExistsReturnStatusCode204 ()
	{
		EmailDTO dto = new EmailDTO ("iommi@email.com");
		HttpEntity<EmailDTO> request = new HttpEntity<EmailDTO>(dto, null);
		ResponseEntity<String> response = restTemplate.exchange (FORGOT_PASSWORD_URL, HttpMethod.POST, request, String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.NO_CONTENT.value ());
		System.out.println (response);
	}
	
	@Test
	public void forgotPasswordWhenEmailDoesNotExistsReturnStatusCode404 ()
	{
		EmailDTO dto = new EmailDTO ("invalid@email.com");
		HttpEntity<EmailDTO> request = new HttpEntity<EmailDTO>(dto, null);
		ResponseEntity<String> response = restTemplate.exchange (FORGOT_PASSWORD_URL, HttpMethod.POST, request, String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.NOT_FOUND.value ());
		System.out.println (response.getBody ());
	}
	
	// --> REFRESH_TOKEN_URL = "/v1/protected/auth/refresh-token/"
	
	@Test
	public void refreshTokenWhenUserHasRoleEmployeeShouldReturnStatusCode204 ()
	{
		HttpEntity<Void> request = new HttpEntity<Void>(this.getEmployeeEntity ().getHeaders ());
		ResponseEntity<String> response = restTemplate.exchange (REFRESH_TOKEN_URL, HttpMethod.POST, request, String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.NO_CONTENT.value ());
		System.out.println (response);
	}
	
	@Test
	public void refreshTokenWhenUserHasRoleStudentShouldReturnStatusCode204 ()
	{
		HttpEntity<Void> request = new HttpEntity<Void>(this.getStudentEntity ().getHeaders ());
		ResponseEntity<String> response = restTemplate.exchange (REFRESH_TOKEN_URL, HttpMethod.POST, request, String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.NO_CONTENT.value ());
		System.out.println (response);
	}
	
	@Test
	public void refreshTokenWhenUserIsInvalidShouldReturnStatusCode403 ()
	{
		HttpEntity<Void> request = new HttpEntity<Void>(this.getWrongEntity ().getHeaders ());
		ResponseEntity<String> response = restTemplate.exchange (REFRESH_TOKEN_URL, HttpMethod.POST, request, String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.FORBIDDEN.value ());
		System.out.println (response.getBody ());
	}
}