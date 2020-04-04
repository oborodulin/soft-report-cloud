package com.oborodulin.softreport.domain.common.service;

import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.oborodulin.softreport.domain.common.entity.AuditableEntity;
import com.oborodulin.softreport.domain.common.entity.DetailTreeEntity;
import com.oborodulin.softreport.domain.common.repository.CommonDetailTreeRepository;
import com.oborodulin.softreport.domain.common.repository.CommonRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

public abstract class AbstractJpaDetailTreeService<E extends AuditableEntity<U>, T extends DetailTreeEntity<E, T, U>, M extends CommonRepository<E, U>, R extends CommonDetailTreeRepository<E, T, U>, U>
		extends AbstractJpaTreeService<T, R, U>
		implements CommonJpaDetailTreeService<E, T, U>, CommonJpaDetailService<E, T, U>, CommonJpaTreeService<T, U> {
	protected final M masterRepository;

	@Autowired
	public AbstractJpaDetailTreeService(M masterRepository, R repository, Class<T> clazz) {
		super(repository, clazz);
		this.masterRepository = masterRepository;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(readOnly = true)
	public List<T> findByMaster(E master, Sort sort) {
		return this.repository.findByMaster(master, sort);
	};

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(readOnly = true)
	public List<T> findByMasterId(Long id, Sort sort) {
		return this.repository.findByMaster_Id(id, sort);
	};

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(readOnly = true)
	public List<T> findByMasterId(Long masterId) {
		return this.repository.findByMaster_IdAndParentIsNull(masterId);
	};

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public T create(Long parentId) {
		T entity = null;
		try {
			T parent = this.repository.findById(parentId)
					.orElseThrow(() -> new IllegalArgumentException("Invalid parent Id:" + parentId));
			entity = this.create();
			entity.setParent(parent);
			entity.setMaster(parent.getMaster());
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
	public Optional<T> save(Long parentId, T entity) {
		T parent = this.repository.findById(parentId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid parent Id:" + parentId));
		entity.setParent(parent);
		entity.setMaster(parent.getMaster());
		return this.save(entity);
	}

}