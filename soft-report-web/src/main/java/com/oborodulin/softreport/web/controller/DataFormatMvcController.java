package com.oborodulin.softreport.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oborodulin.softreport.domain.model.dic.proglang.datatype.DataType;
import com.oborodulin.softreport.domain.model.dic.proglang.datatype.dataformat.DataFormat;
import com.oborodulin.softreport.domain.service.DataFormatServiceImpl;
import com.oborodulin.softreport.domain.service.DataTypeServiceImpl;
import com.oborodulin.softreport.web.AbstractMasterDetailMvcController;

@Controller
@RequestMapping(DataFormatMvcController.BASE_URL)
public class DataFormatMvcController
		extends AbstractMasterDetailMvcController<DataType, DataFormat, DataTypeServiceImpl, DataFormatServiceImpl, String> {

	/** Базовый URL контроллера */
	protected static final String BASE_URL = "/dataformats";
	/** Наименование объекта контроллера (Controller Objects Name) */
	private static final String CO_NAME = "dataFormat";
	/**
	 * Наименование коллекции объектов контроллера (Controller Objects Collection
	 * Name)
	 */
	private static final String COC_NAME = "dataFormats";
	/** Путь к шаблонам (каталог) */
	private static final String VN_PATH = DataTypeMvcController.VN_PATH.concat(COC_NAME.toLowerCase()).concat("/");

	@Autowired
	public DataFormatMvcController(DataTypeServiceImpl masterService, DataFormatServiceImpl service) {
		super(masterService, service, BASE_URL, VN_PATH, CO_NAME, COC_NAME);
		this.setDtlSortPropName("format");
	}

}
