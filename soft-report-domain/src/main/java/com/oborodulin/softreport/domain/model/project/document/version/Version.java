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

	/** Номер ревизии */
	private Long revisionNumber;	
	
	/** Объекты БД/UI */
	@ManyToMany(mappedBy = "versions", fetch = FetchType.LAZY)
	private List<DocObject> docObjects = new ArrayList<>();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String codeId() {
		return this.getSemVersionString();
	}

	/**
	 * Возвращает семантический номер версии в формате (x.y.z)
	 * 
	 * @return номер версии в формате (x.y.z)
	 */
	public String getSemVersionString() {
		String strMinor = null;
		String strPatch = null;
		if (this.patch != 0L) {
			strPatch = ".".concat(this.patch.toString());
			if (this.minor != 0L) {
				strMinor = ".".concat(this.minor.toString());
			}
		}
		return this.major.toString().concat(strMinor).concat(strPatch);
	}

	/**
	 * Возвращает номер версии в числовом формате
	 * 
	 * @return номер версии в числовом формате
	 */
	public Long getVersionNumber() {
		return this.major * 100000000L + this.minor * 100000L + this.patch * 100L;
	}

	/**
	 * Увеличивает номер мажорной версии
	 */
	public void incMajor() {
		this.major++;
		this.minor = 0L;
		this.patch = 0L;
	}

	/**
	 * Увеличивает номер минорной версии
	 */
	public void incMinor() {
		this.minor++;
		this.patch = 0L;
	}

	/**
	 * Увеличивает номер патча
	 */
	public void incPatch() {
		this.patch++;
	}

}
