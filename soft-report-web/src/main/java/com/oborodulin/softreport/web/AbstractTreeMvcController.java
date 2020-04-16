package com.oborodulin.softreport.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import com.oborodulin.softreport.domain.common.entity.TreeEntity;
import com.oborodulin.softreport.domain.common.service.CommonJpaTreeService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractTreeMvcController<E extends TreeEntity<E, U>, S extends CommonJpaTreeService<E, U>, U>
		extends AbstractMvcController<E, S, U> implements CommonTreeMvcController<E, U> {

	public static final String PV_PARENT_ID = "parentId";

	protected static final String RM_CREATE_CHILD = "create-child";

	/**
	 * Конструктор. Инстанцирует объект.
	 * 
	 * @param service  сервис объекта доменной модели
	 * @param baseUrl  базовый URL контроллера
	 * @param viewPath путь к CRUD-шаблонам контроллера (каталог)
	 */
	@Autowired
	protected AbstractTreeMvcController(S service, String baseUrl, String viewPath, String objName,
			String objCollectName) {
		super(service, baseUrl, viewPath, objName, objCollectName);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getRedirectFromSlaveCreate(Long mainId) {
		log.info("getRedirectFromSlaveCreate: mainId = " + mainId);
		return this.getRedirectToRead();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> getShowCreateModelAttributes() {
		log.info("getShowCreateModelAttributes:");
		Map<String, Object> ma = new HashMap<>();
		ma.put(this.objCollectName, this.service.findAll());
		ma.forEach((key, value) -> log.debug(key + ":" + value));
		return ma;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> getShowCreateModelAttributes(Long parentId) {
		log.info("getShowCreateModelAttributes: parentId = " + parentId);
		return this.getShowCreateModelAttributes();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> getShowUpdateModelAttributes(Long id) {
		log.info("getShowUpdateModelAttributes: id = " + id);
		Map<String, Object> ma = new HashMap<>();
		ma.put(this.objCollectName, this.service.findByIdIsNot(id));
		ma.forEach((key, value) -> log.debug(key + ":" + value));
		return ma;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<E> getShowListEntities() {
		log.info("getShowListEntities:");
		return this.service.findByParentIsNull();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public E createSlaveEntity(Long mainId) {
		log.info("createSlaveEntity: mainId = " + mainId);
		return this.service.create(mainId);
	}

}
