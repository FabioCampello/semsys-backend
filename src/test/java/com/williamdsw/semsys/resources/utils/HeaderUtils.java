package com.williamdsw.semsys.resources.utils;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.williamdsw.semsys.domain.dto.CredentialsDTO;

@Component
public class HeaderUtils 
{
	// HELPER FUNCTIONS
	
	public static String getCredentialsJson (String socialSecurityNumber, String password)
	{
		try 
		{
			CredentialsDTO dto = new CredentialsDTO (socialSecurityNumber, password);
			ObjectMapper mapper = new ObjectMapper ();
			return mapper.writeValueAsString (dto);
		} 
		catch (Exception e) 
		{
			System.out.println(e.getMessage());
			return "";
		}
	}
	
	public static URI buildUriWithPathParams (String uriString, String[] headers, String[] values)
	{
		// Checks
		if (uriString == null || uriString.isEmpty () || 
		    headers == null || values == null || headers.length != values.length)
		{
			return null;
		}
		
		Map<String, String> params = new HashMap<>();
		for (int index = 0; index < headers.length; index++) 
		{
			params.put (headers[index], values[index]);
		} 
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString (uriString);
		return builder.buildAndExpand (params).toUri ();
	}
	
	public static URI buildUriWithQueryParams (String uriString, String[] names, String[] values)
	{
		// Checks
		if (uriString == null || uriString.isEmpty () || 
		    names == null || values == null || names.length != values.length)
		{
			return null;
		}
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString (uriString);
		for (int index = 0; index < names.length; index++) 
		{
			builder.queryParam (names[index], values[index]);
		} 
		
		return builder.build ().toUri ();
	}
}