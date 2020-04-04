package com.oborodulin.softreport.domain.common.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.NoRepositoryBean;

import com.oborodulin.softreport.domain.common.entity.AuditableEntity;
import com.oborodulin.softreport.domain.common.entity.TreeEntity;

@NoRepositoryBean
public interface CommonDetailTreeRepository<E extends AuditableEntity<U>, T extends TreeEntity<T, U>, U>
		extends CommonTreeRepository<T, U> {

	public List<T> findByMaster(E master, Sort sort);

	public List<T> findByMaster_Id(Long masterId, Sort sort);
	
	public List<T> findByMaster_IdAndParentIsNull(Long masterId);

}
