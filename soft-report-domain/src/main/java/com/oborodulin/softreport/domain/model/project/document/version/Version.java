package com.oborodulin.softreport.domain.model.project.document.version;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import com.oborodulin.softreport.domain.common.entity.DetailEntity;
import com.oborodulin.softreport.domain.model.docobject.DocObject;
import com.oborodulin.softreport.domain.model.project.document.Document;

import lombok.Data;

/**
 * Класс обеспечивает хранение версий документов.
 * 
 * Используется семантическое версионирование 2.0.0
 * (https://semver.org/lang/ru/)
 * 
 * @author Oleg Borodulin
 */
@Data
@Entity
@Table(name = Version.TABLE_NAME)
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class Version extends DetailEntity<Document, String> {
	private static final long serialVersionUID = 81758010780885142L;

	/** Наименование таблицы данных доменного объекта (сущности) */
	protected static final String TABLE_NAME = "VERSIONS";

	/** Мажорная версия */
	@NotEmpty
	private Long major = 0L;

	/** Минорная версия */
	@NotEmpty
	private Long minor = 1L;

	/** Патч */
	@NotEmpty
	private Long patch = 0L;

	/** Объекты БД/UI */
	@ManyToMany(mappedBy = "versions", fetch = FetchType.LAZY)
	private List<DocObject> docObjects = new ArrayList<>();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCodeId() {
		return this.getSemVer();
	}

	/**
	 * Возвращает семантический номер версии в формате (x.y.z)
	 * 
	 * @return номер версии в формате (x.y.z)
	 */
	public String getSemVer() {
		String strMinor = null;
		String strPatch = null;
		if (getPatch() != 0L) {
			strPatch = ".".concat(getPatch().toString());
			if (getMinor() != 0L) {
				strMinor = ".".concat(getMinor().toString());
			}
		}
		return this.major.toString().concat(strMinor).concat(strPatch);

	}

	/**
	 * Увеличивает номер мажорной версии
	 */
	public void incMajor() {
		setMajor(getMajor() + 1);
		setMinor(0L);
		setPatch(0L);
	}

	/**
	 * Увеличивает номер минорной версии
	 */
	public void incMinor() {
		setMinor(getMinor() + 1);
		setPatch(0L);
	}

	/**
	 * Увеличивает номер патча
	 */
	public void incPatch() {
		setPatch(getPatch() + 1);
	}

}
