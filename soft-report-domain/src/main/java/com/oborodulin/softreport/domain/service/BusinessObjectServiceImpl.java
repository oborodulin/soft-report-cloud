package com.oborodulin.softreport.domain.service;

import com.oborodulin.softreport.domain.common.service.AbstractJpaDetailService;
import com.oborodulin.softreport.domain.model.software.Software;
import com.oborodulin.softreport.domain.model.software.SoftwareRepository;
import com.oborodulin.softreport.domain.model.software.businessobject.BusinessObject;
import com.oborodulin.softreport.domain.model.software.businessobject.BusinessObjectRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Service("jpaBusinessObjectService")
@Transactional
public class BusinessObjectServiceImpl
		extends AbstractJpaDetailService<Software, BusinessObject, SoftwareRepository, BusinessObjectRepository, String>
		implements BusinessObjectService {
	@Autowired
	public BusinessObjectServiceImpl(SoftwareRepository masterRepository, BusinessObjectRepository repository) {
		super(masterRepository, repository, BusinessObject.class);
	}

}
