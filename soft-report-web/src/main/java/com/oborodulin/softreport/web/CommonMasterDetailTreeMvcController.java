package com.oborodulin.softreport.web;

import java.util.Locale;

import org.springframework.ui.Model;
import com.oborodulin.softreport.domain.common.entity.AuditableEntity;
import com.oborodulin.softreport.domain.common.entity.DetailTreeEntity;

public interface CommonMasterDetailTreeMvcController<E extends AuditableEntity<U>, T extends DetailTreeEntity<E, T, U>, U>
		extends CommonTreeMvcController<T, U> {
	
	public String showDetailTree(Long masterId, Locale locale, Model model);

}
