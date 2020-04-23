package com.oborodulin.softreport.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import com.oborodulin.softreport.domain.common.entity.AuditableEntity;
import com.oborodulin.softreport.domain.common.entity.DetailEntity;
import com.oborodulin.softreport.domain.common.service.CommonJpaDetailService;
import com.oborodulin.softreport.domain.common.service.CommonJpaService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractMasterDetailMvcController<E extends AuditableEntity<U>, D extends DetailEntity<E, U>, M extends CommonJpaService<E, U>, S extends CommonJpaDetailService<E, D, U>, U>
		extends AbstractMvcController<D, S, U> implements CommonMasterDetailMvcController<E, D, U> {

	protected final M masterService;

	/** Наименование коллекции главных объектов контроллера */
	protected String masterObjCollectName;

	/**
	 * Конструктор. Инстанцирует объект.
	 * 
	 * @param masterService сервис главного объекта доменной модели
	 * @param service       сервис подчинённого объекта доменной модели
	 * @param baseUrl       базовый URL контроллера
	 * @param viewPath      путь к CRUD-шаблонам контроллера (каталог)
	 */
	@Autowired
	protected AbstractMasterDetailMvcController(M masterService, String masterObjCollectName, S service, String baseUrl,
			String viewPath, String objName, String objCollectName) {
		super(service, baseUrl, viewPath, objName, objCollectName);
		this.masterService = masterService;
		this.masterObjCollectName = masterObjCollectName;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> getShowCreateModelAttributes() {
		log.info("getShowCreateModelAttributes:");
		Map<String, Object> ma = new HashMap<>();
		ma.put(this.masterObjCollectName, this.masterService.findAll());
		return ma;
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
		return this.masterService.getById(mainId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<D> getShowListSlavesEntities(Long mainId) {
		return this.service.findByMasterId(mainId, Sort.by(Sort.Direction.ASC, this.getSortPropName()));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public D createSlaveEntity(Long mainId) {
		return this.service.create(mainId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public E getMainEntity(D slaveEntity) {
		return slaveEntity.getMaster();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveSlaveEntity(Long mainId, D slaveEntity) {
		this.service.save(mainId, slaveEntity);
	}

}
