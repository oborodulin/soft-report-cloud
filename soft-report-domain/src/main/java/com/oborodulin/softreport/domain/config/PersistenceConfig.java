package com.oborodulin.softreport.domain.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.oborodulin.softreport.domain.common.AuditorAwareImpl;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableTransactionManagement
public class PersistenceConfig {
	@Bean
	public AuditorAware<String> auditorAware() {
		//String user = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();

		return new AuditorAwareImpl();
	}
}