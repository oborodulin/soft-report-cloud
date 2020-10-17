package com.oborodulin.softreport.domain.service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import com.oborodulin.softreport.domain.common.service.CommonJpaDetailTreeService;
import com.oborodulin.softreport.domain.docmodel.CommonDocModelObject;
import com.oborodulin.softreport.domain.docmodel.DocumentModel;
import com.oborodulin.softreport.domain.model.dic.doctype.DocType;
import com.oborodulin.softreport.domain.model.project.Project;
import com.oborodulin.softreport.domain.model.project.document.Document;

/**
 * Интерфейс документа.
 * <p>
 * Предоставляет функционал по работе с документами проекта: создание, просмотр,
 * выгрузка
 * 
 * @author Oleg Borodulin
 */
public interface DocumentService extends CommonJpaDetailTreeService<Project, Document, String> {

	/**
	 * Возвращает ассоциативный список типов документов.
	 *
	 * @return ассоциативный список типов документов
	 */
	public Map<String, List<DocType>> getTypes();

	/**
	 * Возвращает наименование файла шаблона по типу заданного документа.
	 *
	 * @param document документ
	 * @return наименование файла шаблона
	 */
	public String getView(Document document);

	/**
	 * Возвращает модель заданного документа.
	 *
	 * @param document документ
	 * @return модель документа
	 */
	public DocumentModel getDocModel(Document document);

	/**
	 * Возвращает компаратор сортировки объектов модели документа.
	 * <p>
	 * Используется при выводе объектов документов в порядке указанной позиции.
	 * 
	 * @return компаратор сортировки
	 */
	public Comparator<CommonDocModelObject> getDocObjectComparator();

}
