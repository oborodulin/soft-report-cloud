package com.oborodulin.softreport.web;

import java.util.Locale;

import org.springframework.ui.Model;
import org.springframework.validation.Errors;

import com.oborodulin.softreport.domain.common.entity.TreeEntity;

public interface CommonMvcTreeController<E extends TreeEntity<E, U>, U> extends CommonMvcController<E, U> {
	public String getRedirectToCreateChild(Long parentId);

	public String showRootList(Locale locale, Model model);

	public String createChild(Long parentId, boolean isContinue, E entity, Errors errors, Model model);

}
