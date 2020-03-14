package com.oborodulin.softreport.domain.model.software.connection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.oborodulin.softreport.domain.common.entity.DetailEntity;
import com.oborodulin.softreport.domain.model.software.Software;

import lombok.Data;

@Data
@Entity
@Table(name = Connection.TABLE_NAME)
public class Connection extends DetailEntity<Software, String> {
	private static final long serialVersionUID = 552064751437275103L;

	protected static final String TABLE_NAME = "CONNECTIONS";

	/** URL-соединения */
	private String url;

	/** Параметры подключения (например, алиас в ) */
	@Column(length = 2000)
	private String params;

	/** Логин */
	private String login;

	/** Пароль */
	private String password;

}
