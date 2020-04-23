package com.oborodulin.softreport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//@SpringBootApplication
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@EnableJpaRepositories(repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class)
public class SoftReportApplication {

	public static void main(String[] args) {
		// SpringApplication springApplication = new
		// SpringApplication(SoftReportApplication.class);
		// springApplication.addListeners(new PropertiesLogger());
		// springApplication.run(args);
		SpringApplication.run(SoftReportApplication.class, args);
	}

}
