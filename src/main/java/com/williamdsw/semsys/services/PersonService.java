package com.williamdsw.semsys.services;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.williamdsw.semsys.domain.Employee;
import com.williamdsw.semsys.domain.Person;
import com.williamdsw.semsys.domain.Student;
import com.williamdsw.semsys.domain.enums.Profile;
import com.williamdsw.semsys.repositories.AddressRepository;
import com.williamdsw.semsys.repositories.PersonRepository;
import com.williamdsw.semsys.repositories.SchoolClassRepository;
import com.williamdsw.semsys.security.UserDetailsSS;
import com.williamdsw.semsys.services.aws.ImageService;
import com.williamdsw.semsys.services.aws.S3Service;
import com.williamdsw.semsys.services.aws.util.ImageUtil;
import com.williamdsw.semsys.services.exceptions.AuthorizationException;
import com.williamdsw.semsys.services.exceptions.ObjectNotFoundException;
import com.williamdsw.semsys.services.security.UserService;

@Service
public class PersonService 
{
	// FIELDS
	
	@Autowired private PersonRepository<Person> personRepository;
	@Autowired private AddressRepository addressRepository;
	@Autowired private SchoolClassRepository schoolClassRepository;
	@Autowired private S3Service s3Service;
	@Autowired private ImageService imageService;
	
	// HELPER FUNCTIONS FOR RESOURCE
	
	public List<Person> findAllPersons ()
	{
		UserService.checkAuthenticatedUser (Profile.ADMIN);
		return personRepository.findAll ();
	}
	
	public Person findById (Integer id)
	{
		UserDetailsSS user = UserService.getAuthenticated ();
		if (user == null || !user.hasRole (Profile.EMPLOYEE) && !user.hasRole (Profile.STUDENT) || 
			user.hasRole (Profile.STUDENT) && !user.getId ().equals (id)) 
		{
			throw new AuthorizationException ("Access Denied!");
		}
				
		Optional<Person> person = personRepository.findById (id);
		return person.orElseThrow (() -> new ObjectNotFoundException (String.format ("Person not found for Id: %s", id))); 
	}
	
	public Person findBySocialSecurityNumber (String socialSecurityNumber)
	{
		UserDetailsSS user = UserService.getAuthenticated ();
		if (user == null || !user.hasRole (Profile.EMPLOYEE) && !user.hasRole (Profile.STUDENT))
		{
			throw new AuthorizationException ("Access Denied!");
		}
		
		Person person = personRepository.findBySocialSecurityNumber (socialSecurityNumber);
		if (person == null)
		{
			throw new ObjectNotFoundException (String.format ("Person not found for SSN: %s ", socialSecurityNumber));
		}
		
		return person;
	}
	
	@Transactional
	public Person insert (Person person)
	{
		if (person instanceof Employee)
		{
			UserService.checkAuthenticatedUser (Profile.ADMIN);
		}

		person.setId (null);
		person = personRepository.save (person);
		addressRepository.save (person.getAddress ());
		
		if (person instanceof Student)
		{
			Student student = (Student) person;
			schoolClassRepository.save (student.getSchoolClass ());
		}
		
		return person;
	}
	
	@Transactional
	public Person update (Person person)
	{
		if (person instanceof Employee)
		{
			UserService.checkAuthenticatedUser (Profile.EMPLOYEE);
		}
		else if (person instanceof Student)
		{
			UserService.checkAuthenticatedUser (Profile.STUDENT);
		}
		
		UserDetailsSS user = UserService.getAuthenticated ();
		if (user == null || !user.getId ().equals (person.getId ())) 
		{
			throw new AuthorizationException ("Access Denied!");
		}

		Person newPerson = findById (person.getId ());
		updateData (person, newPerson);
		return personRepository.save (newPerson);
	}
	
	public void deleteById (Integer id)
	{
		UserService.checkAuthenticatedUser (Profile.ADMIN);
		findById (id);
		personRepository.deleteById (id);
	}
	
	public URI uploadProfilePicture (MultipartFile multipartFile)
	{
		UserDetailsSS user = UserService.getAuthenticated ();
		if (user == null || !user.hasRole (Profile.EMPLOYEE) && !user.hasRole (Profile.STUDENT))
		{
			throw new AuthorizationException ("Access Denied!");
		}
		
		Person person = findById (user.getId ());
		String fileName = ImageUtil.buildFileName (person.getName ());
		BufferedImage image = imageService.getJpgImageFromFile (multipartFile);
		image = imageService.cropSquare (image);
		image = imageService.resize (image, ImageUtil.getImageSize ());
		InputStream inputStream = imageService.getInputStream (image, "jpg");
		return s3Service.uploadFile (fileName, inputStream, "image");
	}
	
	// HELPER FUNCTIONS
	
	private void updateData (Person person, Person newPerson)
	{
		newPerson.setName (person.getName ());
		newPerson.setEmail (person.getEmail ());
	}
}