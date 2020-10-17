package com.oborodulin.softreport.domain.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.oborodulin.softreport.domain.common.AuditorAwareImpl;

/**
 * Класс конфигурации представления объектов БД
 * <p>
 * Включает поддержку транзакций и создаёт необходимые бины с настройками
 * по-умолчанию. Обеспечивает поддержку аудита Hibernate Envers/Spring Data JPA.
 * 
 * @author Oleg Borodulin
 * @version 1.0
 */
@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableTransactionManagement
public class PersistenceConfig {
	/**
	 * Бин возвращает объект, содержащий идентификатор текущего пользователя
	 * <p>
	 * Используется для аудита изменения сущностей
	 * 
	 * @see com.oborodulin.softreport.domain.common.AuditorAwareImpl
	 * @return объект, содержащий идентификатор текущего пользователя
	 */
	@Bean
	public AuditorAware<String> auditorAware() {
		// String user = ((User)
		// SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();

		return new AuditorAwareImpl();
	}
}