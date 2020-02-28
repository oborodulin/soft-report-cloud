package com.oborodulin.softreport.domain.service;

import com.oborodulin.softreport.domain.common.service.JpaAbstractService;
import com.oborodulin.softreport.domain.model.valuesset.ValuesSet;
import com.oborodulin.softreport.domain.model.valuesset.ValuesSetRepository;
import com.oborodulin.softreport.domain.model.valuesset.value.Value;
import com.oborodulin.softreport.domain.model.valuesset.value.ValueRepository;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Service("jpaValuesSetService")
@Transactional
public class ValuesSetServiceImpl extends JpaAbstractService<ValuesSet, ValuesSetRepository, String>
		implements ValuesSetService {
	@Autowired
	private ValueRepository valueRepository;

	@Autowired
	public ValuesSetServiceImpl(ValuesSetRepository repository) {
		super(repository);
	}

	@Override
	public Optional<Set<Value>> findValuesBySetCode(String code) {
		Optional<ValuesSet> valuesSet = this.repository.findByCode(code);

		return valuesSet.isPresent() ? Optional.ofNullable(valuesSet.get().getValues()) : Optional.empty();
	}

	@Override
	public Optional<Set<Value>> findValuesBySetId(Long id) {
		Optional<ValuesSet> valuesSet = this.repository.findById(id);

		return valuesSet.isPresent() ? Optional.ofNullable(valuesSet.get().getValues()) : Optional.empty();
	}

}
