package com.oborodulin.softreport.domain.service;

import com.oborodulin.softreport.domain.common.service.JpaDetailAbstractService;
import com.oborodulin.softreport.domain.model.dic.valuesset.ValuesSet;
import com.oborodulin.softreport.domain.model.dic.valuesset.ValuesSetRepository;
import com.oborodulin.softreport.domain.model.dic.valuesset.value.Value;
import com.oborodulin.softreport.domain.model.dic.valuesset.value.ValueRepository;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Service("jpaValueService")
@Transactional
public class ValueServiceImpl
		extends JpaDetailAbstractService<ValuesSet, Value, ValuesSetRepository, ValueRepository, String>
		implements ValueService {
	@Autowired
	public ValueServiceImpl(ValuesSetRepository masterRepository, ValueRepository repository) {
		super(masterRepository, repository, Value.class);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Value> findByValuesSetCode(String code, Sort sort) {
		return this.repository.findByMaster_Code(code, sort);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Value> findByValuesSetId(Long id, Sort sort) {
		return this.repository.findByMaster_Id(id, sort);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Value getDocObjectDataBaseType() {
		return this.repository.findByCode(Value.VC_DOT_DB);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Value getDocObjectSchemaType() {
		return this.repository.findByCode(Value.VC_DOT_SCHEMA);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Value getDocObjectDataTableType() {
		return this.repository.findByCode(Value.VC_DOT_DT);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Value getDocObjectDataTableColumnType() {
		return this.repository.findByCode(Value.VC_DOT_DTCOLUMN);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Value getDocObjectDataTableColumnValueType() {
		return this.repository.findByCode(Value.VC_DOT_CLNMVAL);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Value getDocObjectDataTableTriggerType() {
		return this.repository.findByCode(Value.VC_DOT_TRIGGER);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Value getDocObjectDataBaseViewType() {
		return this.repository.findByCode(Value.VC_DOT_VIEW);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Value getDocObjectDataBaseViewColumnType() {
		return this.repository.findByCode(Value.VC_DOT_VWCOLUMN);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Value getDocObjectDataBaseFuncType() {
		return this.repository.findByCode(Value.VC_DOT_FUNC);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Value getDocObjectDataBaseProcType() {
		return this.repository.findByCode(Value.VC_DOT_PROC);
	}

	/**
	 * {@inheritDoc}
	 */
	public Value getServerDbType() {
		return this.repository.findByCode(Value.VC_ST_DB);
	}
	
}
