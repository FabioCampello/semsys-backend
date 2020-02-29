package com.williamdsw.semsys.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Header;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig 
{
	// FIELDS
	
	private final ResponseMessage m201 = customResponseHeader ();
	private final ResponseMessage m204put = simpleMessage (204, "Successfully Updated");
	private final ResponseMessage m204del = simpleMessage (204, "Successfully Deleted");
	private final ResponseMessage m403 = simpleMessage (403, "Access Denied");
	private final ResponseMessage m404 = simpleMessage (404, "Not Found");
	private final ResponseMessage m422 = simpleMessage (422, "Validation Error");
	private final ResponseMessage m500 = simpleMessage (500, "Internal Error");
	
	// BEANS
	
	@Bean
	public Docket buildApiDoc ()
	{
		String basePackage = "com.williamdsw.semsys.resources";
		return new Docket (DocumentationType.SWAGGER_2)
						  .useDefaultResponseMessages (false)
						  .globalResponseMessage (RequestMethod.GET, Arrays.asList (m403, m404, m500))
						  .globalResponseMessage (RequestMethod.POST, Arrays.asList (m201, m403, m422, m500))
						  .globalResponseMessage (RequestMethod.PUT, Arrays.asList (m204put, m403, m404, m422, m500))
						  .globalResponseMessage (RequestMethod.DELETE, Arrays.asList (m204del, m403, m404, m422, m500))
						  .select ()
						  .apis (RequestHandlerSelectors.basePackage (basePackage))
						  .paths (PathSelectors.any ())
						  .build ()
						  .apiInfo (buildApiInfo ());
	}
	
	private ApiInfo buildApiInfo ()
	{
		String title = "SEMSYS";
		String description = "Backend API for SEMSYS";
		String version = "1.0";
		Contact contact = new Contact ("William Santos", "https://github.com/williamdsw", "williamdsw@outlook.com");
		String license = "Apache License Version 2.0";
		String licenseUrl = "https://www.apache.org/license/LICENSE-2.0";
		return new ApiInfo (title, description, version, null, contact, license, licenseUrl, Collections.emptyList ());
	}
	
	// HELPER FUNCTIONS
	
	private ResponseMessage simpleMessage (Integer code, String message)
	{
		return new ResponseMessageBuilder ().code (code).message (message).build ();
	}
	
	private ResponseMessage customResponseHeader ()
	{
		Map<String, Header> map = new HashMap<>();
		Header header = new Header ("location", "New resource URI", new ModelRef ("string"));
		map.put ("location", header);
		return new ResponseMessageBuilder ().code (201).message ("Resource created").headersWithDescription (map).build ();
	}
}