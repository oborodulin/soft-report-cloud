package com.oborodulin.softreport.domain.service;

import com.oborodulin.softreport.domain.common.service.JpaDetailAbstractService;
import com.oborodulin.softreport.domain.model.valuesset.ValuesSet;
import com.oborodulin.softreport.domain.model.valuesset.ValuesSetRepository;
import com.oborodulin.softreport.domain.model.valuesset.value.Value;
import com.oborodulin.softreport.domain.model.valuesset.value.ValueRepository;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Service("jpaValuesSetService")
@Transactional
public class ValueServiceImpl
		extends JpaDetailAbstractService<ValuesSet, Value, ValuesSetRepository, ValueRepository, String>
		implements ValueService {
	@Autowired
	public ValueServiceImpl(ValuesSetRepository masterRepository, ValueRepository repository) {
		super(masterRepository, repository, Value.class);
	}

	@Override
	public List<Value> findByValuesSetCode(String code, Sort sort) {
		return this.repository.findByValuesSetCode(code, sort);
	}

	@Override
	public List<Value> findByValuesSetId(Long id, Sort sort) {
		return this.repository.findByValuesSetId(id, sort);
	}
}
