package com.oborodulin.softreport.domain.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.history.RevisionRepository;

import com.oborodulin.softreport.domain.common.entity.AuditableEntity;

/**
 * Базовый интерфейс Spring Data JPA описывает функционал репозиториев доменных объектов.
 * 
 * Расширяет интерфейсы {@link JpaRepository} и RevisionRepository
 * @author Oleg Borodulin
 *
 */
@NoRepositoryBean
public interface CommonRepository<E extends AuditableEntity<U>, U>
		extends RevisionRepository<E, Long, Long>, JpaRepository<E, Long> {

}
