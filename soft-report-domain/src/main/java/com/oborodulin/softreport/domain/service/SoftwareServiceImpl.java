package com.oborodulin.softreport.domain.service;

import com.oborodulin.softreport.domain.common.service.JpaTreeAbstractService;
import com.oborodulin.softreport.domain.model.software.Software;
import com.oborodulin.softreport.domain.model.software.SoftwareRepository;
import com.oborodulin.softreport.domain.model.valuesset.ValuesSet;
import com.oborodulin.softreport.domain.model.valuesset.value.Value;
import com.oborodulin.softreport.domain.model.valuesset.value.ValueRepository;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Service("jpaSoftwareService")
@Transactional
public class SoftwareServiceImpl extends JpaTreeAbstractService<Software, SoftwareRepository, String>
		implements SoftwareService {
	@Autowired
	private ValueRepository valueRepository;

	@Autowired
	public SoftwareServiceImpl(SoftwareRepository repository) {
		super(repository);
	}

	@Override
	public List<Software> findByTypeCode(String typeCode) {
		return this.repository.findByTypeCode(typeCode);
	}

	@Override
	public Software getNewChild(Long parentId) {
		Software parent = this.repository.findById(parentId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid software parent Id:" + parentId));
		Software software = new Software();
		software.setParent(parent);
		software.setType(parent.getType());
		return software;
	};

	@Override
	public List<Value> getTypes() {
		return valueRepository.findByValuesSetCode(ValuesSet.VS_SOFTWARE_TYPES, Sort.by("code"));
	};

	@Override
	public List<Software> findByParentIsNull() {
		return this.repository.findByParentIsNull();
	};

	@Override
	public List<Software> findByIdIsNot(Long id) {
		return this.repository.findByIdIsNot(id);
	};
}
