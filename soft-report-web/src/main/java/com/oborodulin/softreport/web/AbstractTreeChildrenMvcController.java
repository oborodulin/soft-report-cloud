package com.oborodulin.softreport.web;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import com.oborodulin.softreport.domain.common.entity.AuditableEntity;
import com.oborodulin.softreport.domain.common.entity.TreeEntity;
import com.oborodulin.softreport.domain.common.service.CommonJpaTreeService;

public abstract class AbstractTreeChildrenMvcController<T extends TreeEntity<T, U>, S extends CommonJpaTreeService<T, U>, U>
		extends AbstractMvcController<T, S, U> implements CommonTreeChildrenMvcController<T, U> {

	/**
	 * Конструктор. Инстанцирует объект.
	 * 
	 * @param parentService сервис главного объекта доменной модели
	 * @param service       сервис подчинённого объекта доменной модели
	 * @param baseUrl       базовый URL контроллера
	 * @param viewPath      путь к CRUD-шаблонам контроллера (каталог)
	 */
	@Autowired
	protected AbstractTreeChildrenMvcController(S service, String baseUrl, String viewPath, String objName,
			String collectObjName) {
		super(service, baseUrl, viewPath, objName, collectObjName);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AuditableEntity<U> getShowListMainEntity(Long mainId) {
		return this.service.entity(mainId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<T> getShowListSlavesEntities(Long mainId) {
		return this.service.initializedEntities(this.service.findByParentId(mainId, Sort.by(Sort.Direction.ASC, this.getSortPropName())));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public T createSlaveEntity(Long mainId) {
		return this.service.createdEntity(mainId);// this.service.getById(mainId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public T getMainEntity(T slaveEntity) {
		return slaveEntity.getParent();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveSlaveEntity(Long mainId, T slaveEntity) {
		this.service.save(mainId, slaveEntity);
	}
	
	/**
	 * Возвращает строковый идентификатор главного объекта.
	 * 
	 * @param parent главный объект
	 * @param isCode признак возврата значения кода {@code getCode()} или
	 *               наименование {@code getName()}
	 * @return строковый идентификатор главного объекта (код или наименование)
	 */
	/*
	 * private String getMasterIdentifier(E parent) { Object result = null; try {
	 * Class<?> clazz = parent.getClass(); if (clazz != null) { Method method =
	 * null;
	 * 
	 * switch (this.titleMaster) { case CODE: method =
	 * clazz.getDeclaredMethod("getCode", parent.getClass()); break; case NAME:
	 * method = clazz.getDeclaredMethod("getName", parent.getClass()); }
	 * 
	 * result = method.invoke(null); } } catch (Exception e) { e.printStackTrace();
	 * } return (String) result; }
	 */

}
