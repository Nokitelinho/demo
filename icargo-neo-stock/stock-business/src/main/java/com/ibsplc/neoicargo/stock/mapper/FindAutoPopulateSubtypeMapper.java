package com.ibsplc.neoicargo.stock.mapper;

import com.ibsplc.icargo.framework.util.time.LocalDateMapper;
import com.ibsplc.neoicargo.stock.mapper.common.ModelAbstractMapper;
import com.ibsplc.neoicargo.stock.model.viewfilter.DocumentFilterModel;
import com.ibsplc.neoicargo.stock.vo.filter.DocumentFilterVO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
    unmappedSourcePolicy = ReportingPolicy.IGNORE,
    componentModel = "spring",
    nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
    uses = {LocalDateMapper.class})
public interface FindAutoPopulateSubtypeMapper
    extends ModelAbstractMapper<DocumentFilterModel, DocumentFilterVO> {}
