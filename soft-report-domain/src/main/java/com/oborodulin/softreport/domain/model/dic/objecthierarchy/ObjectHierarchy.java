package com.oborodulin.softreport.domain.model.dic.objecthierarchy;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.oborodulin.softreport.domain.common.entity.TreeEntity;
import com.oborodulin.softreport.domain.model.dic.valuesset.value.Value;
import lombok.Data;
import lombok.ToString;

/**
 * Класс описывает иерархию объектов базы данных (БД) и пользовательского интерфейса (UI),
 * в т.ч. событийную модель.
 * 
 * @author acs-i
 *
 */
@Data
@Entity
@Table(name = ObjectHierarchy.TABLE_NAME)
public class ObjectHierarchy extends TreeEntity<ObjectHierarchy, String> {

	private static final long serialVersionUID = 3850158095617591531L;

	public static final String TABLE_NAME = "OBJECT_HIERARCHES";

	/** Тип архитектуры ПО */
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "arch_code", nullable = false)
	@ToString.Exclude
	private Value arch;

	/** Тип объекта БД */
	@ManyToOne
	@JoinColumn(name = "db_obj_code")
	@ToString.Exclude
	private Value dbType;


	/** Тип объекта UI */
	@ManyToOne
	@JoinColumn(name = "ui_obj_code")
	@ToString.Exclude
	private Value uiType;
	
}
