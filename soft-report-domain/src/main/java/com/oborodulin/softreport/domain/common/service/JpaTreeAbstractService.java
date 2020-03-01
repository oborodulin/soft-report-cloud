package com.oborodulin.softreport.domain.common.service;

import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.oborodulin.softreport.domain.common.entity.TreeEntity;
import com.oborodulin.softreport.domain.common.repository.CommonRepository;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class JpaTreeAbstractService<E extends TreeEntity<E, U>, R extends CommonRepository<E, U>, U>
		extends JpaAbstractService<E, R, U> implements CommonJpaTreeService<E, U> {

	@Autowired
	public JpaTreeAbstractService(R repository) {
		super(repository);
	}

	@Override
	@Transactional
	public Optional<E> saveChild(Long parentId, E entity) {
		entity.setParent(this.repository.findById(parentId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid software parent Id:" + parentId)));
		return Optional.of(this.repository.save(entity));
	};

}