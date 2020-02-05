package com.oborodulin.softreport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import com.oborodulin.softreport.utils.PropertiesLogger;

@SpringBootApplication
@ConfigurationPropertiesScan("application-local.properties")
public class SoftReportApplication {

	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(SoftReportApplication.class);
		springApplication.addListeners(new PropertiesLogger());
		springApplication.run(args);
		//SpringApplication.run(SoftReportApplication.class, args);
	}

}
