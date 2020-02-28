package com.oborodulin.softreport.domain.model.valuesset.value;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.oborodulin.softreport.domain.common.repository.CommonRepository;
import com.oborodulin.softreport.domain.model.valuesset.ValuesSet;

import org.springframework.data.domain.Sort;

@Repository
public interface ValueRepository extends CommonRepository<Value, String> {
	public List<Value> findByValuesSet(ValuesSet valuesSet, Sort sort);

	public List<Value> findByValuesSetCode(String code, Sort sort);

	public List<Value> findByValuesSetId(Long id, Sort sort);

}