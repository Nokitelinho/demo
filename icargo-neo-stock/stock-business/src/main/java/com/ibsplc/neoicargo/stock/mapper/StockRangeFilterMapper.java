package com.ibsplc.neoicargo.stock.mapper;

import com.ibsplc.neoicargo.common.converters.CentralConfig;
import com.ibsplc.neoicargo.stock.mapper.common.BaseMapper;
import com.ibsplc.neoicargo.stock.model.viewfilter.StockRangeFilterModel;
import com.ibsplc.neoicargo.stock.vo.filter.StockRangeFilterVO;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    config = CentralConfig.class,
    builder = @Builder(disableBuilder = true),
    uses = {BaseMapper.class})
public interface StockRangeFilterMapper {
  @Mapping(
      target = "privilegeLevelValue",
      source = "privilegeLevelValue",
      qualifiedByName = "toArray")
  @Mapping(
      target = "startDate",
      source = "startDate",
      qualifiedByName = "convertLocalDateToTimestamp")
  @Mapping(target = "endDate", source = "endDate", qualifiedByName = "convertLocalDateToTimestamp")
  StockRangeFilterVO mapModelToVo(StockRangeFilterModel model);
}
