package com.oborodulin.softreport.domain.service;

import com.oborodulin.softreport.domain.common.service.JpaAbstractService;
import com.oborodulin.softreport.domain.model.dic.valuesset.ValuesSet;
import com.oborodulin.softreport.domain.model.dic.valuesset.ValuesSetRepository;
import com.oborodulin.softreport.domain.model.dic.valuesset.value.Value;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Service("jpaValuesSetService")
@Transactional
public class ValuesSetServiceImpl extends JpaAbstractService<ValuesSet, ValuesSetRepository, String>
		implements ValuesSetService {

	@Autowired
	public ValuesSetServiceImpl(ValuesSetRepository repository) {
		super(repository);
	}

	@Override
	public List<Value> findValuesBySetCode(String code) {
		Comparator<Value> compareByCode = (Value v1, Value v2) -> v1.getCode().compareTo(v2.getCode());
		List<Value> values = this.repository.findByCode(code).get().getValues();
		Collections.sort(values, compareByCode);
		return values;
	}

	@Override
	public Optional<List<Value>> findValuesBySetId(Long id) {
		Optional<ValuesSet> valuesSet = this.repository.findById(id);

		return valuesSet.isPresent() ? Optional.ofNullable(valuesSet.get().getValues()) : Optional.empty();
	}

	public List<Value> getServersTypes() {
		return this.findValuesBySetCode(ValuesSet.VS_SERVERS_TYPES);
	};

	public List<Value> getEnvTypes() {
		return this.findValuesBySetCode(ValuesSet.VS_ENV_TYPES);
	};

	public List<Value> getSortDirections() {
		return this.findValuesBySetCode(ValuesSet.VS_SORT_DIRECTIONS);
	};

	public List<Value> getCfgBundleTypes() {
		return this.findValuesBySetCode(ValuesSet.VS_CFG_BUNDLE_TYPES);
	};

	public List<Value> getSoftwareTypes() {
		return this.findValuesBySetCode(ValuesSet.VS_SOFTWARE_TYPES);
	};

	public List<Value> getSoftwareArchs() {
		return this.findValuesBySetCode(ValuesSet.VS_SOFTWARE_ARCHS);
	};

	public List<Value> getSoftwareTechs() {
		return this.findValuesBySetCode(ValuesSet.VS_SOFTWARE_TECHS);
	};

	public List<Value> getDocCategs() {
		return this.findValuesBySetCode(ValuesSet.VS_DOC_CATEGS);
	};

	public List<Value> getDocTypes() {
		return this.findValuesBySetCode(ValuesSet.VS_DOC_TYPES);
	};

	public List<Value> getDbTypes() {
		return this.findValuesBySetCode(ValuesSet.VS_DB_TYPES);
	};

	public List<Value> getDtTypes() {
		return this.findValuesBySetCode(ValuesSet.VS_DB_DT_TYPES);
	};

	public List<Value> getDbColumnTypes() {
		return this.findValuesBySetCode(ValuesSet.VS_DB_COLUMN_TYPES);
	};

	public List<Value> getSqlDataTypes() {
		return this.findValuesBySetCode(ValuesSet.VS_DB_SQL_DATA_TYPES);
	};

	public List<Value> getDocObjTypes() {
		return this.findValuesBySetCode(ValuesSet.VS_DOC_OBJ_TYPES);
	};

	public List<Value> getDocObjEvents() {
		return this.findValuesBySetCode(ValuesSet.VS_DOC_OBJ_EVENTS);
	};

	public List<Value> getDocObjEventActions() {
		return this.findValuesBySetCode(ValuesSet.VS_DOC_OBJ_EVENT_ACTIONS);
	};

	public List<Value> getRuleTypes() {
		return this.findValuesBySetCode(ValuesSet.VS_RULE_TYPES);
	};

	public List<Value> getRuleCompareTypes() {
		return this.findValuesBySetCode(ValuesSet.VS_RULE_COMPARE_TYPES);
	};

	public List<Value> getRuleOperators() {
		return this.findValuesBySetCode(ValuesSet.VS_RULE_OPERATORS);
	};

}
