package com.oborodulin.softreport.domain.service;

import com.oborodulin.softreport.domain.common.service.AbstractJpaDetailService;
import com.oborodulin.softreport.domain.model.project.document.Document;
import com.oborodulin.softreport.domain.model.project.document.DocumentRepository;
import com.oborodulin.softreport.domain.model.project.document.version.Version;
import com.oborodulin.softreport.domain.model.project.document.version.VersionRepository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Service("jpaVersionService")
@Transactional
public class VersionServiceImpl
		extends AbstractJpaDetailService<Document, Version, DocumentRepository, VersionRepository, String>
		implements VersionService {
	@Autowired
	private ValuesSetService valuesSetService;

	@Autowired
	public VersionServiceImpl(DocumentRepository masterRepository, VersionRepository repository) {
		super(masterRepository, repository, Version.class);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Version getNextVersion(Document document) {
		Version nextVersion = null;
		Optional<Version> lastVersion = this.repository.findFirstByMasterOrderByIdDesc(document);
		if (lastVersion.isEmpty()) {
			nextVersion = new Version();
		} else {

		}
		return nextVersion;
	}

}
