package com.oborodulin.softreport.domain.service;

import java.util.List;

import com.oborodulin.softreport.domain.common.service.CommonJpaTreeService;
import com.oborodulin.softreport.domain.model.dic.objhierarchy.ObjHierarchy;
import com.oborodulin.softreport.domain.model.dic.valuesset.value.Value;

public interface ObjHierarchyService extends CommonJpaTreeService<ObjHierarchy, String> {

	public List<Value> getArchs();

	public List<Value> getTypes();
}
