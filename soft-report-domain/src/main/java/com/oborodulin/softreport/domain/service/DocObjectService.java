package com.oborodulin.softreport.domain.service;

import java.util.List;

import com.oborodulin.softreport.domain.common.service.CommonJpaTreeService;
import com.oborodulin.softreport.domain.model.dic.server.Server;
import com.oborodulin.softreport.domain.model.dic.valuesset.value.Value;
import com.oborodulin.softreport.domain.model.docobject.DocObject;

public interface DocObjectService extends CommonJpaTreeService<DocObject, String> {

	public List<DocObject> findDataBases();

	public DocObject createDataBase();

	public List<Value> getDbTypes();
	
	public List<Server> getDbServers();
	
}
