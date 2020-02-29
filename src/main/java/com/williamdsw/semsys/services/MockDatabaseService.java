package com.williamdsw.semsys.services;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.williamdsw.semsys.domain.Address;
import com.williamdsw.semsys.domain.City;
import com.williamdsw.semsys.domain.Country;
import com.williamdsw.semsys.domain.Course;
import com.williamdsw.semsys.domain.Employee;
import com.williamdsw.semsys.domain.MeetingSchedule;
import com.williamdsw.semsys.domain.Person;
import com.williamdsw.semsys.domain.Report;
import com.williamdsw.semsys.domain.SchoolClass;
import com.williamdsw.semsys.domain.State;
import com.williamdsw.semsys.domain.Student;
import com.williamdsw.semsys.domain.enums.CourseType;
import com.williamdsw.semsys.domain.enums.MeetingStatus;
import com.williamdsw.semsys.domain.enums.Profile;
import com.williamdsw.semsys.domain.enums.TimePeriod;
import com.williamdsw.semsys.repositories.AddressRepository;
import com.williamdsw.semsys.repositories.CityRepository;
import com.williamdsw.semsys.repositories.CountryRepository;
import com.williamdsw.semsys.repositories.CourseRepository;
import com.williamdsw.semsys.repositories.MeetingScheduleRepository;
import com.williamdsw.semsys.repositories.PersonRepository;
import com.williamdsw.semsys.repositories.ReportRepository;
import com.williamdsw.semsys.repositories.SchoolClassRepository;
import com.williamdsw.semsys.repositories.StateRepository;

@Service
public class MockDatabaseService 
{
	// FIELDS
	
	@Autowired private PersonRepository<Person> personRepository;
	@Autowired private CountryRepository countryRepository;
	@Autowired private StateRepository stateRepository;
	@Autowired private CityRepository cityRepository;
	@Autowired private AddressRepository addressRepository;
	@Autowired private CourseRepository courseRepository;
	@Autowired private SchoolClassRepository schoolClassRepository;
	@Autowired private MeetingScheduleRepository meetingScheduleRepository;
	@Autowired private ReportRepository reportRepository;
	
	@Autowired private BCryptPasswordEncoder passwordEncoder;
	
	// HELPER FUNCTIONS
	
	public void mockDatabase () throws Exception
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat ("dd/MM/yyyy");
				
		// COUNTRY - STATE - CITY
		Country unitedStatesOfAmerica = new Country (null, "United States of America");
		
		State kansas = new State (null, "Kansas", unitedStatesOfAmerica);
		State california = new State (null, "California", unitedStatesOfAmerica);
		State nevada = new State (null, "Nevada", unitedStatesOfAmerica);
		State newYork = new State (null, "New York", unitedStatesOfAmerica);
		
		City wichita = new City (null, "Wichita", kansas);
		City kansasCity = new City (null, "Kansas City", kansas);
		City topeka = new City (null, "Topeka", kansas);
		City lawrence = new City (null, "Lawrence", kansas);
		City beverlyHills = new City (null, "Beverly Hills", california);
		City losAngeles = new City (null, "Los Angeles", california);
		City lasVegas = new City (null, "Las Vegas", nevada);
		City longBeach = new City (null, "Long Beach", newYork);
		City newYorkCity = new City (null, "New York", newYork);
		City jamestown = new City (null, "James Town", newYork);
		
		kansas.getCities ().addAll (Arrays.asList (wichita, kansasCity, topeka, lawrence));
		california.getCities ().addAll (Arrays.asList (beverlyHills, losAngeles));
		nevada.getCities ().addAll (Arrays.asList (lasVegas));
		newYork.getCities ().addAll (Arrays.asList (longBeach, newYorkCity, jamestown));
		unitedStatesOfAmerica.getStates ().addAll (Arrays.asList (kansas,california, nevada, newYork));
		
		// Lists - Saves
		List<Country> countries = Arrays.asList (unitedStatesOfAmerica);
		List<State> states = Arrays.asList (kansas, california, nevada, newYork);
		List<City> cities = Arrays.asList (wichita, kansasCity, topeka, lawrence, beverlyHills, losAngeles, lasVegas, longBeach, newYorkCity, jamestown);
		countryRepository.saveAll (countries);
		stateRepository.saveAll (states);
		cityRepository.saveAll (cities);
	
		// COURSES
		Course bachelor1 = new Course (null, "Computer Science", TimePeriod.MORNING, CourseType.BACHELOR_DEGREE);
		Course bachelor2 = new Course (null, "Information Systems", TimePeriod.AFTERNOON, CourseType.BACHELOR_DEGREE);
		Course technical1 = new Course (null, "Internet of Things: Business Implications and Opportunities", TimePeriod.EVENING, CourseType.TECHNICAL_COURSE);
		Course technical2 = new Course (null, "Specialization in Big Data and Business Intelligence", TimePeriod.MORNING, CourseType.TECHNICAL_COURSE);
		Course technical3 = new Course (null, "Information Technology Management", TimePeriod.EVENING, CourseType.TECHNICAL_COURSE);
		
		// CLASSES
		SchoolClass class1 = new SchoolClass (null, "CS - Class01", bachelor1, dateFormat.parse ("01/02/2018"), dateFormat.parse ("01/02/2021"));
		SchoolClass class2 = new SchoolClass (null, "CS - Class02", bachelor1, dateFormat.parse ("05/10/2019"), dateFormat.parse ("05/10/2022"));
		SchoolClass class3 = new SchoolClass (null, "IS - Class01", bachelor2, dateFormat.parse ("10/08/2015"), dateFormat.parse ("10/08/2018"));
		SchoolClass class4 = new SchoolClass (null, "IS - Class02", bachelor2, dateFormat.parse ("03/04/2016"), dateFormat.parse ("03/04/2019"));
		SchoolClass class5 = new SchoolClass (null, "IS - Class03", bachelor2, dateFormat.parse ("05/05/2019"), dateFormat.parse ("05/05/2022"));
		SchoolClass class6 = new SchoolClass (null, "Iot - Class01", technical1, dateFormat.parse ("01/03/2019"), dateFormat.parse ("01/03/2020"));
		SchoolClass class7 = new SchoolClass (null, "BDBI - Class01", technical2, dateFormat.parse ("01/02/2020"), dateFormat.parse ("01/08/2020"));
		SchoolClass class8 = new SchoolClass (null, "ITM - Class01", technical3, dateFormat.parse ("05/06/2020"), dateFormat.parse ("05/06/2022"));
		
		bachelor1.getClasses().addAll (Arrays.asList (class1, class2));
		bachelor2.getClasses().addAll (Arrays.asList (class3, class4, class5));
		technical1.getClasses().addAll (Arrays.asList (class6));
		technical2.getClasses().addAll (Arrays.asList (class7));
		technical3.getClasses().addAll (Arrays.asList (class8));
		
		// Lists - Saves
		List<Course> courses = Arrays.asList (bachelor1, bachelor2, technical1, technical2, technical3);
		List<SchoolClass> classes = Arrays.asList (class1, class2, class3, class4, class5, class6, class7, class8);
		courseRepository.saveAll (courses);
		schoolClassRepository.saveAll (classes);
		
		// EMPLOYEES
		Employee employee1 = new Employee (null, "Anthony Frank Iommi", "iommi@email.com", "039-58-6788", passwordEncoder.encode ("111"), null);
		Employee employee2 = new Employee (null, "Terence Michael Joseph Butler", "butler@email.com", "554-90-1122", passwordEncoder.encode ("222"), null);
		Employee employee3 = new Employee (null, "John Michael Osbourne", "osbourne@email.com", "464-38-6460", passwordEncoder.encode ("333"), null);
		Employee employee4 = new Employee (null, "William Thomas Ward", "ward@email.com", "426-24-7372", passwordEncoder.encode ("444"), null);
		Employee employee5 = new Employee (null, "Ronnie James Dio", "dio@email.com", "517-02-6017", passwordEncoder.encode ("555"), null);
		
		// STUDENTS
		Student student1 = new Student (null, "Gary Lee Weinrib", "geddy@email.com", "500-28-0871", dateFormat.parse ("29/07/1998"), passwordEncoder.encode ("555"), null, class1);
		Student student2 = new Student (null, "Alexandar Živojinović", "alex@email.com", "097-10-6026", dateFormat.parse ("29/07/2000"), passwordEncoder.encode ("666"), null, class3);
		Student student3 = new Student (null, "Neil Ellwood Peart", "neil@email.com", "513-70-7489", dateFormat.parse ("29/07/2002"), passwordEncoder.encode ("777"), null, class6);		
		
		// PROFILES
		employee1.addProfile (Profile.ADMIN);
		
		// ADDRESSES
		Address address1 = new Address (null, "3947 Roosevelt Road", "165", null, "67202", employee1, wichita);
		Address address2 = new Address (null, "2035 Fidler Drive", "178", null, "66101", employee2, kansasCity);
		Address address3 = new Address (null, "4060  Hickman Street", "2265", null, "66601", employee3, topeka);
		Address address4 = new Address (null, "4007  Sherman Street", "10", null, "66044", employee4, lawrence);
		Address address5 = new Address (null, "4007  Sherman Street", "10", null, "66044", employee5, lawrence);
		Address address6 = new Address (null, "621 Lilac Circle", "45", null, "90003", student1, losAngeles);
		Address address7 = new Address (null, "643 Hickory Ridge Drive", "352", null, "89101", student2, lasVegas);
		Address address8 = new Address (null, "4542 Elm Drive", "76", null, "10019", student3, newYorkCity);
		
		// MEETING SCHEDULE
		MeetingSchedule schedule1 = new MeetingSchedule (null, LocalDateTime.parse ("2019-05-20T12:00:00"), employee1, student1, MeetingStatus.FINISHED);
		MeetingSchedule schedule2 = new MeetingSchedule (null, LocalDateTime.parse ("2019-06-16T13:30:00"), employee1, student2, MeetingStatus.CANCELED);
		MeetingSchedule schedule3 = new MeetingSchedule (null, LocalDateTime.parse ("2019-07-05T15:00:00"), employee1, student3, MeetingStatus.FINISHED);
		MeetingSchedule schedule4 = new MeetingSchedule (null, LocalDateTime.parse ("2019-08-12T19:15:00"), employee2, student3, MeetingStatus.CANCELED);
		MeetingSchedule schedule5 = new MeetingSchedule (null, LocalDateTime.parse ("2019-09-25T20:30:00"), employee2, student1, MeetingStatus.FINISHED);
		MeetingSchedule schedule6 = new MeetingSchedule (null, LocalDateTime.parse ("2019-10-30T14:00:00"), employee3, student3, MeetingStatus.FINISHED);
		MeetingSchedule schedule7 = new MeetingSchedule (null, LocalDateTime.parse ("2019-12-01T16:10:00"), employee4, student1, MeetingStatus.FINISHED);
		MeetingSchedule schedule8 = new MeetingSchedule (null, LocalDateTime.parse ("2019-12-11T15:20:00"), employee4, student2, MeetingStatus.CANCELED);
		MeetingSchedule schedule9 = new MeetingSchedule (null, LocalDateTime.parse ("2020-01-20T20:00:00"), employee4, student2, MeetingStatus.SCHEDULED);
		MeetingSchedule schedule10 = new MeetingSchedule (null, LocalDateTime.parse ("2020-02-10T12:30:00"), employee1, student1, MeetingStatus.SCHEDULED);
		MeetingSchedule schedule11 = new MeetingSchedule (null, LocalDateTime.parse ("2020-03-15T13:30:00"), employee2, student1, MeetingStatus.SCHEDULED);
		MeetingSchedule schedule12 = new MeetingSchedule (null, LocalDateTime.parse ("2020-04-05T15:30:00"), employee2, student2, MeetingStatus.SCHEDULED);
		
		// REPORTS
		Report report1 = new Report (null, "Home problems", "Lorem ipsum dolor sit. Suspendisse nec metus tellus. Vivamus eu hendrerit nisl. Curabitur non eleifend lacus. Donec ut laoreet arcu. Sed lobortis sem ac tortor consequat vulputate. Donec in purus vehicula, sollicitudin sapien vel, sodales ipsum. Curabitur facilisis fringilla semper. Vivamus pulvinar velit ligula.", dateFormat.parse ("20/05/2019"), schedule1);
		Report report2 = new Report (null, "Lack of Discipline", "Lorem ipsum dolor sit. Suspendisse nec metus tellus. Vivamus eu hendrerit nisl. Curabitur non eleifend lacus. Donec ut laoreet arcu. Sed lobortis sem ac tortor consequat vulputate. Donec in purus vehicula, sollicitudin sapien vel, sodales ipsum. Curabitur facilisis fringilla semper. Vivamus pulvinar velit ligula.", dateFormat.parse ("05/07/2019"), schedule3);
		Report report3 = new Report (null, "Domestic violence", "Lorem ipsum dolor sit. Suspendisse nec metus tellus. Vivamus eu hendrerit nisl. Curabitur non eleifend lacus. Donec ut laoreet arcu. Sed lobortis sem ac tortor consequat vulputate. Donec in purus vehicula, sollicitudin sapien vel, sodales ipsum. Curabitur facilisis fringilla semper. Vivamus pulvinar velit ligula.", dateFormat.parse ("25/09/2019"), schedule5);
		Report report4 = new Report (null, "Bullying", "Lorem ipsum dolor sit. Suspendisse nec metus tellus. Vivamus eu hendrerit nisl. Curabitur non eleifend lacus. Donec ut laoreet arcu. Sed lobortis sem ac tortor consequat vulputate. Donec in purus vehicula, sollicitudin sapien vel, sodales ipsum. Curabitur facilisis fringilla semper. Vivamus pulvinar velit ligula.", dateFormat.parse ("30/10/2019"), schedule6);
		Report report5 = new Report (null, "Drinking problems", "Lorem ipsum dolor sit. Suspendisse nec metus tellus. Vivamus eu hendrerit nisl. Curabitur non eleifend lacus. Donec ut laoreet arcu. Sed lobortis sem ac tortor consequat vulputate. Donec in purus vehicula, sollicitudin sapien vel, sodales ipsum. Curabitur facilisis fringilla semper. Vivamus pulvinar velit ligula.", dateFormat.parse ("01/12/2019"), schedule7);
		
		// PERSON -> ADDRESS
		employee1.setAddress (address1);
		employee2.setAddress (address2);
		employee3.setAddress (address3);
		employee4.setAddress (address4);
		employee5.setAddress (address5);
		student1.setAddress (address6);
		student2.setAddress (address7);
		student3.setAddress (address8);		
		
		// PHONE NUMBERS
		employee1.getPhoneNumbers ().addAll (Arrays.asList ("316-201-2184", "316-202-1340"));
		employee2.getPhoneNumbers ().addAll (Arrays.asList ("913-214-6938"));
		employee3.getPhoneNumbers ().addAll (Arrays.asList ("785-207-0079"));
		employee4.getPhoneNumbers ().addAll (Arrays.asList ("785-218-2226"));
		employee5.getPhoneNumbers ().addAll (Arrays.asList ("785-218-2226"));
		student1.getPhoneNumbers ().addAll (Arrays.asList ("213-200-1392"));
		student2.getPhoneNumbers ().addAll (Arrays.asList ("702-201-6185"));
		student3.getPhoneNumbers ().addAll (Arrays.asList ("646-388-5724"));
		
		// STUDENTS -> STUDENT CLASSES
		class1.getStudents ().add (student1);
		class3.getStudents ().add (student1);
		class6.getStudents ().add (student1);
		
		// EMPLOYEE / STUDENT -> MEETING SCHEDULE
		employee1.getSchedules ().addAll (Arrays.asList (schedule1, schedule2, schedule3, schedule10));
		employee2.getSchedules ().addAll (Arrays.asList (schedule4, schedule5, schedule11, schedule12));
		employee3.getSchedules ().addAll (Arrays.asList (schedule6));
		employee4.getSchedules ().addAll (Arrays.asList (schedule7, schedule8, schedule9));
		student1.getSchedules ().addAll (Arrays.asList (schedule1, schedule5, schedule7, schedule10, schedule11));
		student2.getSchedules ().addAll (Arrays.asList (schedule2, schedule8, schedule9, schedule12));
		student3.getSchedules ().addAll (Arrays.asList (schedule3, schedule4, schedule6));
		
		// SCHEDULE -> REPORTS
		schedule1.setReport (report1);
		schedule3.setReport (report2);
		schedule5.setReport (report3);
		schedule6.setReport (report4);
		schedule7.setReport (report5);
		
		// Lists
		List<Employee> employees = Arrays.asList (employee1, employee2, employee3, employee4, employee5);
		List<Student> students = Arrays.asList (student2, student2, student3);
		List<Address> addresses = Arrays.asList (address1, address2, address3, address4, address6, address7, address8);
		List<MeetingSchedule> schedules = Arrays.asList (schedule1, schedule2, schedule3, schedule4, schedule5, schedule6, schedule7, schedule8, schedule9, schedule10, schedule11, schedule12);
		List<Report> reports = Arrays.asList (report1, report2, report3, report4, report5);
		
		// Saves
		meetingScheduleRepository.saveAll (schedules);
		reportRepository.saveAll (reports);
		personRepository.saveAll (employees);
		personRepository.saveAll (students);
		addressRepository.saveAll (addresses);
	}
}