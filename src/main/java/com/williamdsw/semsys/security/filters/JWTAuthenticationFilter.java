package com.williamdsw.semsys.security.filters;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.williamdsw.semsys.domain.dto.CredentialsDTO;
import com.williamdsw.semsys.resources.exceptions.StandardError;
import com.williamdsw.semsys.security.JWTUtil;
import com.williamdsw.semsys.security.SecurityConstants;
import com.williamdsw.semsys.security.UserDetailsSS;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter
{
	// FIELDS
	
	private AuthenticationManager authenticationManager;
	private JWTUtil jwtUtil;
	
	// CONSTRUCTOR
	
	public JWTAuthenticationFilter (AuthenticationManager authenticationManager, JWTUtil jwtUtil) 
	{
		this.setAuthenticationFailureHandler (new JWtAuthenticationFailureHandler ());
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
	}
	
	// OVERRIDED FUNCTIONS
	
	@Override
	public Authentication attemptAuthentication (HttpServletRequest request, HttpServletResponse response) throws AuthenticationException 
	{
		try 
		{
			CredentialsDTO dto = new ObjectMapper ().readValue (request.getInputStream (), CredentialsDTO.class);
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken (dto.getSocialSecurityNumber(), dto.getPassword(), new ArrayList<> ());
			Authentication authentication = authenticationManager.authenticate (authenticationToken);
			return authentication;
		} 
		catch (IOException exception) 
		{
			throw new RuntimeException (exception);
		}
	}
	
	@Override
	protected void successfulAuthentication (HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException
	{
		UserDetailsSS user = (UserDetailsSS) authResult.getPrincipal ();
		String token = jwtUtil.generateToken (user.getUsername ());
		response.addHeader (SecurityConstants.getAuthorizationHeader (), String.format ("%s %s", SecurityConstants.getTokenPrefix (), token));
		response.addHeader ("access-control-expose-headers", SecurityConstants.getAuthorizationHeader ());
	}
	
	// INNER CLASSES
	
	private class JWtAuthenticationFailureHandler implements AuthenticationFailureHandler
	{
		// OVERRIDED FUNCTIONS
		
		@Override
		public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException 
		{
			response.setStatus (HttpStatus.UNAUTHORIZED.value ());
			response.setContentType ("application/json");
			response.getWriter ().append (buildJson ());
		}
		
		// HELPER FUNCTIONS
		
		private String buildJson () throws JsonProcessingException
		{
			StandardError error = new StandardError (System.currentTimeMillis (), HttpStatus.UNAUTHORIZED.value (), "Unauthorized", "Invalid SSN or Password", "/login");
			ObjectMapper mapper = new ObjectMapper ();
			return mapper.writeValueAsString (error);
		}
	}
}