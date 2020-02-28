package com.oborodulin.softreport.domain.common.service;

import java.util.List;
import java.util.Optional;

import com.oborodulin.softreport.domain.common.entity.AuditableEntity;

public interface CommonEmService<E extends AuditableEntity<U>, U> {
	List<E> findAll(Class<E> entityClass);

	Optional<E> findById(Class<E> entityClass, Long id);

	Optional<E> save(E entity);

	void delete(E entity);

	void deleteById(Class<E> entityClass, Long id);
}
