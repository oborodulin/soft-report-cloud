package com.oborodulin.softreport.domain.service;

import com.oborodulin.softreport.domain.common.service.AbstractJpaService;
import com.oborodulin.softreport.domain.model.dic.valuesset.ValuesSet;
import com.oborodulin.softreport.domain.model.dic.valuesset.ValuesSetRepository;
import com.oborodulin.softreport.domain.model.dic.valuesset.value.AttrValueYes;
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
public class ValuesSetServiceImpl extends AbstractJpaService<ValuesSet, ValuesSetRepository, String>
		implements ValuesSetService {

	@Autowired
	public ValuesSetServiceImpl(ValuesSetRepository repository) {
		super(repository, ValuesSet.class);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Value> findValuesBySetCode(String code) {
		Optional<ValuesSet> valuesSet = this.repository.findByCode(code);
		List<Value> values = new ArrayList<>();
		if (valuesSet.isPresent()) {
			Comparator<Value> compareByCode = (Value v1, Value v2) -> v1.getCodeId().compareTo(v2.getCodeId());
			values = this.repository.findByCode(code).get().getValues();
			Collections.sort(values, compareByCode);
		}
		return values;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Optional<List<Value>> findValuesBySetId(Long id) {
		Optional<ValuesSet> valuesSet = this.repository.findById(id);

		return valuesSet.isPresent() ? Optional.ofNullable(valuesSet.get().getValues()) : Optional.empty();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Value> getServersTypes() {
		return this.findValuesBySetCode(ValuesSet.VS_SERVERS_TYPES);
	};

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Value> getEnvTypes() {
		return this.findValuesBySetCode(ValuesSet.VS_ENV_TYPES);
	};

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Value> getSortDirections() {
		return this.findValuesBySetCode(ValuesSet.VS_SORT_DIRECTIONS);
	};

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Value> getCfgBundleTypes() {
		return this.findValuesBySetCode(ValuesSet.VS_CFG_BUNDLE_TYPES);
	};

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Value> getSoftwareTypes() {
		return this.findValuesBySetCode(ValuesSet.VS_SOFTWARE_TYPES);
	};

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Value> getSoftwareArchs() {
		return this.findValuesBySetCode(ValuesSet.VS_SOFTWARE_ARCHS);
	};

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Value> getSoftwareTechs() {
		return this.findValuesBySetCode(ValuesSet.VS_SOFTWARE_TECHS);
	};

	/**
	 * {@inheritDoc}
	 */
	public List<Value> getProgramLangs() {
		List<Value> progLangs = new ArrayList<>();
		for (Value val : this.getSoftwareTechs()) {
			if (val.getAttr1().equals(Value.AV_LANG)) {
				progLangs.add(val);
			}
		}
		return progLangs;
	};

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Value> getDocCategs() {
		return this.findValuesBySetCode(ValuesSet.VS_DOC_CATEGS);
	};

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Value> getDocTypes() {
		return this.findValuesBySetCode(ValuesSet.VS_DOC_TYPES);
	};

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Value> getDbTypes() {
		return this.findValuesBySetCode(ValuesSet.VS_DB_TYPES);
	};

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Value> getDtTypes() {
		return this.findValuesBySetCode(ValuesSet.VS_DB_DT_TYPES);
	};

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Value> getDtColumnTypes() {
		return this.findValuesBySetCode(ValuesSet.VS_DT_COLUMN_TYPES);
	};

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Value> getSqlDataTypes() {
		return this.findValuesBySetCode(ValuesSet.VS_DB_SQL_DATA_TYPES);
	};

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Value> getDocObjTypes() {
		return this.findValuesBySetCode(ValuesSet.VS_DOC_OBJ_TYPES);
	};

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Value> getDocObjСontainerTypes() {
		List<Value> containerTypes = new ArrayList<>();
		for (Value val : this.findValuesBySetCode(ValuesSet.VS_DOC_OBJ_TYPES)) {
			if (new AttrValueYes().equals(val.getAttr2())) {
				containerTypes.add(val);
			}
		}
		return containerTypes;
	};

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Value> getParentObjHierarchyСontainerTypes(String archCode) {
		List<Value> parentTypes = new ArrayList<>();
		for (Value val : this.findValuesBySetCode(ValuesSet.VS_DOC_OBJ_TYPES)) {
			if (val.getAttr1().equals(archCode) && new AttrValueYes().equals(val.getAttr2())) {
				parentTypes.add(val);
			}
		}
		return parentTypes;
	};

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Value> getDocObjComponentTypes() {
		List<Value> componentTypes = new ArrayList<>();
		for (Value val : this.findValuesBySetCode(ValuesSet.VS_DOC_OBJ_TYPES)) {
			if (new AttrValueYes().equals(val.getAttr3())) {
				componentTypes.add(val);
			}
		}
		return componentTypes;
	};

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Value> getDocObjEvents() {
		return this.findValuesBySetCode(ValuesSet.VS_DOC_OBJ_EVENTS);
	};

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Value> getDocObjEventActions() {
		return this.findValuesBySetCode(ValuesSet.VS_DOC_OBJ_EVENT_ACTIONS);
	};

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Value> getUiControlTypes() {
		return this.findValuesBySetCode(ValuesSet.VS_UI_CONTROL_TYPES);
	};

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Value> getRuleTypes() {
		return this.findValuesBySetCode(ValuesSet.VS_RULE_TYPES);
	};

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Value> getRuleCompareTypes() {
		return this.findValuesBySetCode(ValuesSet.VS_RULE_COMPARE_TYPES);
	};

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Value> getRuleOperators() {
		return this.findValuesBySetCode(ValuesSet.VS_RULE_OPERATORS);
	};

}
