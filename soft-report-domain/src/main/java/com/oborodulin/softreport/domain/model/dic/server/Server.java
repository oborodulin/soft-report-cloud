package com.oborodulin.softreport.domain.model.dic.server;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import com.oborodulin.softreport.domain.common.entity.AuditableEntity;
import com.oborodulin.softreport.domain.model.dic.valuesset.value.Value;
import com.oborodulin.softreport.domain.model.docobject.DocObject;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@Table(name = Server.TABLE_NAME)
public class Server extends AuditableEntity<String> {

	private static final long serialVersionUID = 5526300525843873694L;

	/** Наименование таблицы данных доменного объекта (сущности) */
	protected static final String TABLE_NAME = "SERVERS";

	/** Хост */
	@NotBlank
	@Column(unique = true)
	private String host;

	/** Порт */
	private Long port;

	/** Экземпляр */
	private String instance;
	
	/** Описание */
	private String descr;
	
	/** Тип сервера */
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "type_code", nullable = false)
	@ToString.Exclude
	private Value type;

	/** Тип окружения */
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "env_code", nullable = false)
	@ToString.Exclude
	private Value env;
	
	/** Список объектов БД, связанных с текущим сервером */
	@OneToMany(mappedBy = "server", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@EqualsAndHashCode.Exclude
	private List<DocObject> docObjects = new ArrayList<>();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCodeId() {
		return this.host;
	}
	
}
