package com.ibsplc.neoicargo.stock.mapper;

import com.ibsplc.neoicargo.common.converters.CentralConfig;
import com.ibsplc.neoicargo.stock.mapper.common.BaseMapper;
import com.ibsplc.neoicargo.stock.mapper.common.ModelAbstractMapper;
import com.ibsplc.neoicargo.stock.model.StockAllocationModel;
import com.ibsplc.neoicargo.stock.vo.StockAllocationVO;
import com.ibsplc.neoicargo.stock.vo.StockRangeHistoryVO;
import com.ibsplc.neoicargo.stock.vo.filter.StockAllocationFilterVO;
import org.mapstruct.Builder;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    config = CentralConfig.class,
    builder = @Builder(disableBuilder = true),
    uses = {RangeMapper.class, BaseMapper.class, StockRequestOALMapper.class})
public interface StockAllocationMapper
    extends ModelAbstractMapper<StockAllocationModel, StockAllocationVO> {

  @Mapping(target = "autoAllocated", source = "autoAllocated", qualifiedByName = "convertToFlag")
  @Mapping(target = "rangeType", source = "manual", qualifiedByName = "convertToRangeType")
  StockRangeHistoryVO mapAllocationVoToRangeHistoryVo(StockAllocationVO stockAllocationVO);

  @Mapping(target = "startRange", expression = "java(startRange)")
  StockAllocationFilterVO constructStockAllocationFilterVO(
      StockAllocationVO stockAllocationVO, @Context long startRange);
}
