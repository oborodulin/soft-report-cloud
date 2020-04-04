package com.oborodulin.softreport.domain.common.service;

import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.oborodulin.softreport.domain.common.entity.AuditableEntity;
import com.oborodulin.softreport.domain.common.repository.CommonRepository;

import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractEmService<E extends AuditableEntity<U>, R extends CommonRepository<E, U>, U>
		implements CommonEmService<E, U> {
	@Autowired
	private EntityManager em;

	@Transactional(readOnly = true)
	@Override
	public List<E> findAll(Class<E> entityClass) {
		return em.createQuery("FROM " + entityClass.getSimpleName(), entityClass).getResultList();
	}

	@Transactional(readOnly = true)
	@Override
	public Optional<E> findById(Class<E> entityClass, Long id) {
		return Optional.of(this.em.find(entityClass, id));
	}

	@Override
	@Transactional
	public Optional<E> save(E entity) {
		if (entity.getVersion() == null) {
			em.persist(entity);
		} else {
			em.merge(entity);
		}
		return Optional.of(entity);
	}

	@Override
	@Transactional
	public void delete(E entity) {
		em.remove(entity);
	}

	@Override
	@Transactional
	public void deleteById(Class<E> entityClass, Long id) {
		delete(findById(entityClass, id).get());
	}

}