package com.williamdsw.semsys.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.williamdsw.semsys.aws.AwsConstants;

@Configuration
public class S3Config 
{
	// FIELDS
	
	private String accessKeyId = AwsConstants.getAccessKeyId ();
	private String secretAccessKey = AwsConstants.getSecretAccessKey ();
	private String regionName = AwsConstants.getS3Region ();
	
	// BEANS
	
	@Bean
	public AmazonS3 buildAmazonS3 ()
	{
		BasicAWSCredentials credentials = new BasicAWSCredentials (accessKeyId, secretAccessKey);
		AWSStaticCredentialsProvider provider = new AWSStaticCredentialsProvider (credentials);
		AmazonS3 client = AmazonS3ClientBuilder.standard ().withRegion (Regions.fromName (regionName)).withCredentials (provider).build ();
		return client;
	}
}