package com.oborodulin.softreport.domain.model;

import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface CommonTreeRepository<E extends TreeEntity<E, U>, U> extends CommonRepository<E, U> {

}
