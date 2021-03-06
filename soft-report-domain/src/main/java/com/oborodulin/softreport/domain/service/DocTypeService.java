package com.oborodulin.softreport.domain.service;

import java.util.List;

import com.oborodulin.softreport.domain.common.service.CommonJpaTreeService;
import com.oborodulin.softreport.domain.model.dic.doctype.DocType;
import com.oborodulin.softreport.domain.model.dic.valuesset.value.Value;

public interface DocTypeService extends CommonJpaTreeService<DocType, String> {

	public List<DocType> findAllOrderByCateg();

	public List<Value> getCategs();

	public List<Value> getTypes();
	
}
