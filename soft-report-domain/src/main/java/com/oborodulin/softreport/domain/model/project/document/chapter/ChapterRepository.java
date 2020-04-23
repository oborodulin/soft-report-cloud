package com.oborodulin.softreport.domain.model.project.document.chapter;

import java.util.Optional;

import com.oborodulin.softreport.domain.common.repository.CommonDetailRepository;
import com.oborodulin.softreport.domain.model.project.document.Document;

public interface ChapterRepository extends CommonDetailRepository<Document, Chapter, String> {

	public Optional<Chapter> findFirstByMasterOrderByIdDesc(Document master);

}
