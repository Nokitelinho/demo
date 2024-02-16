package com.ibsplc.neoicargo.stock.mapper;

import com.ibsplc.icargo.framework.util.currency.helper.EBLMoneyMapper;
import com.ibsplc.icargo.framework.util.time.LocalDateMapper;
import com.ibsplc.icargo.framework.util.unit.MeasureMapper;
import com.ibsplc.neoicargo.stock.mapper.common.ModelAbstractMapper;
import com.ibsplc.neoicargo.stock.model.StockDetailsModel;
import com.ibsplc.neoicargo.stock.model.viewfilter.StockDetailsFilterModel;
import com.ibsplc.neoicargo.stock.vo.StockDetailsVO;
import com.ibsplc.neoicargo.stock.vo.filter.StockDetailsFilterVO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
    unmappedSourcePolicy = ReportingPolicy.IGNORE,
    componentModel = "spring",
    nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
    uses = {MeasureMapper.class, LocalDateMapper.class, EBLMoneyMapper.class})
public interface CustomerStockDetailsMapper
    extends ModelAbstractMapper<StockDetailsFilterModel, StockDetailsFilterVO> {

  StockDetailsModel mapVoToModel(StockDetailsVO stockDetailsVO);
}
