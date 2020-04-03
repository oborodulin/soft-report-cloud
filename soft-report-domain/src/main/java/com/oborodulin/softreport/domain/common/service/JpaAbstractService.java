package com.oborodulin.softreport.domain.common.service;

import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.oborodulin.softreport.domain.common.entity.AuditableEntity;
import com.oborodulin.softreport.domain.common.repository.CommonRepository;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class JpaAbstractService<E extends AuditableEntity<U>, R extends CommonRepository<E, U>, U>
		implements CommonJpaService<E, U> {
	protected Class<E> clazz;
	protected final R repository;

	@Autowired
	public JpaAbstractService(R repository) {
		this.repository = repository;
	}

	@Autowired
	public JpaAbstractService(R repository, Class<E> clazz) {
		this.repository = repository;
		this.clazz = clazz;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(readOnly = true)
	public List<E> findAll() {
		return this.repository.findAll();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<E> findById(Long id) {
		return this.repository.findById(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public E getById(Long id) {
		return this.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid entity Id:" + id));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public E create() {
		E entity = null;
		try {
			entity = this.clazz.getDeclaredConstructor().newInstance();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return entity;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public Optional<E> save(E entity) {
		return Optional.of(this.repository.save(entity));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public void delete(E entity) {
		this.repository.delete(entity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteById(Long id) {
		this.repository.deleteById(id);
	}

}