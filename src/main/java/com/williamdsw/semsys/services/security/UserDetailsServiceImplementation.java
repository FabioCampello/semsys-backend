package com.williamdsw.semsys.services.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.williamdsw.semsys.domain.Person;
import com.williamdsw.semsys.repositories.PersonRepository;
import com.williamdsw.semsys.security.UserDetailsSS;

@Service
public class UserDetailsServiceImplementation implements UserDetailsService
{
	// FIELDS
	
	@Autowired private PersonRepository<Person> personRepository;

	// OVERRIDED FUNCTIONS
	
	@Override
	public UserDetails loadUserByUsername (String ssn) throws UsernameNotFoundException 
	{
		Person person = personRepository.findBySocialSecurityNumber (ssn);
		if (person == null)
		{
			throw new UsernameNotFoundException (ssn);
		}
		
		UserDetailsSS userDetails = new UserDetailsSS (person.getId (), person.getSocialSecurityNumber (), person.getPassword (), person.getProfiles ());		
		return userDetails;
	}

}
