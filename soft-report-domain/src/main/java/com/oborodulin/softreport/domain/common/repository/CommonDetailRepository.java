package com.oborodulin.softreport.domain.common.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.NoRepositoryBean;

import com.oborodulin.softreport.domain.common.entity.AuditableEntity;
import com.oborodulin.softreport.domain.common.entity.DetailEntity;

@NoRepositoryBean
public interface CommonDetailRepository<E extends AuditableEntity<U>, D extends DetailEntity<E, U>, U> extends CommonRepository<D, U> {

	public List<D> findByMaster(E master, Sort sort);

	public List<D> findByMasterId(Long id, Sort sort);

}
