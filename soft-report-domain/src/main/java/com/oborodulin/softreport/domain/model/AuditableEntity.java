package com.oborodulin.softreport.domain.model;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditableEntity<U> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Version
	private Integer version;
	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	protected Date createdAt;
	@CreatedBy
	protected String createdBy;
	@LastModifiedBy
	protected String modifiedBy;
	@LastModifiedDate
	@Temporal(TemporalType.TIMESTAMP)
	protected Date modifiedAt;
}
