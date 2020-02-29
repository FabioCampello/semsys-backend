package com.williamdsw.semsys.resources;

import java.io.File;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;


@ExtendWith (SpringExtension.class)
@SpringBootTest (webEnvironment = WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
@ActiveProfiles (profiles = "test")
public class ImageResourceTest extends GlobalResourceConfigureTest
{
	// FIELDS
	
	private final String UPLOAD_PICTURE_URL = "/v1/protected/persons/upload-picture/";
	
	// TESTS
	
	@Test
	public void uploadImageWhenUserHasRoleAdminShouldReturnStatusCode201 ()
	{
		HttpEntity<MultiValueMap<String, Object>> entity = buildHttpEntity (this.getAdminEntity ().getHeaders (), "files/iommi.jpg");
		ResponseEntity<String> response = restTemplate.exchange (UPLOAD_PICTURE_URL, HttpMethod.POST, entity, String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.CREATED.value ());
		System.out.println (response.getHeaders ().getLocation ());
	}
	
	@Test
	public void uploadImageWhenUserHasRoleEmployeeShouldReturnStatusCode201 ()
	{
		HttpEntity<MultiValueMap<String, Object>> entity = buildHttpEntity (this.getEmployeeEntity ().getHeaders (), "files/butler.jpg");
		ResponseEntity<String> response = restTemplate.exchange (UPLOAD_PICTURE_URL, HttpMethod.POST, entity, String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.CREATED.value ());
		System.out.println (response.getHeaders ().getLocation ());
	}
	
	@Test
	public void uploadImageWhenUserHasRoleStudentShouldReturnStatusCode201 ()
	{
		HttpEntity<MultiValueMap<String, Object>> entity = buildHttpEntity (this.getStudentEntity ().getHeaders (), "files/lee.jpg");
		ResponseEntity<String> response = restTemplate.exchange (UPLOAD_PICTURE_URL, HttpMethod.POST, entity, String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.CREATED.value ());
		System.out.println (response.getHeaders ().getLocation ());
	}
	
	@Test
	public void uploadImageWhenUserIsInvalidShouldReturnStatusCode403 ()
	{
		HttpEntity<MultiValueMap<String, Object>> entity = buildHttpEntity (this.getWrongEntity ().getHeaders (), "files/lee.jpg");
		ResponseEntity<String> response = restTemplate.exchange (UPLOAD_PICTURE_URL, HttpMethod.POST, entity, String.class);
		Assertions.assertEquals (response.getStatusCodeValue (), HttpStatus.FORBIDDEN.value ());
		System.out.println (response.getBody ());
	}
	
	// HELPER FUNCTIONS
	
	private HttpEntity<MultiValueMap<String, Object>> buildHttpEntity (HttpHeaders entityHeaders, String fileName)
	{
		// Build headers
		HttpHeaders headers = new HttpHeaders ();
		headers.clear ();
		headers.setContentType (MediaType.MULTIPART_FORM_DATA);
		headers.add (HttpHeaders.AUTHORIZATION, entityHeaders.getFirst(HttpHeaders.AUTHORIZATION));
		
		// Build multipart
		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		File file = this.getFileByName (fileName);		
		body.add ("file", new FileSystemResource (file));
		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<> (body, headers);
		return requestEntity;
	}
	
	private File getFileByName (String fileName)
	{
		try 
		{
			return new File (fileName);
		} 
		catch (Exception exception) 
		{
			return null;
		}
	}
}