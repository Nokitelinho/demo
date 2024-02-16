package com.ibsplc.neoicargo.stock.mapper;

import com.ibsplc.neoicargo.common.converters.CentralConfig;
import com.ibsplc.neoicargo.stock.mapper.common.BaseMapper;
import com.ibsplc.neoicargo.stock.mapper.common.ModelAbstractMapper;
import com.ibsplc.neoicargo.stock.model.viewfilter.StockHolderLovFilterModel;
import com.ibsplc.neoicargo.stock.vo.filter.StockHolderLovFilterVO;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(
    config = CentralConfig.class,
    builder = @Builder(disableBuilder = true),
    uses = {BaseMapper.class})
public interface StockHolderLovFilterMapper
    extends ModelAbstractMapper<StockHolderLovFilterModel, StockHolderLovFilterVO> {}
