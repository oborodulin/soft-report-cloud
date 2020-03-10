package com.oborodulin.softreport.domain.model.dic.objecthierarchy;

import org.springframework.stereotype.Repository;

import com.oborodulin.softreport.domain.common.repository.CommonTreeRepository;

@Repository
public interface ObjectHierarchyRepository extends CommonTreeRepository<ObjectHierarchy, String> {


}
