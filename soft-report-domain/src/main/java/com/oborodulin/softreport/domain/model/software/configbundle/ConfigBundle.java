package com.oborodulin.softreport.domain.model.software.configbundle;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.oborodulin.softreport.domain.common.entity.DetailEntity;
import com.oborodulin.softreport.domain.model.dic.valuesset.value.Value;
import com.oborodulin.softreport.domain.model.docobject.DocObject;
import com.oborodulin.softreport.domain.model.software.Software;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = ConfigBundle.TABLE_NAME)
public class ConfigBundle extends DetailEntity<Software, String> {

	private static final long serialVersionUID = 4401547574205093586L;

	protected static final String TABLE_NAME = "CONFIG_BUNDLES";

	/** Наименование */
	@Column(length = 500)
	private String name;

	/** Тип конфигурационного пакета */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "type_code")
	@ToString.Exclude
	private Value type;
	
	/** Поле ТД, описывающее параметры */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "dt_column_id")
	@ToString.Exclude
	private DocObject dtColumn;

}
