package com.oborodulin.softreport.domain.service;

import com.oborodulin.softreport.domain.common.service.AbstractJpaDetailService;
import com.oborodulin.softreport.domain.model.dic.proglang.datatype.DataType;
import com.oborodulin.softreport.domain.model.dic.proglang.datatype.DataTypeRepository;
import com.oborodulin.softreport.domain.model.dic.proglang.datatype.dataformat.DataFormat;
import com.oborodulin.softreport.domain.model.dic.proglang.datatype.dataformat.DataFormatRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Service("jpaDataFormatService")
@Transactional
public class DataFormatServiceImpl
		extends AbstractJpaDetailService<DataType, DataFormat, DataTypeRepository, DataFormatRepository, String>
		implements DataFormatService {
	@Autowired
	public DataFormatServiceImpl(DataTypeRepository masterRepository, DataFormatRepository repository) {
		super(masterRepository, repository, DataFormat.class);
	}

}
