package com.oborodulin.softreport.domain.service;

import com.oborodulin.softreport.domain.common.service.JpaAbstractService;
import com.oborodulin.softreport.domain.model.dic.server.Server;
import com.oborodulin.softreport.domain.model.dic.server.ServerRepository;
import com.oborodulin.softreport.domain.model.dic.valuesset.value.Value;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Service("jpaServerService")
@Transactional
public class ServerServiceImpl extends JpaAbstractService<Server, ServerRepository, String> implements ServerService {
	@Autowired
	private ValuesSetService valuesSetService;
	@Autowired
	private ValueService valueService;

	@Autowired
	public ServerServiceImpl(ServerRepository repository) {
		super(repository, Server.class);
	}

	@Override
	public List<Value> getTypes() {
		return valuesSetService.getServersTypes();
	};

	@Override
	public List<Value> getEnvTypes() {
		return valuesSetService.getEnvTypes();
	};

	@Override
	public List<Server> getDbServers() {
		return repository.findByType(this.valueService.getServerDbType(), Sort.by("host"));
	};

}