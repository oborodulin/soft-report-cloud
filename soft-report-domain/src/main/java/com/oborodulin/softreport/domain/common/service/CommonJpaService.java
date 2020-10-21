package com.oborodulin.softreport.domain.common.service;

import java.util.List;
import java.util.Optional;

import com.oborodulin.softreport.domain.common.entity.AuditableEntity;

/**
 * Базовый интерфейс JPA-сервисов всех сущностей модели
 * <p>
 * Описывает базовые операции сервисов: поиск сущностей, их создание,
 * инициализацию, сохранение и удаление
 * 
 * @author Oleg Borodulin
 * @version 1.0
 */
public interface CommonJpaService<E extends AuditableEntity<U>, U> {

	/**
	 * Возвращает список всех сущностей
	 * 
	 * @return список всех сущностей
	 */
	public List<E> entities();

	/**
	 * Возвращает возможную cущность по заданному идентификатору
	 * 
	 * @param id идентификатор сущности
	 * @return возможная cущность
	 */
	public Optional<E> optionalEntity(Long id);

	/**
	 * Возвращает cущность по заданному идентификатору
	 * 
	 * @param id идентификатор сущности
	 * @return cущность или {@code null}
	 */
	public E entity(Long id);

	/**
	 * Возвращает созданную cущность
	 * 
	 * @return созданная cущность
	 */
	public E createdEntity();

	/**
	 * Возвращает проинициализированную cущность
	 * 
	 * @param entity сущность, которую требуется проинициализировать
	 * @return проинициализированная cущность
	 */
	public E initializedEntity(E entity);

	/**
	 * Возвращает список проинициализированных cущностей
	 * 
	 * @param entities список сущностей, которые требуется проинициализировать
	 * @return список проинициализированных cущностей
	 */
	public List<E> initializedEntities(List<E> entities);

	/**
	 * Сохраняет заданную cущность
	 * 
	 * @param entity сохраняемая сущность
	 */
	public void save(E entity);

	/**
	 * Удаляет заданную cущность
	 * 
	 * @param entity удаляемая сущность
	 */
	public void delete(E entity);

	/**
	 * Удаляет cущность с заданным идентификатором
	 * 
	 * @param id идентификатор сущности
	 */
	public void delete(Long id);
}
