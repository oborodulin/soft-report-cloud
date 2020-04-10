package com.oborodulin.softreport.domain.model.dic.proglang.datatype;

import java.util.List;

import com.oborodulin.softreport.domain.common.repository.CommonDetailRepository;
import com.oborodulin.softreport.domain.model.dic.proglang.ProgLang;
import com.oborodulin.softreport.domain.model.dic.valuesset.value.Value;

import org.springframework.data.domain.Sort;

public interface DataTypeRepository extends CommonDetailRepository<ProgLang, DataType, String> {

	public List<DataType> findByMaster_DbType(Value dbType, Sort sort);

}
