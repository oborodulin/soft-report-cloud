package com.oborodulin.softreport.domain.model.dic.dbhierarchy;

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
 * Класс описывает иерархию объектов базы данных (БД).
 * 
 * @author acs-i
 *
 */
@Data
@Entity
@Table(name = DbHierarchy.TABLE_NAME)
public class DbHierarchy extends TreeEntity<DbHierarchy, String> {

	private static final long serialVersionUID = 3850158095617591531L;

	public static final String TABLE_NAME = "DBHIERARCHES";

	/** Тип объекта БД */
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "obj_code", nullable = false)
	@ToString.Exclude
	private Value type;

}
