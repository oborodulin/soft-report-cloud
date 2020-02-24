package com.oborodulin.softreport.domain.service;

import java.util.Optional;
import java.util.Set;

import com.oborodulin.softreport.domain.model.CommonJpaService;
import com.oborodulin.softreport.domain.model.valuesset.ValuesSet;
import com.oborodulin.softreport.domain.model.valuesset.value.Value;

public interface ValuesSetService extends CommonJpaService<ValuesSet, String> {
	public ValuesSet getById(Long id);

	public Optional<Set<Value>> findValuesBySetCode(String code);

	public Optional<Set<Value>> findValuesBySetId(Long id);

	public Value findValueById(Long id);

	public Value getNewValue(Long setId);

	public Value saveValue(Long setId, Value value);

	public void deleteValueById(Long id);

}
