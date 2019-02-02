package com.ps.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@Configuration
@ImportResource("integration-context.xml")
public class SpringIntegrationApplication implements ApplicationRunner {
	@Autowired
	MessagingExample example;

	public static void main(String[] args) {
		SpringApplication.run(SpringIntegrationApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		//example.doOne();
		//example.doTwo();
		//example.doThree();
		//example.doFour();
		//example.queueChannelExample();
		//example.priorityChannelExample();
		//example.priorityChannelWithComparatorExample();
		example.directChannel();
	}

}
