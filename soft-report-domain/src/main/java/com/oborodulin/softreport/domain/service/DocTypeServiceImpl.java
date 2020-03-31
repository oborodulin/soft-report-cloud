package com.oborodulin.softreport.domain.service;

import com.oborodulin.softreport.domain.common.service.JpaAbstractService;
import com.oborodulin.softreport.domain.model.dic.doctype.DocType;
import com.oborodulin.softreport.domain.model.dic.doctype.DocTypeRepository;
import com.oborodulin.softreport.domain.model.dic.valuesset.value.Value;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Service("jpaDocTypeService")
@Transactional
public class DocTypeServiceImpl extends JpaAbstractService<DocType, DocTypeRepository, String>
		implements DocTypeService {
	@Autowired
	private ValuesSetService valuesSetService;

	@Autowired
	public DocTypeServiceImpl(DocTypeRepository repository) {
		super(repository, DocType.class);
	}

	@Override
	public List<Value> getCategs() {
		return valuesSetService.getDocCategs();
	};

	@Override
	public List<Value> getTypes() {
		return valuesSetService.getDocTypes();
	};

}
