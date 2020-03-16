package com.oborodulin.softreport.domain.model.dic.proglang;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import com.oborodulin.softreport.domain.common.entity.AuditableEntity;
import com.oborodulin.softreport.domain.model.dic.valuesset.value.Value;
import com.oborodulin.softreport.domain.model.software.Software;

import lombok.Data;
import lombok.ToString;

/**
 * Класс реализует описание и хранение языков программирования.
 * 
 * @author acs-i
 *
 */
@Data
@Entity
@Table(name = ProgLang.TABLE_NAME)
public class ProgLang extends AuditableEntity<String> {

	private static final long serialVersionUID = -2566233527997372728L;

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

	/** Список ПО, использующих текущий язык программирования */
	@ManyToMany(mappedBy = "progLangs", fetch = FetchType.LAZY)
	private List<Software> softwares = new ArrayList<>();
}
