package com.oborodulin.softreport.domain.common.service;

import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.oborodulin.softreport.domain.common.entity.AuditableEntity;
import com.oborodulin.softreport.domain.common.entity.DetailEntity;
import com.oborodulin.softreport.domain.common.repository.CommonRepository;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class JpaDetailAbstractService<E extends AuditableEntity<U>, D extends DetailEntity<E, U>, M extends CommonRepository<E, U>, R extends CommonRepository<D, U>, U>
		extends JpaAbstractService<D, R, U> implements CommonJpaDetailService<E, D, U> {
	protected final M masterRepository;

	@Autowired
	public JpaDetailAbstractService(M masterRepository, R repository) {
		super(repository);
		this.masterRepository = masterRepository;
	}

	@Transactional(readOnly = true)
	@Override
	public D create(Long masterId, D entity) {
		entity.setMaster(this.masterRepository.findById(masterId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid master Id:" + masterId)));
		return entity;
	}

	@Override
	@Transactional
	public Optional<D> save(Long masterId, D entity) {
		entity.setMaster(this.masterRepository.findById(masterId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid master Id:" + masterId)));
		return Optional.of(this.repository.save(entity));
	}

}