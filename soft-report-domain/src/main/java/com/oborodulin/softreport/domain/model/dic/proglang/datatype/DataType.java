package com.oborodulin.softreport.domain.model.dic.proglang.datatype;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import com.oborodulin.softreport.domain.common.entity.DetailEntity;
import com.oborodulin.softreport.domain.model.dic.proglang.ProgLang;
import com.oborodulin.softreport.domain.model.dic.proglang.datatype.dataformat.DataFormat;
import com.oborodulin.softreport.domain.model.dic.valuesset.value.Value;
import com.oborodulin.softreport.domain.model.docobject.DocObject;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@Table(name = DataType.TABLE_NAME)
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class DataType extends DetailEntity<ProgLang, String> {

	private static final long serialVersionUID = -2045805271638510988L;

	protected static final String TABLE_NAME = "DATA_TYPES";

	/** Наименование */
	private String name;

	/** Выражение получения значения по умолчанию */
	private String defValExpr;

	/** Описание */
	private String descr;

	/** Тип данных SQL */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "sql_type_code")
	@ToString.Exclude
	private Value sqlType;

	/** Описание */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "backend_id")
	@ToString.Exclude
	private DataType backendType;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "frontend_id")
	@ToString.Exclude
	private DataType frontendType;

	@OneToMany(mappedBy = DetailEntity.CLM_MASTER, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@EqualsAndHashCode.Exclude
	private List<DataFormat> formats = new ArrayList<>();

	/** Список объектов БД/UI, текущего типа данных */
	@OneToMany(mappedBy = "dataType", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@EqualsAndHashCode.Exclude
	private List<DocObject> docObjects = new ArrayList<>();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCodeId() {
		return this.name;
	}

}
