package com.oborodulin.softreport.domain.service;

import com.oborodulin.softreport.domain.common.service.AbstractJpaDetailService;
import com.oborodulin.softreport.domain.model.dic.proglang.ProgLang;
import com.oborodulin.softreport.domain.model.dic.proglang.ProgLangRepository;
import com.oborodulin.softreport.domain.model.dic.proglang.datatype.DataType;
import com.oborodulin.softreport.domain.model.dic.proglang.datatype.DataTypeRepository;
import com.oborodulin.softreport.domain.model.dic.valuesset.value.Value;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Service("jpaDataTypeService")
@Transactional
public class DataTypeServiceImpl
		extends AbstractJpaDetailService<ProgLang, DataType, ProgLangRepository, DataTypeRepository, String>
		implements DataTypeService {
	@Autowired
	private ValuesSetService valuesSetService;

	@Autowired
	public DataTypeServiceImpl(ProgLangRepository masterRepository, DataTypeRepository repository) {
		super(masterRepository, repository, DataType.class);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Value> getSqlTypes() {
		return valuesSetService.getSqlDataTypes();
	};

	/**
	 * {@inheritDoc}
	 */
	private Map<String, List<DataType>> getDataTypes(String archCode) {
		Map<String, List<DataType>> backendTypes = new HashMap<>();
		for (ProgLang backendProgLang : this.masterRepository.findByArch_CodeOrderByArch_ValAsc(archCode)) {
			backendTypes.put(backendProgLang.getLang().getVal(), backendProgLang.getDataTypes());
		}
		return backendTypes;
	};

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, List<DataType>> getBackendTypes() {
		return this.getDataTypes(Value.VC_ARCH_BACK);
	};

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, List<DataType>> getFrontendTypes() {
		return this.getDataTypes(Value.VC_ARCH_FRONT);
	};

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<DataType> getTypesByDataBaseType(Value dbType) {
		return this.repository.findByMaster_DbType(dbType, Sort.by("name"));
	};
	
}
