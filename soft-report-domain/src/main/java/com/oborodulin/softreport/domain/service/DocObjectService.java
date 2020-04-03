package com.oborodulin.softreport.domain.service;

import java.util.List;

import com.oborodulin.softreport.domain.common.service.CommonJpaTreeService;
import com.oborodulin.softreport.domain.model.dic.server.Server;
import com.oborodulin.softreport.domain.model.dic.valuesset.value.Value;
import com.oborodulin.softreport.domain.model.docobject.DocObject;

public interface DocObjectService extends CommonJpaTreeService<DocObject, String> {

	public List<DocObject> findDataBases();

	public List<DocObject> findDataTables();

	public List<Value> getDbTypes();

	public List<Server> getDbServers();

	public DocObject createDataBase();

	public DocObject createSchema(Long parentId);

	public DocObject createDataTable(Long parentId);

}
