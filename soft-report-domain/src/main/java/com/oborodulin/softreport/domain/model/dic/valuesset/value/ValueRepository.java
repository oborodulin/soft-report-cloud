package com.oborodulin.softreport.domain.model.dic.valuesset.value;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.oborodulin.softreport.domain.common.repository.CommonDetailRepository;
import com.oborodulin.softreport.domain.model.dic.valuesset.ValuesSet;

import org.springframework.data.domain.Sort;

@Repository
public interface ValueRepository extends CommonDetailRepository<ValuesSet, Value, String> {
	public List<Value> findByMasterCode(String code, Sort sort);

	public List<Value> findByValuesSetCode(String code, Sort sort);
	
}