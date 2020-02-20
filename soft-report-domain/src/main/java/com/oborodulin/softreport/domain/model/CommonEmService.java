package com.oborodulin.softreport.domain.model;

import java.util.List;
import java.util.Optional;

public interface CommonEmService<E extends AuditableEntity<U>, U> {
	List<E> findAll(Class<E> entityClass);

	Optional<E> findById(Class<E> entityClass, Long id);

	Optional<E> save(E entity);

	void delete(E entity);

	void deleteById(Class<E> entityClass, Long id);
}
