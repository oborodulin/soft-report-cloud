package com.oborodulin.softreport.domain.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
//import org.springframework.data.repository.history.RevisionRepository;

import com.oborodulin.softreport.domain.common.entity.AuditableEntity;

/**
 * Базовый интерфейс Spring Data JPA описывает функционал репозиториев доменных
 * объектов
 * <p>
 * Расширяет интерфейс {@link JpaRepository}
 * 
 * @author Oleg Borodulin
 * @version 1.0
 */
@NoRepositoryBean
public interface CommonRepository<E extends AuditableEntity<U>, U> extends // RevisionRepository<E, Long, Long>,
		JpaRepository<E, Long> {

}
