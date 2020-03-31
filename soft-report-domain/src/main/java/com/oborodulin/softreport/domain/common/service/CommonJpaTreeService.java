package com.oborodulin.softreport.domain.common.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;

import com.oborodulin.softreport.domain.common.entity.TreeEntity;

public interface CommonJpaTreeService<E extends TreeEntity<E, U>, U> extends CommonJpaService<E, U> {

	public List<E> findByIdIsNot(Long id);

	public List<E> findByParentId(Long parentId, Sort sort);

	public List<E> findByParentIsNull();

	public E create(Long parentId);

	public Optional<E> save(Long parentId, E entity);

	public E createChild(Long parentId);

	public Optional<E> saveChild(Long parentId, E entity);

}
