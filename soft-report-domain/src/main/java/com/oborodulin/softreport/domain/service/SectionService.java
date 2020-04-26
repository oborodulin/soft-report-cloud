package com.oborodulin.softreport.domain.service;

import com.oborodulin.softreport.domain.common.service.CommonJpaDetailTreeService;
import com.oborodulin.softreport.domain.model.dic.doctype.DocType;
import com.oborodulin.softreport.domain.model.dic.doctype.section.Section;

public interface SectionService extends CommonJpaDetailTreeService<DocType, Section, String> {

}
