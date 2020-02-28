package com.oborodulin.softreport.web;

import java.util.List;

import org.springframework.ui.Model;
import org.springframework.validation.Errors;

import com.oborodulin.softreport.domain.common.entity.AuditableEntity;

public interface CommonMvcController<E extends AuditableEntity<U>, U> {

	public String getViewNameReadDelete();

	public String getViewNameCreateUpdate();

	public String getRedirectToRead();

	public String getRedirectToCreate();

	public String create(boolean isContinue, E entity, Errors errors, Model model);

	public String update(Long id, E entity, Errors errors, Model model);

	public String delete(List<String> ids);

	public String deleteById(Long id);

}
