package com.oborodulin.softreport.domain.service;

import java.util.List;
import java.util.Map;

import com.oborodulin.softreport.domain.common.service.CommonJpaDetailService;
import com.oborodulin.softreport.domain.model.dic.proglang.ProgLang;
import com.oborodulin.softreport.domain.model.dic.proglang.datatype.DataType;
import com.oborodulin.softreport.domain.model.dic.valuesset.value.Value;

public interface DataTypeService extends CommonJpaDetailService<ProgLang, DataType, String> {

	public List<Value> getSqlTypes();

	public Map<String, List<DataType>> getBackendTypes();

	public Map<String, List<DataType>> getFrontendTypes();

}
