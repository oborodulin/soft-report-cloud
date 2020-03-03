package com.oborodulin.softreport.domain.model.document.version;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import com.oborodulin.softreport.domain.common.entity.DetailEntity;
import com.oborodulin.softreport.domain.model.document.Document;
import lombok.Data;

@Data
@Entity
@Table(name = Version.TABLE_NAME)
public class Version extends DetailEntity<Document, String> {
	private static final long serialVersionUID = 5298352106891089618L;
	public static final String TABLE_NAME = "VERSIONS";

	@NotBlank(message = "Name is required")
	private String name;
}
