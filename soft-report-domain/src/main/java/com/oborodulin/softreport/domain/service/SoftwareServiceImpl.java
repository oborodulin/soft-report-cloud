package com.oborodulin.softreport.domain.service;

import com.oborodulin.softreport.domain.common.service.JpaTreeAbstractService;
import com.oborodulin.softreport.domain.model.dic.valuesset.value.Value;
import com.oborodulin.softreport.domain.model.software.Software;
import com.oborodulin.softreport.domain.model.software.SoftwareRepository;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Service("jpaSoftwareService")
@Transactional
public class SoftwareServiceImpl extends JpaTreeAbstractService<Software, SoftwareRepository, String>
		implements SoftwareService {
	@Autowired
	private ValuesSetService valuesSetService;

	@Autowired
	public SoftwareServiceImpl(SoftwareRepository repository) {
		super(repository, Software.class);
	}

	@Override
	public List<Software> findByTypeCode(String typeCode) {
		return this.repository.findByTypeCode(typeCode);
	}

	@Override
	public Software createChild(Long parentId) {
		Software software = super.createChild(parentId);
		software.setType(software.getParent().getType());
		return software;
	};

	@Override
	public List<Value> getTypes() {
		return valuesSetService.getSoftwareTypes();
	};
}
