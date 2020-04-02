package com.oborodulin.softreport.domain.service;

import java.util.List;
import org.springframework.data.domain.Sort;

import com.oborodulin.softreport.domain.common.service.CommonJpaDetailService;
import com.oborodulin.softreport.domain.model.dic.valuesset.ValuesSet;
import com.oborodulin.softreport.domain.model.dic.valuesset.value.Value;

public interface ValueService extends CommonJpaDetailService<ValuesSet, Value, String> {

	public List<Value> findByValuesSetCode(String code, Sort sort);

	public List<Value> findByValuesSetId(Long id, Sort sort);

	public Value getDocObjectDataBaseType();

	public Value getDocObjectSchemaType();

	public Value getDocObjectDataTableType();

	public Value getDocObjectDataTableColumnType();

	public Value getDocObjectDataTableColumnValueType();
	
	public Value getDocObjectDataTableTriggerType();

	public Value getDocObjectDataBaseViewType();

	public Value getDocObjectDataBaseViewColumnType();

	public Value getDocObjectDataBaseFuncType();

	public Value getDocObjectDataBaseProcType();

	public Value getServerDbType();
}
