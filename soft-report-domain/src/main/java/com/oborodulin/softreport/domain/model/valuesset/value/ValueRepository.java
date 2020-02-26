package com.oborodulin.softreport.domain.model.valuesset.value;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.oborodulin.softreport.domain.model.CommonRepository;
import com.oborodulin.softreport.domain.model.valuesset.ValuesSet;

import org.springframework.data.domain.Sort;

@Repository
public interface ValueRepository extends CommonRepository<Value, String> {
	List<Value> findByValuesSet(ValuesSet valuesSet, Sort sort);

	List<Value> findByValuesSetCode(String valuesSetCode, Sort sort);

}