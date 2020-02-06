package com.oborodulin.softreport.rest.software;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import com.oborodulin.softreport.domain.software.Software;

public class SoftwareModelAssembler extends RepresentationModelAssemblerSupport<Software, SoftwareModel> {

	public SoftwareModelAssembler() {
		super(SoftwareRestController.class, SoftwareModel.class);
	}

	@Override
	protected SoftwareModel instantiateModel(Software software) {
		return new SoftwareModel(software);
	}

	@Override
	public SoftwareModel toModel(Software software) {
		return createModelWithId(software.getId(), software);
	}

}
