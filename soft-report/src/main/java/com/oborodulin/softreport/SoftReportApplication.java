package com.oborodulin.softreport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

//@SpringBootApplication
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class SoftReportApplication {

	public static void main(String[] args) {
		//SpringApplication springApplication = new SpringApplication(SoftReportApplication.class);
		//springApplication.addListeners(new PropertiesLogger());
		//springApplication.run(args);
		SpringApplication.run(SoftReportApplication.class, args);
	}

}
