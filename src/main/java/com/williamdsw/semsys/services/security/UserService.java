package com.williamdsw.semsys.services.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.williamdsw.semsys.domain.enums.Profile;
import com.williamdsw.semsys.security.UserDetailsSS;
import com.williamdsw.semsys.services.exceptions.AuthorizationException;

@Service
public class UserService 
{
	// HELPER FUNCTIONS
	
	public static UserDetailsSS getAuthenticated ()
	{
		try 
		{
			return (UserDetailsSS) SecurityContextHolder.getContext ().getAuthentication ().getPrincipal ();
		} 
		catch (Exception e) 
		{
			return null;
		}
	}
	
	public static void checkAuthenticatedUser (Profile profile)
	{
		UserDetailsSS user = getAuthenticated ();
		if (user == null || !user.hasRole (profile))
		{
			throw new AuthorizationException ("Access Denied!");
		}
	}
}