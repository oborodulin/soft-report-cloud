package com.oborodulin.softreport.domain.service;

import com.oborodulin.softreport.domain.common.service.JpaTreeAbstractService;
import com.oborodulin.softreport.domain.model.docobject.DocObject;
import com.oborodulin.softreport.domain.model.docobject.DocObjectRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Service("jpaDocObjectService")
@Transactional
public class DocObjectServiceImpl extends JpaTreeAbstractService<DocObject, DocObjectRepository, String>
		implements DocObjectService {
	@Autowired
	private ValuesSetService valuesSetService;

	@Autowired
	public DocObjectServiceImpl(DocObjectRepository repository) {
		super(repository, DocObject.class);
	}

}
