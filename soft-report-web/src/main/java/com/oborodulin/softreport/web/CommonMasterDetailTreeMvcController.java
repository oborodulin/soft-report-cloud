package com.oborodulin.softreport.web;

import java.util.Locale;

import javax.validation.Valid;

import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import com.oborodulin.softreport.domain.common.entity.AuditableEntity;
import com.oborodulin.softreport.domain.common.entity.DetailTreeEntity;

public interface CommonMasterDetailTreeMvcController<E extends AuditableEntity<U>, T extends DetailTreeEntity<E, T, U>, U>
		extends CommonTreeMvcController<T, U> {

	public String showCreateForm(Long masterId, Long parentId, Locale locale, Model model);

	public String create(Long masterId, Long parentId, boolean isContinue, @Valid T entity, Errors errors, Model model);

}
