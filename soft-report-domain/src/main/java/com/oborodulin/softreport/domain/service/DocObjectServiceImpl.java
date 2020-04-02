package com.oborodulin.softreport.domain.service;

import com.oborodulin.softreport.domain.common.service.JpaTreeAbstractService;
import com.oborodulin.softreport.domain.model.dic.server.Server;
import com.oborodulin.softreport.domain.model.dic.valuesset.value.Value;
import com.oborodulin.softreport.domain.model.docobject.DocObject;
import com.oborodulin.softreport.domain.model.docobject.DocObjectRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Service("jpaDocObjectService")
@Transactional
public class DocObjectServiceImpl extends JpaTreeAbstractService<DocObject, DocObjectRepository, String>
		implements DocObjectService {
	@Autowired
	private ValuesSetService valuesSetService;
	@Autowired
	private ValueService valueService;
	@Autowired
	private ServerService serverService;

	@Autowired
	public DocObjectServiceImpl(DocObjectRepository repository) {
		super(repository, DocObject.class);
	}

	@Override
	public List<DocObject> findDataBases() {
		return this.repository.findByType(this.valueService.getDocObjectDataBaseType(), Sort.by("name"));
	}

	@Override
	public DocObject createDataBase() {
		DocObject dataBase = this.create();
		dataBase.setType(this.valueService.getDocObjectDataBaseType());
		return dataBase;
	}

	@Override
	public List<Value> getDbTypes() {
		return this.valuesSetService.getDbTypes();
	}
	
	public List<Server> getDbServers(){
		return this.serverService.getDbServers();
	}
	
}
