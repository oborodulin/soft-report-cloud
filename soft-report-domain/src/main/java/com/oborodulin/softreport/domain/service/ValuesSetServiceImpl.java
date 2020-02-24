package com.oborodulin.softreport.domain.service;

import com.oborodulin.softreport.domain.model.JpaAbstractService;
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
	public ValuesSet getById(Long id) {
		return this.repository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid valuesSet Id:" + id));
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

	@Override
	public Value findValueById(Long id) {
		return valueRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid value Id:" + id));
	};

	@Override
	public Value saveValue(Long setId, Value value) {
		value.setValuesSet(this.repository.findById(setId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid valuesSet Id:" + setId)));
		return valueRepository.save(value);
	};

	@Override
	public Value getNewValue(Long setId) {
		Value value = new Value();
		value.setValuesSet(this.repository.findById(setId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid valuesSet Id:" + setId)));
		return value;
	};

	@Override
	public void deleteValueById(Long id) {
		valueRepository.deleteById(id);
	};

}
