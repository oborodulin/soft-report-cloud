package com.oborodulin.softreport.domain.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.oborodulin.softreport.domain.common.entity.AuditableEntity;

@NoRepositoryBean
public interface CommonRepository<E extends AuditableEntity<U>, U> extends JpaRepository<E, Long> {

}
