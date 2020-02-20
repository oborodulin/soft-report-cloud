package com.oborodulin.softreport.domain.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface CommonRepository<E extends AuditableEntity<U>, U> extends JpaRepository<E, Long> {

}
