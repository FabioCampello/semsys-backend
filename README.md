# Semsys Backend

## Built With

* 	[Spring Tools 4](https://maven.apache.org/) - Eclipse-based development environment that is customized for developing Spring applications.
* 	[Maven](https://maven.apache.org/) - Dependency Management.
* 	[JDK](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) - Java™ Platform, Standard Edition Development Kit 
* 	[Spring Boot](https://spring.io/projects/spring-boot) - Framework to ease the bootstrapping and development of new Spring Applications.
* 	[MySQL](https://www.mysql.com/) - Open-Source Relational Database Management System
* 	[H2 Database Engine](https://www.h2database.com/html/main.html) -  Relational Database Management System written in Java.
* 	[git](https://git-scm.com/) - Free and Open-Source distributed version control system.
* 	[Thymeleaf](https://www.thymeleaf.org/) - Modern server-side Java template engine for both web and standalone environments.
* 	[Swagger](https://swagger.io/) - Open-Source software framework backed by a large ecosystem of tools that helps developers design, build, document, and consume RESTful Web services.

## External Tools Used

* [Postman](https://www.getpostman.com/) - API Development Environment (Testing Docmentation)

## To-Do

- [x] Logger (Console, File, Mail)
- [x] RESTful Web Service (CRUD)
- [x] Security
- [x] Amazon S3 AWS Integration

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `com.williamdsw.semsys.SemsysApplication` class from your IDE.

- Download the zip or clone the Git repository.
- Unzip the zip file (if you downloaded one)
- Open Command Prompt and Change directory (cd) to folder containing pom.xml
- Open Eclipse / STS 4 / Netbeans
   - File -> Import -> Existing Maven Project -> Navigate to the folder where you unzipped the zip
   - Select the project
- Choose the Spring Boot Application file (search for @SpringBootApplication)
- Right Click on the file and Run as Java Application

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run
```

### URLs

|  URL |  Method | Remarks |
|----------|--------------|--------------|
|`http://localhost:8080/v1/protected/auth-refresh-token`                          | POST | |
|`http://localhost:8080/v1/public/auth/forgot-password`                           | POST | |
|`http://localhost:8080/v1/public/countries`                                      | GET | |
|`http://localhost:8080/v1/public/countries/{countryId}/states`                   | GET | |
|`http://localhost:8080/v1/public/countries/{countryId}/states/{stateId}/cities`  | GET | |
|`http://localhost:8080/v1/protected/courses/name`                                | GET | |
|`http://localhost:8080/v1/protected/courses/period`                              | GET | |
|`http://localhost:8080/v1/protected/courses/{courseId}/classes/page`             | GET | |
|`http://localhost:8080/v1/public/courses`                                        | GET | |
|`http://localhost:8080/v1/public/courses/{courseId}/classes`                     | GET | |
|`http://localhost:8080/v1/admin/employees`                                       | POST | |
|`http://localhost:8080/v1/admin/employees/email`                                 | GET | |
|`http://localhost:8080/v1/admin/employees/ssn`                                   | GET | |
|`http://localhost:8080/v1/protected/employees`                                   | GET | |
|`http://localhost:8080/v1/protected/employees/page`                              | GET | |
|`http://localhost:8080/v1/protected/employees/{id}`                              | PUT | |
|`http://localhost:8080/v1/protected/schedules`                                   | POST | |
|`http://localhost:8080/v1/protected/schedules/employee/{employeeId}`             | GET | |
|`http://localhost:8080/v1/protected/schedules/page`                              | GET | |
|`http://localhost:8080/v1/protected/schedules/status`                            | GET | |
|`http://localhost:8080/v1/protected/schedules/student/{studentId}`               | GET | |
|`http://localhost:8080/v1/protected/schedules/{id}`                              | GET | |
|`http://localhost:8080/v1/protected/schedules/{id}`                              | PUT | |
|`http://localhost:8080/v1/admin/persons`                               	      | GET | |
|`http://localhost:8080/v1/admin/persons/{id}`                                    | DELETE | |
|`http://localhost:8080/v1/protected/persons/upload-picture`                      | POST | |
|`http://localhost:8080/v1/protected/persons/{id}`                                | GET | |
|`http://localhost:8080/v1/protected/reports`                                     | POST | |
|`http://localhost:8080/v1/protected/reports/employee/{id}`                       | GET | |
|`http://localhost:8080/v1/protected/reports/schedule/{id}`                       | GET | |
|`http://localhost:8080/v1/protected/reports/student/{id}`                        | GET | |
|`http://localhost:8080/v1/protected/reports/{id}`                                | GET | |
|`http://localhost:8080/v1/protected/students`                                    | GET | |
|`http://localhost:8080/v1/protected/students/email`                              | GET | |
|`http://localhost:8080/v1/protected/students/page`                               | GET | |
|`http://localhost:8080/v1/protected/students/ssn`                                | GET | |
|`http://localhost:8080/v1/protected/students/{id}`                               | PUT | |
|`http://localhost:8080/v1/public/students`                                       | POST | |


## Documentation

* [Postman Collection](https://documenter.getpostman.com/view/10197961/SWT8hKm5) - online, with code auto-generated snippets in cURL, jQuery, Ruby,Python Requests, Node, PHP and Go programming languages
* [Swagger](http://localhost:8088/swagger-ui.html) - Documentation & Testing

## Files and Directories

The project (a.k.a. project directory) has a particular directory structure. A representative project is shown below:

```
.
├── src
│   └── main
│       └── java
│           ├── com.williamdsw.semsys
│           ├── com.williamdsw.semsys.aws
│           ├── com.williamdsw.semsys.config
│           ├── com.williamdsw.semsys.domain
│           ├── com.williamdsw.semsys.domain.dto
│           ├── com.williamdsw.semsys.domain.enums
│           ├── com.williamdsw.semsys.mail
│           ├── com.williamdsw.semsys.repositories
│           ├── com.williamdsw.semsys.resources
│           ├── com.williamdsw.semsys.resources.exceptions
│           ├── com.williamdsw.semsys.security
│           ├── com.williamdsw.semsys.security.filters
│           ├── com.williamdsw.semsys.services
│           ├── com.williamdsw.semsys.services.aws
│           ├── com.williamdsw.semsys.services.aws.util
│           ├── com.williamdsw.semsys.services.exceptions
│           ├── com.williamdsw.semsys.services.mail
│           ├── com.williamdsw.semsys.services.security
├── src
│   └── main
│       └── resources
│           ├── templates
│           |    ├── report
│           |    |   └── issued-report.html
│           |    ├── schedule
│           |    |   └── meeting-schedule-confirmation.html
│           ├── application-dev.properties
│           ├── application-prod.properties
│           ├── application-test.properties
│           ├── application.properties
├── src
│   └── test
│       └── java
│           ├── com.williamdsw.semsys
│           ├── com.williamdsw.semsys.resources
│           ├── com.williamdsw.semsys.resources.utils
├── JRE System Library
├── Maven Dependencies
├── bin
├── files
├── src
├── target
├── LICENSE
├── mvnw
├── mvnw.cmd
├── pom.xml
└── Procfile
└── README.md
```

## packages

- `aws` — to hold our AWS Constants class;
- `config` — to hold our configuration classes;
- `domain` — to hold our entities;
- `domain.dto` — to hold our DTO (Data Transfer Object) of our entities;
- `domain.enums` — to hold our enums used by our entities;
- `mail` — to hold our Mail Constants class;
- `repositories` — to communicate with the database;
- `resources` — to listen to the client;
- `resources.exceptions` — to deal the exceptions of endpoints;
- `security` — to hold our security configuration;
- `security.filters` — to hold our security filters;
- `services` — to hold our business logic;
- `services.aws` — to hold our business logic of AWS;
- `services.aws.util` — to hold utility class of AWS;
- `services.exceptions` — to deal the exceptions of services;
- `services.mail` — to hold our business logic of mail services;
- `services.security` — to hold our business logic of secutiry services;

- `resources/templates` - contains server-side templates which are rendered by Spring.
- `resources/application.properties` - It contains application-wide properties. Spring reads the properties defined in this file to configure your application. You can define server’s default port, server’s context path, database URLs etc, in this file.

- `test/` - contains unit and integration tests

- `pom.xml` - contains all the project dependencies

## Reporting Issues

This Project uses GitHub's integrated issue tracking system to record bugs and feature requests. If you want to raise an issue, please follow the recommendations below:

* Before you log a bug, please [https://github.com/williamdsw/semsys-backend/search?type=Issues](search the issue tracker)
  to see if someone has already reported the problem.
* If the issue doesn't already exist, [https://github.com/williamdsw/semsys-backend/issues/new] (create a new issue)
* Please provide as much information as possible with the issue report.
* If you need to paste code, or include a stack trace use Markdown +++```+++ escapes before and after your text. 