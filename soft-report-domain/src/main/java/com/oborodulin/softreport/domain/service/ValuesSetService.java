package com.oborodulin.softreport.domain.service;

import java.util.List;
import java.util.Optional;
import com.oborodulin.softreport.domain.common.service.CommonJpaService;
import com.oborodulin.softreport.domain.model.dic.valuesset.ValuesSet;
import com.oborodulin.softreport.domain.model.dic.valuesset.value.Value;

public interface ValuesSetService extends CommonJpaService<ValuesSet, String> {

	public List<Value> findValuesBySetCode(String code);

	public Optional<List<Value>> findValuesBySetId(Long id);

	public List<Value> getServersTypes();

	public List<Value> getEnvTypes();

	public List<Value> getSortDirections();

	public List<Value> getCfgBundleTypes();

	public List<Value> getSoftwareTypes();

	public List<Value> getSoftwareArchs();

	public List<Value> getSoftwareTechs();

	public List<Value> getDocCategs();

	public List<Value> getDocTypes();

	public List<Value> getDbTypes();

	public List<Value> getDtTypes();

	public List<Value> getDbColumnTypes();

	public List<Value> getSqlDataTypes();

	public List<Value> getDocObjTypes();

	public List<Value> getDocObjEvents();

	public List<Value> getDocObjEventActions();

	public List<Value> getRuleTypes();

	public List<Value> getRuleCompareTypes();

	public List<Value> getRuleOperators();
}
