package com.oborodulin.softreport.domain.model.software.configbundle.configparameter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import com.oborodulin.softreport.domain.common.entity.DetailEntity;
import com.oborodulin.softreport.domain.model.software.configbundle.ConfigBundle;

import lombok.Data;

@Data
@Entity
@Table(name = ConfigParameter.TABLE_NAME)
@Audited
public class ConfigParameter extends DetailEntity<ConfigBundle, String> {
	private static final long serialVersionUID = 7387119975449292007L;

	/** Наименование таблицы данных доменного объекта (сущности) */
	protected static final String TABLE_NAME = "CONFIG_PARAMETERS";

	/** Наименование */
	private String name;

	/** Описание */
	@Column(length = 1000)
	private String descr;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCodeId() {
		return this.name;
	}
	
}
