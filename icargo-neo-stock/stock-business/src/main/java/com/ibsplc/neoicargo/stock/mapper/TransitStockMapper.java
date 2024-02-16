package com.ibsplc.neoicargo.stock.mapper;

import com.ibsplc.neoicargo.common.converters.CentralConfig;
import com.ibsplc.neoicargo.stock.dao.entity.TransitStock;
import com.ibsplc.neoicargo.stock.mapper.common.BaseMapper;
import com.ibsplc.neoicargo.stock.mapper.common.EntityAbstractMapper;
import com.ibsplc.neoicargo.stock.mapper.common.ModelAbstractMapper;
import com.ibsplc.neoicargo.stock.model.TransitStockModel;
import com.ibsplc.neoicargo.stock.vo.TransitStockVO;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    config = CentralConfig.class,
    builder = @Builder(disableBuilder = true),
    uses = {BaseMapper.class, MissingStockMapper.class})
public interface TransitStockMapper
    extends ModelAbstractMapper<TransitStockModel, TransitStockVO>,
        EntityAbstractMapper<TransitStock, TransitStockVO> {
  @Mapping(
      target = "missingRanges",
      expression = "java(missingStockMapper.clone(vo.getMissingRanges()))")
  TransitStockVO clone(TransitStockVO vo);

  @Mapping(target = "lastUpdatedUser", source = "lastUpdateUser")
  @Mapping(
      target = "lastUpdatedTime",
      source = "lastUpdateTime",
      qualifiedByName = "convertToTimestamp")
  TransitStock mapVoToEntity(TransitStockVO vo);
}
