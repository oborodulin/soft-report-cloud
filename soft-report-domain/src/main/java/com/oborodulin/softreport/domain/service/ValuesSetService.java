package com.oborodulin.softreport.domain.service;

import java.util.Optional;
import java.util.Set;

import com.oborodulin.softreport.domain.common.service.CommonJpaService;
import com.oborodulin.softreport.domain.model.dic.valuesset.ValuesSet;
import com.oborodulin.softreport.domain.model.dic.valuesset.value.Value;

public interface ValuesSetService extends CommonJpaService<ValuesSet, String> {

	public Optional<Set<Value>> findValuesBySetCode(String code);

	public Optional<Set<Value>> findValuesBySetId(Long id);

}
