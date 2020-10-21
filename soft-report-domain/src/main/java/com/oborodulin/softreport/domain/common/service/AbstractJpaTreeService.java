package com.oborodulin.softreport.domain.common.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;

import com.oborodulin.softreport.domain.common.entity.TreeEntity;
import com.oborodulin.softreport.domain.common.repository.CommonTreeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

public abstract class AbstractJpaTreeService<E extends TreeEntity<E, U>, R extends CommonTreeRepository<E, U>, U>
		extends AbstractJpaService<E, R, U> implements CommonJpaTreeService<E, U> {

	@Autowired
	public AbstractJpaTreeService(R repository, Class<E> clazz) {
		super(repository, clazz);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(readOnly = true)
	public List<E> findByParentIsNull() {
		return this.repository.findByParentIsNull();
	};

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(readOnly = true)
	public List<E> findByParentId(Long parentId, Sort sort) {
		return this.repository.findByParentId(parentId, sort);
	};

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(readOnly = true)
	public List<E> findByIdIsNot(Long id) {
		return this.repository.findByIdIsNot(id);
	};

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public E createdEntity() {
		E entity = null;
		try {
			entity = this.clazz.getDeclaredConstructor().newInstance();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return entity;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public E createdEntity(Long parentId) {
		E entity = null;
		try {
			entity = this.createdEntity();
			entity.setParent(this.repository.findById(parentId)
					.orElseThrow(() -> new IllegalArgumentException("Invalid parent Id:" + parentId)));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return entity;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public void save(Long parentId, E entity) {
		entity.setParent(this.repository.findById(parentId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid parent Id:" + parentId)));
		this.save(entity);
	};

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(readOnly = true)
	public String getParentsPath(E entity) {
		if (entity.getParent() != null) {
			return this.getParentsPath(entity.getParent()).concat(".").concat(entity.codeId());
		}
		return entity.codeId();
	}

}