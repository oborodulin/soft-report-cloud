package com.oborodulin.softreport.rest.software;

import java.util.Date;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.Getter;
import com.oborodulin.softreport.domain.software.Software;

@Relation(value = "software", collectionRelation = "softwares")
public class SoftwareModel extends RepresentationModel<SoftwareModel> {

	@Getter
	private final String name;

	@Getter
	private final Date createdAt;

	public SoftwareModel(Software software) {
		this.name = software.getName();
		this.createdAt = software.getCreatedAt();
	}

}
