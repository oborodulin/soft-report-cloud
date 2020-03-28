package com.oborodulin.softreport.domain.model.enterprise;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.oborodulin.softreport.domain.common.entity.TreeEntity;
import lombok.Data;

@Data
@Entity
@Table(name = Enterprise.TABLE_NAME)
public class Enterprise extends TreeEntity<Enterprise, String> {
	private static final long serialVersionUID = -510145406646880694L;
	protected static final String TABLE_NAME = "ENTERPRISES";

	@NotBlank
	@Column(unique = true)
	private String code;
	@NotBlank
	private String shortName;
	@NotBlank
	private String fullName;
	private String descr;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCodeId() {
		return this.code;
	}
}
