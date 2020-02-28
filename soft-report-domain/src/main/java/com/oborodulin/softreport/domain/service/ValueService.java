package com.oborodulin.softreport.domain.service;

import java.util.List;
import org.springframework.data.domain.Sort;

import com.oborodulin.softreport.domain.common.service.CommonJpaDetailService;
import com.oborodulin.softreport.domain.model.valuesset.ValuesSet;
import com.oborodulin.softreport.domain.model.valuesset.value.Value;

public interface ValueService extends CommonJpaDetailService<ValuesSet, Value, String> {

	public List<Value> findByValuesSetCode(String code, Sort sort);

	public List<Value> findByValuesSetId(Long id, Sort sort);

}
