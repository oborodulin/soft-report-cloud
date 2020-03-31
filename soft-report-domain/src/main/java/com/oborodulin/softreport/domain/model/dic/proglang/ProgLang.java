package com.oborodulin.softreport.domain.model.dic.proglang;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.oborodulin.softreport.domain.common.entity.AuditableEntity;
import com.oborodulin.softreport.domain.common.entity.DetailEntity;
import com.oborodulin.softreport.domain.model.dic.proglang.datatype.DataType;
import com.oborodulin.softreport.domain.model.dic.proglang.uiobjecttype.UiObjectType;
import com.oborodulin.softreport.domain.model.dic.valuesset.value.Value;
import com.oborodulin.softreport.domain.model.software.Software;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Класс реализует описание и хранение языков программирования.
 * 
 * @author Oleg Borodulin
 *
 */
@Data
@Entity
@Table(name = ProgLang.TABLE_NAME)
public class ProgLang extends AuditableEntity<String> {

	private static final long serialVersionUID = -2566233527997372728L;

	/** Наименование таблицы данных доменного объекта (сущности) */
	protected static final String TABLE_NAME = "PROG_LANGS";

	/** Тип языка */
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "lang_code", unique = true, nullable = false)
	@ToString.Exclude
	private Value lang;

	/** Тип архитектуры ПО */
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "arch_code", nullable = false)
	@ToString.Exclude
	private Value arch;

	/** Список типов данных текущего языка программирования */
	@OneToMany(mappedBy = DetailEntity.CLM_MASTER, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@EqualsAndHashCode.Exclude
	private List<DataType> dataTypes = new ArrayList<>();

	/** Список типов UI объектов текущего языка программирования */
	@OneToMany(mappedBy = DetailEntity.CLM_MASTER, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@EqualsAndHashCode.Exclude
	private List<UiObjectType> uiObjectTypes = new ArrayList<>();

	/** Список ПО, использующих текущий язык программирования */
	@ManyToMany(mappedBy = "progLangs", fetch = FetchType.LAZY)
	private List<Software> softwares = new ArrayList<>();

	/**
	 * Возвращает строку-перечень кодов ПО через запятую
	 * 
	 * @return строка-перечень кодов ПО
	 */
	public String getSoftwareCodes() {
		StringBuilder sbString = new StringBuilder("");
		for (Software software : this.softwares) {
			sbString.append(software.getCodeId()).append(",");
		}
		String softwareCodes = sbString.toString();
		if (softwareCodes.length() > 0) {
			softwareCodes = softwareCodes.substring(0, softwareCodes.length() - 1);
		}
		return softwareCodes;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCodeId() {
		return this.lang.getVal();
	}

}
