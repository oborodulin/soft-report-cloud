package com.oborodulin.softreport.web;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;

import com.oborodulin.softreport.domain.common.entity.AuditableEntity;
import com.oborodulin.softreport.domain.common.entity.DetailTreeEntity;
import com.oborodulin.softreport.domain.common.service.CommonJpaDetailTreeService;
import com.oborodulin.softreport.domain.common.service.CommonJpaService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
public abstract class AbstractMasterDetailTreeMvcController<E extends AuditableEntity<U>, T extends DetailTreeEntity<E, T, U>, M extends CommonJpaService<E, U>, S extends CommonJpaDetailTreeService<E, T, U>, U>
		extends AbstractTreeMvcController<T, S, U> implements CommonMasterDetailTreeMvcController<E, T, U> {

	protected static final String URL_DTL_READ_TREE = "/tree/{mainId}";
	protected static final String URL_MST_SLV_CREATE = "/{masterId}/{parentId}/create";
	protected static final String URL_MST_SLV_CREATE_CONTINUE = "/{masterId}/{parentId}/create/{isContinue}";

	protected static final String PV_MASTER_ID = "masterId";
	protected static final String PV_PARENT_ID = "parentId";

	protected final M masterService;

	/**
	 * Конструктор. Инстанцирует объект.
	 * 
	 * @param service  сервис объекта доменной модели
	 * @param baseUrl  базовый URL контроллера
	 * @param viewPath путь к CRUD-шаблонам контроллера (каталог)
	 */
	@Autowired
	protected AbstractMasterDetailTreeMvcController(M masterService, S service, String baseUrl, String viewPath,
			String objName, String objCollectName) {
		super(service, baseUrl, viewPath, objName, objCollectName);
		this.masterService = masterService;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> getShowUpdateModelAttributes(Long id) {
		log.info("getShowUpdateModelAttributes: id = " + id);
		return this.getShowCreateModelAttributes();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public E getShowListMainEntity(Long mainId) {
		log.info("getShowListMainEntity: MainId = " + mainId);
		return this.masterService.getById(mainId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<T> getShowListSlavesEntities(Long mainId) {
		log.info("getShowListSlavesEntities: MainId = " + mainId);
		return this.service.findByMasterIdAndParentIsNull(mainId, Sort.by(Sort.Direction.ASC, this.getSortPropName()));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public T createSlaveEntity(Long mainId) {
		log.info("createSlaveEntity: MainId = " + mainId);
		return this.service.create(mainId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public E getMainEntity(T slaveEntity) {
		log.info("getMainEntity: slaveEntityId = " + slaveEntity.getId());
		return slaveEntity.getMaster();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveSlaveEntity(Long mainId, T slaveEntity) {
		log.info("saveSlaveEntity: mainId = " + mainId + "; slaveEntityId = " + slaveEntity.getId());
		this.service.save(mainId, slaveEntity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@GetMapping(URL_MST_SLV_CREATE)
	public String showCreateForm(@PathVariable(PV_MASTER_ID) Long masterId, @PathVariable(PV_PARENT_ID) Long parentId,
			Locale locale, Model model) {
		T slave = this.service.create(masterId, parentId);
		// model.mergeAttributes(this.getShowCreateModelAttributes(mainId));
		model.mergeAttributes(this.getModelAttributes(RM_CREATE));
		AuditableEntity<U> mainEntity = this.getMainEntity(slave);
		if (mainEntity != null) {
			model.addAttribute(MA_TITLE_MASTER, mainEntity.getCodeId());
		}
		model.addAttribute(MA_TITLE_CREATE, this.ms.getMessage(this.msPrefix.concat(".title.create"), null, locale));
		model.addAttribute(this.objName, slave);
		log.info(this.objName + " [" + URL_MST_SLV_CREATE + "]: masterId = " + masterId + "; parentId = " + parentId
				+ "; slave = " + slave);
		return this.getViewNameCreateUpdate();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@PostMapping(URL_MST_SLV_CREATE_CONTINUE)
	public String create(@PathVariable(PV_MASTER_ID) Long masterId, @PathVariable(PV_PARENT_ID) Long parentId,
			@PathVariable(PV_IS_CONTINUE) boolean isContinue, @Valid T entity, Errors errors, Model model
	// , RedirectAttributes redirectAttributes
	) {
		log.info(this.objName + " [" + URL_MST_SLV_CREATE_CONTINUE + "]: masterId = " + masterId + "; isContinue = "
				+ isContinue + "; entity = " + entity);
		if (errors.hasErrors()) {
			errors.getAllErrors().stream().forEach(err -> log.error(err.toString()));
			return this.getViewNameCreateUpdate();
		}
		this.service.save(masterId, parentId, entity);
		if (isContinue) {
			return getRedirectToCreate(masterId);
		}
		// redirectAttributes.addFlashAttribute("valuesSet", );
		return this.getRedirectFromSlaveCreate(masterId);
	}

}
