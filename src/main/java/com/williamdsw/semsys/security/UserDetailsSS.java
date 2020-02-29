package com.williamdsw.semsys.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.williamdsw.semsys.domain.enums.Profile;

public class UserDetailsSS implements UserDetails
{
	// FIELDS
	
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String socialSecurityNumber;
	private String password;
	private Collection<? extends GrantedAuthority> authorities;
	
	// CONSTRUCTORS
	
	public UserDetailsSS () {}
	public UserDetailsSS(Integer id, String socialSecurityNumber, String password, Set<Profile> profiles) 
	{
		super();
		this.id = id;
		this.socialSecurityNumber = socialSecurityNumber;
		this.password = password;
		this.authorities = profiles.stream ().map (profile -> new SimpleGrantedAuthority (profile.getDescription ())).collect(Collectors.toList ());
	}
	
	// GETTERS / SETTERS
	
	public Integer getId () 
	{
		return id;
	}
	
	// OVERRIDED FUNCTIONS

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities () 
	{
		return this.authorities;
	}

	@Override
	public String getPassword () 
	{
		return this.password;
	}

	@Override
	public String getUsername () 
	{
		return this.socialSecurityNumber;
	}

	@Override
	public boolean isAccountNonExpired () 
	{
		return true;
	}

	@Override
	public boolean isAccountNonLocked () 
	{
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired () 
	{
		return true;
	}

	@Override
	public boolean isEnabled () 
	{
		return true;
	}
	
	// HELPER FUNCTIONS
	
	public boolean hasRole (Profile profile)
	{
		SimpleGrantedAuthority role = new SimpleGrantedAuthority (profile.getDescription ());
		return this.getAuthorities ().contains (role);
	}
}