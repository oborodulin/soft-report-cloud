package com.oborodulin.softreport.domain.service;

import com.oborodulin.softreport.domain.common.service.AbstractJpaTreeService;
import com.oborodulin.softreport.domain.model.dic.proglang.datatype.DataType;
import com.oborodulin.softreport.domain.model.dic.server.Server;
import com.oborodulin.softreport.domain.model.dic.valuesset.value.Value;
import com.oborodulin.softreport.domain.model.docobject.DocObject;
import com.oborodulin.softreport.domain.model.docobject.DocObjectRepository;
import com.oborodulin.softreport.domain.model.software.Software;
import com.oborodulin.softreport.domain.model.software.businessobject.BusinessObject;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service("jpaDocObjectService")
@Transactional
public class DocObjectServiceImpl extends AbstractJpaTreeService<DocObject, DocObjectRepository, String>
		implements DocObjectService {
	@Autowired
	private ValuesSetService valuesSetService;
	@Autowired
	private ValueService valueService;
	@Autowired
	private ServerService serverService;
	@Autowired
	private SoftwareService softwareService;
	@Autowired
	private BusinessObjectService businessObjectService;
	@Autowired
	private DataTypeService dataTypeService;

	@Autowired
	public DocObjectServiceImpl(DocObjectRepository repository) {
		super(repository, DocObject.class);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<DocObject> findDataBases() {
		return this.repository.findByType(this.valueService.getDocObjectDataBaseType(), Sort.by("name"));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<DocObject> findSchemasByDataBaseId(Long dataBaseId) {
		return this.repository.findByParentIdAndType(dataBaseId, this.valueService.getDocObjectSchemaType(),
				Sort.by("name"));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<DocObject> findDataTables() {
		return this.repository.findByType(this.valueService.getDocObjectDataTableType(), Sort.by("name"));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<DocObject> findDtColumnsByDataTableId(Long dataTableId) {
		return this.repository.findByParentIdAndType(dataTableId, this.valueService.getDocObjectDataTableColumnType(),
				Sort.by("pos"));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Value> getDbTypes() {
		return this.valuesSetService.getDbTypes();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Server> getDbServers() {
		return this.serverService.getDbServers();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<DocObject> getSchemas() {
		return this.repository.findByType(this.valueService.getDocObjectSchemaType(), Sort.by("name"));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Value> getDtTypes() {
		return this.valuesSetService.getDtTypes();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Software> getSoftwares() {
		return this.softwareService.entities();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<BusinessObject> getBusinessObjects() {
		return this.businessObjectService.entities();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Value> getDtColumnTypes() {
		return this.valuesSetService.getDtColumnTypes();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DocObject getDataObjectDb(DocObject docObject) {
		switch (docObject.getType().getCode()) {
		case Value.VC_DOT_DB:
			return docObject;
		case Value.VC_DOT_SCHEMA:
		case Value.VC_DOT_DT:
		case Value.VC_DOT_VIEW:
		case Value.VC_DOT_PROC:
		case Value.VC_DOT_FUNC:
		case Value.VC_DOT_TRIGGER:
		case Value.VC_DOT_DTCOLUMN:
		case Value.VC_DOT_VWCOLUMN:
		case Value.VC_DOT_CLNMVAL:
			if (docObject.getParent() != null) {
				return this.getDataObjectDb(docObject.getParent());
			}
			break;
		default:
			return null;
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<DataType> getDbDataTypes(Long dataTableId) {
		Optional<DocObject> dataTable = this.repository.findById(dataTableId);
		// определяем БД по ИД таблицы
		DocObject dataBase = this.getDataObjectDb(dataTable.get());
		log.info("Data Base Type = " + dataBase.getDbType().getCode() + "; id = " + dataBase.getDbType().getId());
		return this.dataTypeService.getTypesByDataBaseType(dataBase.getDbType());
	}

	private List<DocObject> getPrimaryKeysFromDbDataTables(List<DocObject> children, Value dtColumnType) {
		List<DocObject> primaryKeys = new ArrayList<>();
		for (DocObject docObject : children) {
			if (docObject.getType().equals(dtColumnType)) {
				if (docObject.getIsPrimaryKey()) {
					primaryKeys.add(docObject);
					break;
				}
			} else {
				primaryKeys.addAll(this.getPrimaryKeysFromDbDataTables(docObject.getChildren(), dtColumnType));
			}
		}
		return primaryKeys;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<DocObject> getDbDataTablesPrimaryKeys(Long dataTableId) {
		List<DocObject> primaryKeys = new ArrayList<>();
		Optional<DocObject> dataTable = this.repository.findById(dataTableId);
		// определяем БД по ИД таблицы
		DocObject dataBase = this.getDataObjectDb(dataTable.get());
		// получаем все первичные ключи БД
		Value dtColumnType = this.valueService.getDocObjectDataTableColumnType();
		primaryKeys.addAll(this.getPrimaryKeysFromDbDataTables(dataBase.getChildren(), dtColumnType));
		return primaryKeys;
	}

	private DocObject getDataObjectUiForm(DocObject docObject) {
		switch (docObject.getType().getCode()) {
		case Value.VC_DOT_DB:
			return docObject;
		case Value.VC_DOT_SCHEMA:
		case Value.VC_DOT_DT:
		case Value.VC_DOT_VIEW:
		case Value.VC_DOT_PROC:
		case Value.VC_DOT_FUNC:
		case Value.VC_DOT_TRIGGER:
		case Value.VC_DOT_DTCOLUMN:
		case Value.VC_DOT_VWCOLUMN:
		case Value.VC_DOT_CLNMVAL:
			if (docObject.getParent() != null) {
				return this.getDataObjectDb(docObject.getParent());
			}
			break;
		default:
			return null;
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<DocObject> getDataObjectUiForms(DocObject docObject) {
		Set<DocObject> uiForms = new HashSet<>();
		switch (docObject.getType().getCode()) {
		case Value.VC_DOT_DT:
			for (DocObject dtColumn : docObject.getChildren()) {
				for (DocObject uiObject : this.repository.findByDbObject(dtColumn)) {
					uiForms.add(this.getDataObjectUiForm(uiObject));
				}
			}
			break;
		default:
			return null;
		}
		return uiForms;
	}

	/**
	 * Устанавливает позицию по умолчанию у объекта документа.
	 * 
	 * @param docObject объект документа
	 */
	private void setDefaultDocObjectPos(DocObject docObject) {
		DocObject lastDocObjectPos = null;
		if (docObject.getParent() == null) {
			lastDocObjectPos = this.repository.findFirstByTypeAndParentIsNullOrderByPosDesc(docObject.getType());
		} else {
			lastDocObjectPos = this.repository.findFirstByTypeAndParentIdOrderByPosDesc(docObject.getType(),
					docObject.getParent().getId());
		}
		if (lastDocObjectPos != null) {
			docObject.setPos(lastDocObjectPos.getPos() + 1);
		} else {
			docObject.setPos(1);
		}
	}

	/**
	 * Создаёт и возвращает объект документа заданного типа.
	 * 
	 * @param type тип объекта
	 * @return объект документа
	 */
	private DocObject createDocObject(Value type) {
		DocObject entity = this.createdEntity();
		entity.setType(type);
		this.setDefaultDocObjectPos(entity);
		return entity;
	}

	/**
	 * Создаёт и возвращает дочерний объект документа заданного типа.
	 * 
	 * @param type     тип объекта
	 * @param parentId идентификатор родительского объекта
	 * @return дочерний объект документа
	 */
	private DocObject createDocObject(Value type, Long parentId) {
		if (parentId == null) {
			return this.createDocObject(type);
		}
		DocObject entity = this.createdEntity(parentId);
		entity.setType(type);
		this.setDefaultDocObjectPos(entity);
		return entity;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DocObject createDataBase() {
		return this.createDocObject(this.valueService.getDocObjectDataBaseType());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DocObject createSchema(Long parentId) {
		return this.createDocObject(this.valueService.getDocObjectSchemaType(), parentId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DocObject createDataTable(Long parentId) {
		return this.createDocObject(this.valueService.getDocObjectDataTableType(), parentId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DocObject createDtColumn(Long parentId) {
		return this.createDocObject(this.valueService.getDocObjectDataTableColumnType(), parentId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public void save(DocObject entity) {
		DocObject lastDocObjectPos = null;
		if (entity.getParent() == null) {
			lastDocObjectPos = this.repository.findFirstByTypeAndParentIsNullOrderByPosDesc(entity.getType());
		} else {
			lastDocObjectPos = this.repository.findFirstByTypeAndParentIdOrderByPosDesc(entity.getType(),
					entity.getParent().getId());
		}
		if (entity.getPos() <= 0 || (lastDocObjectPos != null && entity.getPos() > lastDocObjectPos.getPos() + 1)) {
			entity.setPos((lastDocObjectPos == null ? 0 : lastDocObjectPos.getPos()) + 1);
		} else if (entity.getPos() >= 1 && lastDocObjectPos != null && entity.getPos() <= lastDocObjectPos.getPos()) {
			log.info("Current Entity Pos = " + entity.getPos() + "; lastDocObjectPos = " + lastDocObjectPos.getPos());
			List<DocObject> docObjects = null;
			if (entity.getParent() == null) {
				docObjects = this.repository.findByPosGreaterThanEqualOrderByPosAsc(entity.getPos());
			} else {
				docObjects = this.repository
						.findByParentIdAndPosGreaterThanEqualOrderByPosAsc(entity.getParent().getId(), entity.getPos());
			}
			for (DocObject docObj : docObjects) {
				docObj.setPos(docObj.getPos() + 1);
			}
			this.repository.saveAll(docObjects);
		}
		Optional.of(this.repository.save(entity));
	}
}
