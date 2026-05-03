package com.edhy.api.config;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Config {

	   @Value("${aws.s3.endpoint}")
	    private String s3Endpoint;

	
    @Bean
    public S3Client s3Client() {
    	System.out.println("probando.....");
    	System.out.println(s3Endpoint);
    	
        return S3Client.builder()
                .endpointOverride(URI.create(s3Endpoint)) // LocalStack endpoint
                .region(Region.US_EAST_1) // región dummy
                .forcePathStyle(true) //
                .credentialsProvider(
                    StaticCredentialsProvider.create(AwsBasicCredentials.create("test", "test"))
                )
                .build();
    }
}

