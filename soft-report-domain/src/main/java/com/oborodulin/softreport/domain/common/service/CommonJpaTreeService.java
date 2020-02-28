package com.oborodulin.softreport.domain.common.service;

import java.util.Optional;

import com.oborodulin.softreport.domain.common.entity.TreeEntity;

public interface CommonJpaTreeService<E extends TreeEntity<E, U>, U> extends CommonJpaService<E, U> {

	public Optional<E> saveChild(Long parentId, E entity);

}
