package com.williamdsw.semsys.security.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import com.williamdsw.semsys.security.JWTUtil;
import com.williamdsw.semsys.security.SecurityConstants;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter
{
	// FIELDS
	
	private JWTUtil jwtUtil;
	private UserDetailsService userDetailsService;
	
	// CONSTRUCTORS

	public JWTAuthorizationFilter (AuthenticationManager authenticationManager, JWTUtil jwtUtil, UserDetailsService userDetailsService) 
	{
		super (authenticationManager);
		this.jwtUtil = jwtUtil;
		this.userDetailsService = userDetailsService;
	}
	
	// OVERRIDED FUNCTIONS
	
	@Override
	protected void doFilterInternal (HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException 
	{
		String header = request.getHeader (SecurityConstants.getAuthorizationHeader ());
		if (header != null && header.startsWith (SecurityConstants.getTokenPrefix ()))
		{
			Integer indexOf = SecurityConstants.getTokenPrefix ().indexOf (" ");
			UsernamePasswordAuthenticationToken authenticationToken = this.getAuthenticationToken (header.substring (indexOf));
			if (authenticationToken != null)
			{
				SecurityContextHolder.getContext ().setAuthentication (authenticationToken);
			}
		}
		
		chain.doFilter (request, response);
	}
	
	// HELPER FUNCTIONS
	
	private UsernamePasswordAuthenticationToken getAuthenticationToken (String token)
	{
		if (jwtUtil.isTokenValid (token)) 
		{
			String username = jwtUtil.getUsername (token);
			UserDetails userDetails = userDetailsService.loadUserByUsername (username);
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities ());
			return authenticationToken;
		}
		
		return null;
	}
}