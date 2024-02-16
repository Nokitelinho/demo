package com.ibsplc.neoicargo.stock.mapper;

import com.ibsplc.neoicargo.common.converters.CentralConfig;
import com.ibsplc.neoicargo.stock.mapper.common.ModelAbstractMapper;
import com.ibsplc.neoicargo.stock.model.DocumentFilter;
import com.ibsplc.neoicargo.stock.model.viewfilter.DocumentFilterModel;
import com.ibsplc.neoicargo.stock.vo.filter.DocumentFilterVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = CentralConfig.class)
public interface DocumentFilterMapper
    extends ModelAbstractMapper<DocumentFilter, DocumentFilterVO> {
  @Mapping(source = "shipmentPrefix", target = "prefix")
  DocumentFilterVO mapModelToVo(DocumentFilterModel documentFilterModel);
}
