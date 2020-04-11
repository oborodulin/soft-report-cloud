package com.oborodulin.softreport.web;

import java.util.List;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import com.oborodulin.softreport.domain.common.entity.AuditableEntity;
import com.oborodulin.softreport.domain.common.entity.DetailTreeEntity;
import com.oborodulin.softreport.domain.common.service.CommonJpaDetailTreeService;
import com.oborodulin.softreport.web.support.MessageHelper;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
public abstract class AbstractMasterDetailTreeMvcController<E extends AuditableEntity<U>, T extends DetailTreeEntity<E, T, U>, S extends CommonJpaDetailTreeService<E, T, U>, U>
		extends AbstractTreeMvcController<T, S, U> implements CommonMasterDetailTreeMvcController<E, T, U> {

	public static final String URL_DTL_READ_TREE = "/tree/{mainId}";

	/**
	 * Конструктор. Инстанцирует объект.
	 * 
	 * @param service  сервис объекта доменной модели
	 * @param baseUrl  базовый URL контроллера
	 * @param viewPath путь к CRUD-шаблонам контроллера (каталог)
	 */
	@Autowired
	protected AbstractMasterDetailTreeMvcController(S service, String baseUrl, String viewPath, String objName,
			String objCollectName) {
		super(service, baseUrl, viewPath, objName, objCollectName);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@GetMapping(URL_DTL_READ_TREE)
	public String showDetailTree(@PathVariable(PV_MAIN_ID) Long masterId, Locale locale, Model model) {
		List<T> entities = this.service.findByMasterId(masterId);
		if (entities.isEmpty()) {
			MessageHelper.addInfoAttribute(model, this.msPrefix.concat(".info.empty"));
		}
		log.info(this.objCollectName + " [" + URL_DTL_READ_TREE + "]: " + entities.size() + " counts");
		model.mergeAttributes(this.getModelAttributes(RM_READ));
		model.addAttribute(MA_TITLE_READ, this.ms.getMessage(this.msPrefix.concat(".title.read"), null, locale));
		model.addAttribute(this.objCollectName, entities);
		return this.getViewNameReadDelete();
	}

}
