package com.oborodulin.softreport.domain.service;

import java.util.List;

import com.oborodulin.softreport.domain.common.service.CommonJpaDetailService;
import com.oborodulin.softreport.domain.model.dic.proglang.ProgLang;
import com.oborodulin.softreport.domain.model.dic.proglang.datatype.DataType;
import com.oborodulin.softreport.domain.model.dic.valuesset.value.Value;

public interface DataTypeService extends CommonJpaDetailService<ProgLang, DataType, String> {

	public List<Value> getSqlTypes();

}
