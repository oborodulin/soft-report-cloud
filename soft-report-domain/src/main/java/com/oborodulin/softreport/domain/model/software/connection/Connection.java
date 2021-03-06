package com.oborodulin.softreport.domain.model.software.connection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import com.oborodulin.softreport.domain.common.entity.DetailEntity;
import com.oborodulin.softreport.domain.model.software.Software;

import lombok.Data;

@Data
@Entity
@Table(name = Connection.TABLE_NAME)
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class Connection extends DetailEntity<Software, String> {
	private static final long serialVersionUID = 552064751437275103L;

	/** Наименование таблицы данных доменного объекта (сущности) */
	protected static final String TABLE_NAME = "CONNECTIONS";

	/** Наименование */
	private String name;

	/** URL-соединения */
	private String url;

	/** Параметры подключения (например, алиас в ) */
	@Column(length = 2000)
	private String params;

	/** Логин */
	private String login;

	/** Пароль */
	private String password;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String codeId() {
		return this.name;
	}
	
}
