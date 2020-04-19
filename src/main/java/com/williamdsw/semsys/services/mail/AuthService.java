package com.williamdsw.semsys.services.mail;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.williamdsw.semsys.domain.Employee;
import com.williamdsw.semsys.domain.Person;
import com.williamdsw.semsys.repositories.PersonRepository;

@Service
public class AuthService 
{
	// FIELDS
	
	@Autowired private PersonRepository<Person> personRepository;
	@Autowired private EmailService emailService;
	@Autowired private BCryptPasswordEncoder passwordEncoder;
	
	private final Random random = new Random ();
	private final int PASSWORD_MIN_LENGTH = 8;
	private final int PASSWORD_MAX_LENGTH = 16;
	
	// HELPER FUNCTIONS FOR RESOURCES
	
	public void sendNewPassword (String email)
	{
		String newPassword = generateNewPassword ();

		// Checks person
		Person person = personRepository.findByEmail (email);
		if (person != null)
		{
			person.setPassword (passwordEncoder.encode (newPassword));
			personRepository.save (person);
		}
		else 
		{
			person = new Employee();
			person.setEmail(email);
		}

		emailService.sendNewPasswordEmail (person, newPassword);
	}
	
	// HELPER FUNCTIONS
	
	private String generateNewPassword ()
	{
		int length = random.nextInt ((PASSWORD_MAX_LENGTH - PASSWORD_MIN_LENGTH) + 1) + PASSWORD_MIN_LENGTH;
		char[] password = new char[length];
		for (int index = 0; index < length; index++) 
		{
			password[index] = generateRandomChar ();
		}
		
		return new String (password);
	}
	
	private char generateRandomChar ()
	{
		int option = random.nextInt (3);
		if (option == 0) { return (char) (random.nextInt (10) + 48); }
		else if (option == 1) { return (char) (random.nextInt (26) + 65); }
		else { return (char) (random.nextInt (26) + 97); }
	}
}