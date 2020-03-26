package com.oborodulin.softreport.domain.service;

import com.oborodulin.softreport.domain.common.service.JpaDetailAbstractService;
import com.oborodulin.softreport.domain.model.dic.proglang.ProgLang;
import com.oborodulin.softreport.domain.model.dic.proglang.ProgLangRepository;
import com.oborodulin.softreport.domain.model.dic.proglang.datatype.DataType;
import com.oborodulin.softreport.domain.model.dic.proglang.datatype.DataTypeRepository;
import com.oborodulin.softreport.domain.model.dic.valuesset.value.Value;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Service("jpaDataTypeService")
@Transactional
public class DataTypeServiceImpl
		extends JpaDetailAbstractService<ProgLang, DataType, ProgLangRepository, DataTypeRepository, String>
		implements DataTypeService {
	@Autowired
	private ValuesSetService valuesSetService;

	@Autowired
	public DataTypeServiceImpl(ProgLangRepository masterRepository, DataTypeRepository repository) {
		super(masterRepository, repository, DataType.class);
	}

	@Override
	public List<Value> getSqlTypes() {
		return valuesSetService.getSqlDataTypes();
	};

	public Map<String, List<DataType>> getDataTypes(String archCode) {
		Map<String, List<DataType>> backendTypes = new HashMap<>();
		for (ProgLang backendProgLang : masterRepository.findByArch_CodeOrderByArch_ValAsc(archCode)) {
			backendTypes.put(backendProgLang.getLang().getVal(), backendProgLang.getDataTypes());
		}
		return backendTypes;
	};

	@Override
	public Map<String, List<DataType>> getBackendTypes() {
		return this.getDataTypes(Value.VC_ARCH_BACK);
	};

	@Override
	public Map<String, List<DataType>> getFrontendTypes() {
		return this.getDataTypes(Value.VC_ARCH_FRONT);
	};

}
