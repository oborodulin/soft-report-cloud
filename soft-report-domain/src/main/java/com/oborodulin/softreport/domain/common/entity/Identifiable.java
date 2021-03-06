package com.oborodulin.softreport.domain.common.entity;

/**
 * Интерфейс определяет обязательные методы и данные для идентификации всех доменных объектов
 * <p>
 * Интерфейс предполагает, что по всем доменным объектам будет задействован
 * аудит Hibernate Envers/Spring Data JPA.
 * 
 * @author Oleg Borodulin
 * @version 1.0
 */
public interface Identifiable {
	/**
	 * Возвращает символьный код-идентификатор доменного объекта
	 * <p>
	 * Используется для визуального идентифицирования объекта в слое представления,
	 * например, на HTML-странице в заголовке главного (родительского) доменного
	 * объекта при работе с подчинёнными (дочерними) доменными объектами
	 * 
	 * @return символьный код-идентификатор
	 */
	public String codeId();
}
