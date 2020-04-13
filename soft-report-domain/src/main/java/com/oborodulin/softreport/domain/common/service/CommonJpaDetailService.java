package com.oborodulin.softreport.domain.common.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;

import com.oborodulin.softreport.domain.common.entity.AuditableEntity;

public interface CommonJpaDetailService<E extends AuditableEntity<U>, D extends AuditableEntity<U>, U>
		extends CommonJpaService<D, U> {

	public List<D> findByMaster(E master, Sort sort);

	public List<D> findByMasterId(Long masterId, Sort sort);

	public D create(Long masterId);

	public Optional<D> save(Long masterId, D entity);

}
