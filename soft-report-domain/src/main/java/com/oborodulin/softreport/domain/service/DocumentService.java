package com.oborodulin.softreport.domain.service;

import com.oborodulin.softreport.domain.common.service.CommonJpaDetailService;
import com.oborodulin.softreport.domain.model.software.Software;
import com.oborodulin.softreport.domain.model.software.document.Document;

public interface DocumentService extends CommonJpaDetailService<Software, Document, String> {


}
