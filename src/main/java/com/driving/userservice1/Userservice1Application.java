package com.driving.userservice1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


@EnableEurekaClient
@SpringBootApplication
public class Userservice1Application {

	public static void main(String[] args) {
		SpringApplication.run(Userservice1Application.class, args);
	}

}