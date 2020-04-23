package com.oborodulin.softreport.domain.service;

import com.oborodulin.softreport.domain.common.service.CommonJpaDetailService;
import com.oborodulin.softreport.domain.model.project.document.Document;
import com.oborodulin.softreport.domain.model.project.document.chapter.Chapter;

public interface ChapterService extends CommonJpaDetailService<Document, Chapter, String> {


}
