package com.oborodulin.softreport.domain.service;

import com.oborodulin.softreport.domain.model.JpaAbstractService;
import com.oborodulin.softreport.domain.model.software.Software;
import com.oborodulin.softreport.domain.model.software.SoftwareRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Service("jpaSoftwareService")
@Transactional
public class SoftwareServiceImpl extends JpaAbstractService<Software, SoftwareRepository, String>
		implements SoftwareService {

	@Autowired
		public SoftwareServiceImpl(SoftwareRepository repository) {
			super(repository);
		}

	@Override
	public Software getById(Long id) {
		return this.repository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid valuesSet Id:" + id));
	}

	@Override
	public List<Software> findByTypeCode(String typeCode) {
		return this.repository.findByTypeCode(typeCode);
	}

}
