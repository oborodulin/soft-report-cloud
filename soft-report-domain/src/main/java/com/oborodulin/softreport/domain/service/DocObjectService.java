package com.oborodulin.softreport.domain.service;

import java.util.List;

import com.oborodulin.softreport.domain.common.service.CommonJpaTreeService;
import com.oborodulin.softreport.domain.model.dic.server.Server;
import com.oborodulin.softreport.domain.model.dic.valuesset.value.Value;
import com.oborodulin.softreport.domain.model.docobject.DocObject;
import com.oborodulin.softreport.domain.model.software.Software;
import com.oborodulin.softreport.domain.model.software.businessobject.BusinessObject;

public interface DocObjectService extends CommonJpaTreeService<DocObject, String> {

	public List<DocObject> findDataBases();

	public List<DocObject> findSchemasByDataBaseId(Long dataBaseId);

	public List<DocObject> findDataTables();

	public List<DocObject> findDtColumnsByDataTableId(Long dataTableId);

	public List<Value> getDbTypes();

	public List<Server> getDbServers();

	public List<DocObject> getSchemas();

	public List<Value> getDtTypes();

	public List<Software> getSoftwares();

	public List<BusinessObject> getBusinessObjects();

	public DocObject createDataBase();

	public DocObject createSchema(Long parentId);

	public DocObject createDataTable(Long parentId);

	public DocObject createDtColumn(Long parentId);

}
