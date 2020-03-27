package com.oborodulin.softreport.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.oborodulin.softreport.domain.common.entity.AuditableEntity;
import com.oborodulin.softreport.domain.common.entity.DetailEntity;
import com.oborodulin.softreport.domain.common.service.CommonJpaDetailService;
import com.oborodulin.softreport.domain.common.service.CommonJpaService;

public abstract class AbstractMvcDetailController<E extends AuditableEntity<U>, D extends DetailEntity<E, U>, M extends CommonJpaService<E, U>, S extends CommonJpaDetailService<E, D, U>, U>
		extends AbstractMvcController<D, S, U> implements CommonMvcDetailController<E, D, U> {
	public static final String PV_MASTER_ID = "masterId";

	public static final String URL_DTL_READ = "/{masterId}";
	public static final String URL_DTL_CREATE = "/{masterId}/create";
	public static final String URL_DTL_CREATE_CONTINUE = "/{masterId}/create/{isContinue}";
	public static final String URL_DTL_CREATE_CHILD = "/{masterId}/{parentId}/create";
	public static final String URL_DTL_CREATE_CHILD_CONTINUE = "/{masterId}/{parentId}/create/{isContinue}";
	public static final String URL_DTL_EDIT = "/{masterId}/edit/{id}";
	public static final String URL_DTL_UPDATE = "/{masterId}/update/{id}";
	public static final String URL_DTL_DELETE = "/{masterId}/delete";
	public static final String URL_DTL_DELETE_BY_ID = "/{masterId}/delete/{id}";

	public static final String MA_TITLE_MASTER = "titleMaster";

	protected final M masterService;

	@Autowired
	protected AbstractMvcDetailController(M masterService, S service, String baseUrl, String viewPath) {
		super(service, baseUrl, viewPath);
		this.masterService = masterService;
	}

	@Autowired
	protected AbstractMvcDetailController(M masterService, S service, String baseUrl, String viewPath, String objName,
			String collectObjName) {
		super(service, baseUrl, viewPath, objName, collectObjName);
		this.masterService = masterService;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getRedirectToRead(Long masterId) {
		return "redirect:".concat(this.baseUrl).concat("/").concat(Long.toString(masterId));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getRedirectToCreate(Long masterId) {
		return this.getRedirectToRead(masterId).concat(URL_CREATE);
	}

	@Override
	@PostMapping(URL_DTL_CREATE_CONTINUE)
	public String create(@PathVariable(PV_MASTER_ID) Long masterId, @PathVariable(PV_IS_CONTINUE) boolean isContinue,
			@Valid D entity, Errors errors, Model model
	// , RedirectAttributes redirectAttributes
	) {
		if (errors.hasErrors()) {
			return this.getViewNameCreateUpdate();
		}
		this.service.save(masterId, entity);
		if (isContinue) {
			return getRedirectToCreate(masterId);
		}
		// redirectAttributes.addFlashAttribute("valuesSet", );
		return this.getRedirectToRead(masterId);
	}

	@Override
	@PostMapping(URL_DTL_UPDATE)
	public String update(@PathVariable(PV_MASTER_ID) Long masterId, @PathVariable(PV_ID) Long id, @Valid D entity,
			Errors errors, Model model) {
		if (errors.hasErrors()) {
			entity.setId(id);
			return this.getViewNameCreateUpdate();
		}
		this.service.save(masterId, entity);
		return this.getRedirectToRead(masterId);
	}

	@Override
	@PostMapping(URL_DTL_DELETE)
	public String delete(@PathVariable(PV_MASTER_ID) Long masterId,
			@RequestParam(RV_CHK_TABLE_RECORDS) List<String> ids) {
		if (ids != null) {
			for (String idsStr : ids) {
				this.service.deleteById(Long.parseLong(idsStr));
			}
		}
		return this.getRedirectToRead(masterId);
	}

	@Override
	@GetMapping(URL_DTL_DELETE_BY_ID)
	public String deleteById(@PathVariable(PV_MASTER_ID) Long masterId, @PathVariable(PV_ID) Long id) {
		this.service.deleteById(id);
		return this.getRedirectToRead(masterId);
	}

}
