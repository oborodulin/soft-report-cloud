package com.oborodulin.softreport.domain.common.service;

import java.util.List;
import java.util.Optional;

import com.oborodulin.softreport.domain.common.entity.AuditableEntity;

public interface CommonJpaService<E extends AuditableEntity<U>, U> {

	public List<E> findAll();

	public Optional<E> findById(Long id);

	public E getById(Long id);

	public E create();

	public E init(E entity);

	public List<E> init(List<E> entities);
	
	public Optional<E> save(E entity);

	public void delete(E entity);

	public void deleteById(Long id);
}
