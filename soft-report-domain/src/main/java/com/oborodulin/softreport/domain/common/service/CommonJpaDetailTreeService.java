package com.oborodulin.softreport.domain.common.service;

import java.util.List;
import com.oborodulin.softreport.domain.common.entity.AuditableEntity;
import com.oborodulin.softreport.domain.common.entity.DetailTreeEntity;

public interface CommonJpaDetailTreeService<E extends AuditableEntity<U>, T extends DetailTreeEntity<E, T, U>, U>
		extends CommonJpaTreeService<T, U> {

	public List<T> findByMasterId(Long masterId);

}
