package com.oborodulin.softreport.domain.service;

import com.oborodulin.softreport.domain.common.service.AbstractJpaDetailService;
import com.oborodulin.softreport.domain.model.project.document.Document;
import com.oborodulin.softreport.domain.model.project.document.DocumentRepository;
import com.oborodulin.softreport.domain.model.project.document.chapter.Chapter;
import com.oborodulin.softreport.domain.model.project.document.chapter.ChapterRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Service("jpaChapterService")
@Transactional
public class ChapterServiceImpl
		extends AbstractJpaDetailService<Document, Chapter, DocumentRepository, ChapterRepository, String>
		implements ChapterService {
	@Autowired
	private ValuesSetService valuesSetService;

	@Autowired
	public ChapterServiceImpl(DocumentRepository masterRepository, ChapterRepository repository) {
		super(masterRepository, repository, Chapter.class);
	}

}
