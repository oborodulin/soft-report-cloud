package com.oborodulin.softreport.domain.service;

import com.oborodulin.softreport.domain.common.service.CommonJpaDetailService;
import com.oborodulin.softreport.domain.model.dic.proglang.datatype.DataType;
import com.oborodulin.softreport.domain.model.dic.proglang.datatype.dataformat.DataFormat;

public interface DataFormatService extends CommonJpaDetailService<DataType, DataFormat, String> {


}
