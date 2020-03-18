package com.oborodulin.softreport.domain.common.entity;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditableEntity<U> implements Serializable {
	private static final long serialVersionUID = -9180531813274000726L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(updatable = false)
	protected Long id;

	/**
	 * Номер версии записи.
	 * 
	 * Необходим для того, чтобы JPA определил необходимо создавать Entity или
	 * обновлять его. Для этого номер версии должен присутствовать в html-форме
	 * обновления данных.
	 */
	@Version
	private Long version;

	@CreatedBy
	protected U createdBy;

	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss.SSS")
	protected Date createdDate;

	@LastModifiedBy
	protected U lastModifiedBy;

	@LastModifiedDate
	@Temporal(TemporalType.TIMESTAMP)
	protected Date lastModifiedDate;
}
