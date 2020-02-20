package com.oborodulin.softreport.domain.model;

import java.util.List;
import java.util.Optional;

public interface CommonJpaService<E extends AuditableEntity<U>, U> {
	List<E> findAll();

	Optional<E> findById(Long id);

	Optional<E> save(E entity);

	void delete(E entity);

	void deleteById(Long id);
}
