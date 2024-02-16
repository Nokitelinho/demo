package com.ibsplc.neoicargo.stock.mapper;

import com.ibsplc.neoicargo.common.converters.CentralConfig;
import com.ibsplc.neoicargo.stock.mapper.common.BaseMapper;
import com.ibsplc.neoicargo.stock.model.viewfilter.StockFilterModel;
import com.ibsplc.neoicargo.stock.util.CalculationUtil;
import com.ibsplc.neoicargo.stock.vo.filter.StockFilterVO;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    config = CentralConfig.class,
    builder = @Builder(disableBuilder = true),
    imports = {CalculationUtil.class},
    uses = {BaseMapper.class})
public interface StockFilterMapper {

  @Mapping(target = "manualFlag", source = "manual", qualifiedByName = "convertToFlag")
  @Mapping(
      target = "asciiRangeFrom",
      expression =
          "java(model.getRangeFrom() != null ? CalculationUtil.toLong(model.getRangeFrom()) : null)")
  @Mapping(
      target = "asciiRangeTo",
      expression =
          "java(model.getRangeTo() != null ? CalculationUtil.toLong(model.getRangeTo()) : null)")
  StockFilterVO mapModelToVo(StockFilterModel model);
}
