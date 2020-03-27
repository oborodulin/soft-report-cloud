package com.oborodulin.softreport.domain.common.service;

import java.util.List;
import java.util.Optional;

import com.oborodulin.softreport.domain.common.entity.AuditableEntity;

public interface CommonJpaService<E extends AuditableEntity<U>, U> {
	List<E> findAll();

	Optional<E> findById(Long id);

	E getById(Long id);

	public E create();

	Optional<E> save(E entity);

	void delete(E entity);

	void deleteById(Long id);
}
