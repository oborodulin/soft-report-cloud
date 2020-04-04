package com.oborodulin.softreport.domain.service;

import com.oborodulin.softreport.domain.common.service.AbstractJpaTreeService;
import com.oborodulin.softreport.domain.model.enterprise.Enterprise;
import com.oborodulin.softreport.domain.model.enterprise.EnterpriseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Service("jpaEnterpriseService")
@Transactional
public class EnterpriseServiceImpl extends AbstractJpaTreeService<Enterprise, EnterpriseRepository, String>
		implements EnterpriseService {
	//@Autowired
	//private ValueRepository valueRepository;

	@Autowired
	public EnterpriseServiceImpl(EnterpriseRepository repository) {
		super(repository, Enterprise.class);
	}

}
