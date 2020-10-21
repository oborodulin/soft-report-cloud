package com.oborodulin.softreport.domain.common.service;

import java.util.List;
import org.springframework.data.domain.Sort;

import com.oborodulin.softreport.domain.common.entity.AuditableEntity;

/**
 * Базовый интерфейс JPA-сервисов подчинённых сущностей модели
 * <p>
 * Описывает специфические операции подчинённых сущностей: поиск, создание и
 * сохранение
 * 
 * @author Oleg Borodulin
 * @version 1.0
 */
public interface CommonJpaDetailService<E extends AuditableEntity<U>, D extends AuditableEntity<U>, U>
		extends CommonJpaService<D, U> {

	/**
	 * Возвращает отсортированный список подчинённых сущностей по заданной главной
	 * сущности
	 * 
	 * @param master главная сущность
	 * @param sort   сортировка (свойства и направления)
	 * @return отсортированный список подчинённых сущностей
	 */
	public List<D> entities(E master, Sort sort);

	/**
	 * Возвращает отсортированный список подчинённых сущностей по заданному
	 * идентификатору главной сущности
	 * 
	 * @param masterId идентификатор главной сущности
	 * @param sort     сортировка (свойства и направления)
	 * @return отсортированный список подчинённых сущностей
	 */
	public List<D> entities(Long masterId, Sort sort);

	/**
	 * Возвращает созданную подчинённую сущность с заданным идентификатором главной
	 * сущности
	 * 
	 * @param masterId идентификатор главной сущности
	 * @return созданная подчинённая сущность
	 */
	public D createdEntity(Long masterId);

	/**
	 * Сохраняет подчинённую сущность с заданным идентификатором главной сущности
	 * 
	 * @param masterId идентификатор главной сущности
	 * @param entity   сохраняемая подчинённая сущность
	 */
	public void save(Long masterId, D entity);

}
