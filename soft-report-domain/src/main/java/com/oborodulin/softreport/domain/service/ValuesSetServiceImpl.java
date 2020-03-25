package com.oborodulin.softreport.domain.service;

import com.oborodulin.softreport.domain.common.service.JpaAbstractService;
import com.oborodulin.softreport.domain.model.dic.valuesset.ValuesSet;
import com.oborodulin.softreport.domain.model.dic.valuesset.ValuesSetRepository;
import com.oborodulin.softreport.domain.model.dic.valuesset.value.Value;

import java.util.ArrayList;
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
		Optional<ValuesSet> valuesSet = this.repository.findByCode(code);
		List<Value> values = new ArrayList<>();
		if (valuesSet.isPresent()) {
			Comparator<Value> compareByCode = (Value v1, Value v2) -> v1.getCode().compareTo(v2.getCode());
			values = this.repository.findByCode(code).get().getValues();
			Collections.sort(values, compareByCode);
		}
		return values;
	}

	@Override
	public Optional<List<Value>> findValuesBySetId(Long id) {
		Optional<ValuesSet> valuesSet = this.repository.findById(id);

		return valuesSet.isPresent() ? Optional.ofNullable(valuesSet.get().getValues()) : Optional.empty();
	}

	@Override
	public List<Value> getServersTypes() {
		return this.findValuesBySetCode(ValuesSet.VS_SERVERS_TYPES);
	};

	@Override
	public List<Value> getEnvTypes() {
		return this.findValuesBySetCode(ValuesSet.VS_ENV_TYPES);
	};

	@Override
	public List<Value> getSortDirections() {
		return this.findValuesBySetCode(ValuesSet.VS_SORT_DIRECTIONS);
	};

	@Override
	public List<Value> getCfgBundleTypes() {
		return this.findValuesBySetCode(ValuesSet.VS_CFG_BUNDLE_TYPES);
	};

	@Override
	public List<Value> getSoftwareTypes() {
		return this.findValuesBySetCode(ValuesSet.VS_SOFTWARE_TYPES);
	};

	@Override
	public List<Value> getSoftwareArchs() {
		return this.findValuesBySetCode(ValuesSet.VS_SOFTWARE_ARCHS);
	};

	@Override
	public List<Value> getSoftwareTechs() {
		return this.findValuesBySetCode(ValuesSet.VS_SOFTWARE_TECHS);
	};

	public List<Value> getProgramLangs() {
		List<Value> progLangs = new ArrayList<>();
		for (Value val : this.getSoftwareTechs()) {
			if (val.getAttr1().equals(Value.AV_LANG)) {
				progLangs.add(val);
			}
		}
		return progLangs;
	};

	@Override
	public List<Value> getDocCategs() {
		return this.findValuesBySetCode(ValuesSet.VS_DOC_CATEGS);
	};

	@Override
	public List<Value> getDocTypes() {
		return this.findValuesBySetCode(ValuesSet.VS_DOC_TYPES);
	};

	@Override
	public List<Value> getDbTypes() {
		return this.findValuesBySetCode(ValuesSet.VS_DB_TYPES);
	};

	@Override
	public List<Value> getDtTypes() {
		return this.findValuesBySetCode(ValuesSet.VS_DB_DT_TYPES);
	};

	@Override
	public List<Value> getDbColumnTypes() {
		return this.findValuesBySetCode(ValuesSet.VS_DB_COLUMN_TYPES);
	};

	@Override
	public List<Value> getSqlDataTypes() {
		return this.findValuesBySetCode(ValuesSet.VS_DB_SQL_DATA_TYPES);
	};

	@Override
	public List<Value> getDocObjTypes() {
		return this.findValuesBySetCode(ValuesSet.VS_DOC_OBJ_TYPES);
	};

	@Override
	public List<Value> getDocObjСontainerTypes() {
		List<Value> containerTypes = new ArrayList<>();
		for (Value val : this.findValuesBySetCode(ValuesSet.VS_DOC_OBJ_TYPES)) {
			if (val.getAttr2().equals(Value.AV_YES)) {
				containerTypes.add(val);
			}
		}
		return containerTypes;
	};

	@Override
	public List<Value> getParentObjHierarchyСontainerTypes(String archCode) {
		List<Value> parentTypes = new ArrayList<>();
		for (Value val : this.findValuesBySetCode(ValuesSet.VS_DOC_OBJ_TYPES)) {
			if (val.getAttr1().equals(archCode) && val.getAttr2().equals(Value.AV_YES)) {
				parentTypes.add(val);
			}
		}
		return parentTypes;
	};

	@Override
	public List<Value> getDocObjComponentTypes() {
		List<Value> componentTypes = new ArrayList<>();
		for (Value val : this.findValuesBySetCode(ValuesSet.VS_DOC_OBJ_TYPES)) {
			if (val.getAttr3().equals(Value.AV_YES)) {
				componentTypes.add(val);
			}
		}
		return componentTypes;
	};

	@Override
	public List<Value> getDocObjEvents() {
		return this.findValuesBySetCode(ValuesSet.VS_DOC_OBJ_EVENTS);
	};

	@Override
	public List<Value> getDocObjEventActions() {
		return this.findValuesBySetCode(ValuesSet.VS_DOC_OBJ_EVENT_ACTIONS);
	};

	@Override
	public List<Value> getUiControlTypes() {
		return this.findValuesBySetCode(ValuesSet.VS_UI_CONTROL_TYPES);
	};

	@Override
	public List<Value> getRuleTypes() {
		return this.findValuesBySetCode(ValuesSet.VS_RULE_TYPES);
	};

	@Override
	public List<Value> getRuleCompareTypes() {
		return this.findValuesBySetCode(ValuesSet.VS_RULE_COMPARE_TYPES);
	};

	@Override
	public List<Value> getRuleOperators() {
		return this.findValuesBySetCode(ValuesSet.VS_RULE_OPERATORS);
	};

}
