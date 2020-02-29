package com.williamdsw.semsys.security;

public class SecurityConstants 
{
	// CONST
	
	private static final String AUTHORIZATION_HEADER = "Authorization";
	private static final String SECRET = "SocioEducationalManagementSystem";
	private static final String TOKEN_PREFIX = "Bearer ";
	private static final String SIGN_UP_URL = "/users/sign-up";
	private static final Long EXPIRATION_TIME = 86400000L;
	
	// GETTERS
	
	public static String getAuthorizationHeader () 
	{
		return AUTHORIZATION_HEADER;
	}

	public static String getSecret () 
	{
		return SECRET;
	}
	
	public static String getTokenPrefix () 
	{
		return TOKEN_PREFIX;
	}
	
	public static String getSignUpUrl () 
	{
		return SIGN_UP_URL;
	}
	
	public static Long getExpirationTime () 
	{
		return EXPIRATION_TIME;
	}
}