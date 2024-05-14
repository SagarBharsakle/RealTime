package com.driving.userservice1.configure;




import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestConfig {

   // @LoadBalanced
	@Bean
	RestTemplate initRest() {
		return new RestTemplate();
	}
	
	
}