package com.oborodulin.softreport.domain.model.dic.valuesset.value;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.oborodulin.softreport.domain.common.repository.CommonDetailRepository;
import com.oborodulin.softreport.domain.model.dic.valuesset.ValuesSet;

import org.springframework.data.domain.Sort;

@Repository
public interface ValueRepository extends CommonDetailRepository<ValuesSet, Value, String> {
	public List<Value> findByMaster_Code(String masterCode, Sort sort);

	public Value findByCode(String code);
	
//	public List<Value> findByMaster_CodeAndAttr1AndAttr2OrderByCodeAsc(String masterCode, String attr1Val, String attr2Val);

//	public List<Value> findByMaster_CodeAndAttr2OrderByCodeAsc(String masterCode, String attr2Val);

//	public List<Value> findByValuesSetCode(String code, Sort sort);

}