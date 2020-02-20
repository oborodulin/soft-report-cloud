package com.oborodulin.softreport.domain.model.valuesset;

import java.util.Optional;
import java.util.Set;

import com.oborodulin.softreport.domain.model.CommonJpaService;
import com.oborodulin.softreport.domain.model.valuesset.value.Value;

public interface ValuesSetService extends CommonJpaService<ValuesSet, String> {

	public Optional<Set<Value>> getValuesBySetCode(String code);
}
