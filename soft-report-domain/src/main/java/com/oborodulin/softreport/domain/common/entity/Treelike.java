package com.oborodulin.softreport.domain.common.entity;

public interface Treelike<T extends TreeEntity<T, U>, U> {
	/**
	 * Добавляет дочерний объект к текущему родительскому.
	 * 
	 * @param child дочерний объект
	 */
	public void addСhild(T child);
}
