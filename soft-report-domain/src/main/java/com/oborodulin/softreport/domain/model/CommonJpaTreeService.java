package com.oborodulin.softreport.domain.model;

import java.util.Optional;

public interface CommonJpaTreeService<E extends TreeEntity<E, U>, U> extends CommonJpaService<E, U> {

	public Optional<E> saveChild(Long parentId, E entity);

}
