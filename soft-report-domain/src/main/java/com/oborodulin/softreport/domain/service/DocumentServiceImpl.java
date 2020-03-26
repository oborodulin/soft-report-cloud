package com.oborodulin.softreport.domain.service;

import com.oborodulin.softreport.domain.common.service.JpaDetailAbstractService;
import com.oborodulin.softreport.domain.model.software.Software;
import com.oborodulin.softreport.domain.model.software.SoftwareRepository;
import com.oborodulin.softreport.domain.model.software.document.Document;
import com.oborodulin.softreport.domain.model.software.document.DocumentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Service("jpaDocumentService")
@Transactional
public class DocumentServiceImpl
		extends JpaDetailAbstractService<Software, Document, SoftwareRepository, DocumentRepository, String>
		implements DocumentService {
	@Autowired
	public DocumentServiceImpl(SoftwareRepository masterRepository, DocumentRepository repository) {
		super(masterRepository, repository, Document.class);
	}

}
