package com.oborodulin.softreport.web;

import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Sort;

import com.oborodulin.softreport.domain.common.entity.AuditableEntity;
import com.oborodulin.softreport.domain.common.entity.DetailEntity;
import com.oborodulin.softreport.domain.common.service.CommonJpaDetailService;
import com.oborodulin.softreport.domain.common.service.CommonJpaService;
import com.oborodulin.softreport.web.support.MessageHelper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractMasterDetailMvcController<E extends AuditableEntity<U>, D extends DetailEntity<E, U>, M extends CommonJpaService<E, U>, S extends CommonJpaDetailService<E, D, U>, U>
		extends AbstractMvcController<D, S, U> implements CommonMasterDetailMvcController<E, D, U> {
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

	public static final String MA_MASTER = "master";
	public static final String MA_TITLE_MASTER = "titleMaster";

	protected static final String RM_DTL_READ = "details-read";

	protected final M masterService;

	private String dtlSortPropName = "name";

	/**
	 * Конструктор. Инстанцирует объект.
	 * 
	 * @param masterService сервис главного объекта доменной модели
	 * @param service       сервис подчинённого объекта доменной модели
	 * @param baseUrl       базовый URL контроллера
	 * @param viewPath      путь к CRUD-шаблонам контроллера (каталог)
	 */
	@Autowired
	protected AbstractMasterDetailMvcController(M masterService, S service, String baseUrl, String viewPath, String objName,
			String collectObjName) {
		super(service, baseUrl, viewPath, objName, collectObjName);
		this.masterService = masterService;
	}


	public String getDtlSortPropName() {
		return dtlSortPropName;
	}


	protected void setDtlSortPropName(String dtlSortPropName) {
		this.dtlSortPropName = dtlSortPropName;
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getRedirectToRead(Long masterId) {
		String redirect = "redirect:".concat(this.baseUrl).concat("/").concat(Long.toString(masterId));
		log.info("Redirect to: " + redirect);
		return redirect;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getRedirectToCreate(Long masterId) {
		String redirect = this.getRedirectToRead(masterId).concat(URL_CREATE);
		log.info("Redirect to: " + redirect);
		return redirect;
	}

	/**
	 * Возвращает строковый идентификатор главного объекта.
	 * 
	 * @param master главный объект
	 * @param isCode признак возврата значения кода {@code getCode()} или
	 *               наименование {@code getName()}
	 * @return строковый идентификатор главного объекта (код или наименование)
	 */
	/*
	 * private String getMasterIdentifier(E master) { Object result = null; try {
	 * Class<?> clazz = master.getClass(); if (clazz != null) { Method method =
	 * null;
	 * 
	 * switch (this.titleMaster) { case CODE: method =
	 * clazz.getDeclaredMethod("getCode", master.getClass()); break; case NAME:
	 * method = clazz.getDeclaredMethod("getName", master.getClass()); }
	 * 
	 * result = method.invoke(null); } } catch (Exception e) { e.printStackTrace();
	 * } return (String) result; }
	 */

	/**
	 * {@inheritDoc}
	 */
	@Override
	@GetMapping(URL_DTL_READ)
	public String showList(@PathVariable(PV_MASTER_ID) Long masterId, Locale locale, Model model) {
		E master = this.masterService.getById(masterId);
		List<D> details = this.service.findByMasterId(masterId, Sort.by(Sort.Direction.ASC, this.dtlSortPropName));
		if (details.isEmpty()) {
			MessageHelper.addInfoAttribute(model, this.msPrefix.concat(".master.info.empty"), master.getCodeId());
		}
		model.mergeAttributes(this.getModelAttributes(RM_DTL_READ));
		model.addAttribute(MA_TITLE_MASTER, master.getCodeId());
		model.addAttribute(MA_TITLE_READ, this.ms.getMessage(this.msPrefix.concat(".title.read"), null, locale));
		model.addAttribute(MA_MASTER, master);
		model.addAttribute(this.objCollectName, details);
		// model.addAttribute(MA_TITLE_READ, this.ms.getMessage("tasks.title.read",
		// new Object[] {project.getCode()}, locale));
		log.info(this.objCollectName + " [" + URL_DTL_READ + "]: " + details.size() + " counts");
		return this.getViewNameReadDelete();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@GetMapping(URL_DTL_CREATE)
	public String showCreateForm(@PathVariable(PV_MASTER_ID) Long masterId, Locale locale, Model model) {
		D detail = this.service.create(masterId);
		model.addAttribute(MA_TITLE_MASTER, detail.getMaster().getCodeId());
		model.addAttribute(MA_TITLE_CREATE, this.ms.getMessage(this.msPrefix.concat(".title.create"), null, locale));
		model.addAttribute(this.objName, detail);
		log.info(this.objName + " [" + URL_DTL_CREATE + "]: masterId = " + masterId + "; detail = " + detail);
		return this.getViewNameCreateUpdate();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@GetMapping(URL_DTL_EDIT)
	public String showUpdateForm(@PathVariable(PV_MASTER_ID) Long masterId, @PathVariable(PV_ID) Long id, Locale locale,
			Model model) {
		D detail = this.service.getById(id);
		model.addAttribute(MA_TITLE_MASTER, detail.getMaster().getCodeId());
		model.addAttribute(MA_TITLE_UPDATE, this.ms.getMessage(this.msPrefix.concat(".title.update"), null, locale));
		model.addAttribute(this.objName, detail);
		log.info(this.objName + " [" + URL_DTL_EDIT + "]: masterId = " + masterId + "; detail = " + detail);
		return this.getViewNameCreateUpdate();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@PostMapping(URL_DTL_CREATE_CONTINUE)
	public String create(@PathVariable(PV_MASTER_ID) Long masterId, @PathVariable(PV_IS_CONTINUE) boolean isContinue,
			@Valid D entity, Errors errors, Model model
	// , RedirectAttributes redirectAttributes
	) {
		log.info(this.objName + " [" + URL_DTL_EDIT + "]: masterId = " + masterId + "; isContinue = " + isContinue
				+ "; entity = " + entity);
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

	/**
	 * {@inheritDoc}
	 */
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

	/**
	 * {@inheritDoc}
	 */
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	@GetMapping(URL_DTL_DELETE_BY_ID)
	public String deleteById(@PathVariable(PV_MASTER_ID) Long masterId, @PathVariable(PV_ID) Long id) {
		this.service.deleteById(id);
		return this.getRedirectToRead(masterId);
	}

}
