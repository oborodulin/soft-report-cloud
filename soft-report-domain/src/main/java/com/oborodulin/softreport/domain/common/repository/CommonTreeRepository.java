package com.oborodulin.softreport.domain.common.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.NoRepositoryBean;

import com.oborodulin.softreport.domain.common.entity.TreeEntity;

@NoRepositoryBean
public interface CommonTreeRepository<E extends TreeEntity<E, U>, U> extends CommonRepository<E, U> {

	public List<E> findByIdIsNot(Long id);

	public List<E> findByParentIsNull();

	public List<E> findByParentId(Long parentId, Sort sort);
	
}
