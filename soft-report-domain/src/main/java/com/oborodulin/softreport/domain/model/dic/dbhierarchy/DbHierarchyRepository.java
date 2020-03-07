package com.oborodulin.softreport.domain.model.dic.dbhierarchy;

import org.springframework.stereotype.Repository;

import com.oborodulin.softreport.domain.common.repository.CommonTreeRepository;

@Repository
public interface DbHierarchyRepository extends CommonTreeRepository<DbHierarchy, String> {


}
