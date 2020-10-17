package com.oborodulin.softreport.domain.common;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;

/**
 * Класс обеспечивает идентификатор пользователя, создавшего/изменившего
 * сущность
 * <p>
 * Сейчас используется "заглушка" {@code "anonymous"}
 * 
 * @author Oleg Borodulin
 * @version 1.0
 */
public class AuditorAwareImpl implements AuditorAware<String> {

	/**
	 * Возвращает идентификатор текущего пользователя
	 * 
	 * @return идентификатор текущего пользователя
	 */
	@Override
	public Optional<String> getCurrentAuditor() {
		return Optional.of("anonymous");
	}
}