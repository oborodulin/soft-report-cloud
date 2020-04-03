package com.oborodulin.softreport.domain.service;

import com.oborodulin.softreport.domain.common.service.JpaTreeAbstractService;
import com.oborodulin.softreport.domain.model.dic.server.Server;
import com.oborodulin.softreport.domain.model.dic.valuesset.value.Value;
import com.oborodulin.softreport.domain.model.docobject.DocObject;
import com.oborodulin.softreport.domain.model.docobject.DocObjectRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Service("jpaDocObjectService")
@Transactional
public class DocObjectServiceImpl extends JpaTreeAbstractService<DocObject, DocObjectRepository, String>
		implements DocObjectService {
	@Autowired
	private ValuesSetService valuesSetService;
	@Autowired
	private ValueService valueService;
	@Autowired
	private ServerService serverService;

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
	public List<DocObject> findDataTables() {
		return this.repository.findByType(this.valueService.getDocObjectDataTableType(), Sort.by("name"));
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
	public List<Server> getDbServers() {
		return this.serverService.getDbServers();
	}

	/**
	 * {@inheritDoc}
	 */
	private Integer getNewPos(DocObject docObject) {
		DocObject lastDocObjectPos = null;
		if (docObject.getParent() == null) {
			lastDocObjectPos = this.repository.findFirstByTypeAndParentIsNullOrderByPosDesc(docObject.getType());
		} else {
			lastDocObjectPos = this.repository.findFirstByTypeAndParentIdOrderByPosDesc(docObject.getType(),
					docObject.getParent().getId());
		}
		if (lastDocObjectPos != null) {
			return lastDocObjectPos.getPos() + 1;
		} else {
			return 1;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DocObject createDataBase() {
		DocObject entity = this.create();
		entity.setType(this.valueService.getDocObjectDataBaseType());
		entity.setPos(this.getNewPos(entity));
		return entity;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DocObject createSchema(Long parentId) {
		DocObject entity = this.createChild(parentId);
		entity.setType(this.valueService.getDocObjectSchemaType());
		entity.setPos(this.getNewPos(entity));
		return entity;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DocObject createDataTable(Long parentId) {
		DocObject entity = this.createChild(parentId);
		entity.setType(this.valueService.getDocObjectDataTableType());
		entity.setPos(this.getNewPos(entity));
		return entity;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public Optional<DocObject> save(DocObject entity) {
		DocObject lastDocObjectPos = null;
		if (entity.getParent() == null) {
			lastDocObjectPos = this.repository.findFirstByTypeAndParentIsNullOrderByPosDesc(entity.getType());
		} else {
			lastDocObjectPos = this.repository.findFirstByTypeAndParentIdOrderByPosDesc(entity.getType(),
					entity.getParent().getId());
		}
		if (entity.getPos() == 0 || entity.getPos() > lastDocObjectPos.getPos() + 1) {
			entity.setPos(lastDocObjectPos.getPos() + 1);
		} else if (entity.getPos() >= 1 && entity.getPos() <= lastDocObjectPos.getPos()) {
			List<DocObject> docObjects = this.repository.findByPosGreaterThanEqual(entity.getPos());
			for (DocObject docObj : docObjects) {
				docObj.setPos(docObj.getPos() + 1);
			}
			this.repository.saveAll(docObjects);
		}
		return Optional.of(this.repository.save(entity));
	}
}
