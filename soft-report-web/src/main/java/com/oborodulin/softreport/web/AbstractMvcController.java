package com.oborodulin.softreport.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;

import com.oborodulin.softreport.domain.common.entity.AuditableEntity;
import com.oborodulin.softreport.domain.common.service.CommonJpaService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

public abstract class AbstractMvcController<E extends AuditableEntity<U>, S extends CommonJpaService<E, U>, U>
		implements CommonMvcController<E, U> {
	private static final String VN_READ_DELETE = "read-delete";
	private static final String VN_CREATE_UPDATE = "create-update";

	public static final String RV_CHK_TABLE_RECORDS = "table_records";
	public static final String PV_ID = "id";
	public static final String PV_MASTER_ID = "masterId";
	public static final String PV_PARENT_ID = "parentId";
	public static final String PV_IS_CONTINUE = "isContinue";

	public static final String URL_CREATE = "/create";
	public static final String URL_CREATE_CONTINUE = "/create/{isContinue}";
	public static final String URL_CREATE_CHILD = "/{parentId}/create";
	public static final String URL_CREATE_CHILD_CONTINUE = "/{parentId}/create/{isContinue}";
	public static final String URL_EDIT = "/edit/{id}";
	public static final String URL_UPDATE = "/update/{id}";
	public static final String URL_DELETE = "/delete";
	public static final String URL_DELETE_BY_ID = "/delete/{id}";

	protected final S service;
	@Autowired
	protected MessageSource ms;

	private String baseUrl;
	private String viewPath;

	@Autowired
	protected AbstractMvcController(S service, String baseUrl, String viewPath) {
		this.service = service;
		this.baseUrl = baseUrl;
		this.viewPath = viewPath;
	}

	/**
	 * Возвращает путь к шаблону "чтение-удаление"
	 * 
	 * @return путь к шаблону "чтение-удаление"
	 */
	@Override
	public String getViewNameReadDelete() {
		return this.viewPath.concat(VN_READ_DELETE);
	}

	/**
	 * Возвращает путь к шаблону "создание-обновление"
	 * 
	 * @return путь к шаблону "создание-обновление"
	 */
	@Override
	public String getViewNameCreateUpdate() {
		return this.viewPath.concat(VN_CREATE_UPDATE);
	}

	/**
	 * Возвращает перенаправление на функцию "ЧТЕНИЕ" (базовый URL)
	 * 
	 * @return строка перенаправления на функцию "ЧТЕНИЕ" (базовый URL)
	 */
	@Override
	public String getRedirectToRead() {
		return "redirect:".concat(this.baseUrl);
	}

	/**
	 * Возвращает перенаправление на функцию "СОЗДАНИЕ"
	 * 
	 * @return строка перенаправления на функцию "СОЗДАНИЕ"
	 */
	@Override
	public String getRedirectToCreate() {
		return this.getRedirectToRead().concat(URL_CREATE);
	}

	@Override
	@PostMapping(URL_CREATE_CONTINUE)
	public String create(@PathVariable(PV_IS_CONTINUE) boolean isContinue, @Valid E entity, Errors errors,
			Model model) {
		// @ModelAttribute("software") @Valid Software software
		if (errors.hasErrors()) {
			return this.getViewNameCreateUpdate();
		}
		this.service.save(entity);
		if (isContinue) {
			return this.getRedirectToCreate();
		}
		return this.getRedirectToRead();
	}

	@Override
	@PostMapping(URL_UPDATE)
	public String update(@PathVariable(PV_ID) Long id, @Valid E entity, Errors errors, Model model) {
		if (errors.hasErrors()) {
			entity.setId(id);
			return this.getViewNameCreateUpdate();
		}
		this.service.save(entity);
		return this.getRedirectToRead();
	}

	@Override
	@PostMapping(URL_DELETE)
	public String delete(@RequestParam(RV_CHK_TABLE_RECORDS) List<String> ids) {
		if (ids != null) {
			for (String idsStr : ids) {
				this.service.deleteById(Long.parseLong(idsStr));
			}
		}
		return this.getRedirectToRead();
	}

	@Override
	@GetMapping(URL_DELETE_BY_ID)
	public String deleteById(@PathVariable(PV_ID) Long id) {
		this.service.deleteById(id);
		return this.getRedirectToRead();
	}

}
