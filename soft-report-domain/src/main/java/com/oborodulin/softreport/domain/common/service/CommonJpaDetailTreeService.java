package com.oborodulin.softreport.domain.common.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;

import com.oborodulin.softreport.domain.common.entity.AuditableEntity;
import com.oborodulin.softreport.domain.common.entity.DetailTreeEntity;

public interface CommonJpaDetailTreeService<E extends AuditableEntity<U>, T extends DetailTreeEntity<E, T, U>, U>
		extends CommonJpaTreeService<T, U> {

	public List<T> findByMasterId(Long id, Sort sort);

	public List<T> findByMasterIdAndParentIsNull(Long masterId, Sort sort);

	public T create(Long masterId, Long parentId);

	public Optional<T> save(Long masterId, Long parentId, T entity);
}
