package com.oborodulin.softreport.domain.service;

import java.util.List;

import com.oborodulin.softreport.domain.common.service.CommonJpaService;
import com.oborodulin.softreport.domain.model.dic.doctype.DocType;
import com.oborodulin.softreport.domain.model.dic.valuesset.value.Value;

public interface DocTypeService extends CommonJpaService<DocType, String> {

	public List<Value> getCategs();

	public List<Value> getTypes();
	
}
