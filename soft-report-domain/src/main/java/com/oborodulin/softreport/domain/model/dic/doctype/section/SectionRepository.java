package com.oborodulin.softreport.domain.model.dic.doctype.section;

import java.util.Optional;

import com.oborodulin.softreport.domain.common.repository.CommonDetailTreeRepository;
import com.oborodulin.softreport.domain.model.dic.doctype.DocType;

public interface SectionRepository extends CommonDetailTreeRepository<DocType, Section, String> {

	public Optional<Section> findFirstByMasterOrderByIdDesc(DocType master);

}
