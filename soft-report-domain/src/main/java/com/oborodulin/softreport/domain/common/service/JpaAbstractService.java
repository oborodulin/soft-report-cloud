package com.oborodulin.softreport.domain.common.service;

import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.oborodulin.softreport.domain.common.entity.AuditableEntity;
import com.oborodulin.softreport.domain.common.repository.CommonRepository;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class JpaAbstractService<E extends AuditableEntity<U>, R extends CommonRepository<E, U>, U>
		implements CommonJpaService<E, U> {
	protected final R repository;

	@Autowired
	public JpaAbstractService(R repository) {
		this.repository = repository;
	}

	@Transactional(readOnly = true)
	@Override
	public List<E> findAll() {
		return this.repository.findAll();
	}

	@Transactional(readOnly = true)
	@Override
	public Optional<E> findById(Long id) {
		return this.repository.findById(id);
	}

	@Override
	public E getById(Long id) {
		return this.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid entity Id:" + id));
	}

	@Override
	@Transactional
	public Optional<E> save(E entity) {
		return Optional.of(this.repository.save(entity));
	}

	@Override
	@Transactional
	public void delete(E entity) {
		this.repository.delete(entity);
	}

	@Override
	public void deleteById(Long id) {
		this.repository.deleteById(id);
	}

}