package com.oborodulin.softreport.domain.common.service;

import java.util.Optional;

import com.oborodulin.softreport.domain.common.entity.AuditableEntity;

public interface CommonJpaDetailService<E extends AuditableEntity<U>, D extends AuditableEntity<U>, U>
		extends CommonJpaService<D, U> {

	public D create(Long masterId);

	public Optional<D> save(Long masterId, D entity);

}
