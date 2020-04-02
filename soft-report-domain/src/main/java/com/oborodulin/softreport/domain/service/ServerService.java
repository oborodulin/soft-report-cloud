package com.oborodulin.softreport.domain.service;

import java.util.List;

import com.oborodulin.softreport.domain.common.service.CommonJpaService;
import com.oborodulin.softreport.domain.model.dic.server.Server;
import com.oborodulin.softreport.domain.model.dic.valuesset.value.Value;

public interface ServerService extends CommonJpaService<Server, String> {

	public List<Value> getTypes();

	public List<Value> getEnvTypes();

	public List<Server> getDbServers();
	
}
