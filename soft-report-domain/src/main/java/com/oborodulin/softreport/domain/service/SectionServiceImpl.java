package com.oborodulin.softreport.domain.service;

import com.oborodulin.softreport.domain.common.service.AbstractJpaDetailTreeService;
import com.oborodulin.softreport.domain.model.dic.doctype.DocType;
import com.oborodulin.softreport.domain.model.dic.doctype.DocTypeRepository;
import com.oborodulin.softreport.domain.model.dic.doctype.section.Section;
import com.oborodulin.softreport.domain.model.dic.doctype.section.SectionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Service("jpaSectionService")
@Transactional
public class SectionServiceImpl
		extends AbstractJpaDetailTreeService<DocType, Section, DocTypeRepository, SectionRepository, String>
		implements SectionService {
	@Autowired
	private ValuesSetService valuesSetService;

	@Autowired
	public SectionServiceImpl(DocTypeRepository masterRepository, SectionRepository repository) {
		super(masterRepository, repository, Section.class);
	}

}
