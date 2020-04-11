package com.oborodulin.softreport.web;

import com.oborodulin.softreport.domain.common.entity.AuditableEntity;
import com.oborodulin.softreport.domain.common.entity.DetailEntity;

public interface CommonMasterDetailMvcController<E extends AuditableEntity<U>, D extends DetailEntity<E, U>, U>
		extends CommonMvcController<D, U> {

}
